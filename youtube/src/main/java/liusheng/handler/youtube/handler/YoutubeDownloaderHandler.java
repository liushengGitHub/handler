package liusheng.handler.youtube.handler;

import liusheng.handler.*;
import liusheng.handler.http.DefaultDownloaderController;
import liusheng.handler.http.DownloadListener;
import liusheng.handler.http.DownloaderController;
import liusheng.handler.http.utils.*;
import liusheng.handler.youtube.handler.entity.VideoDetails;
import liusheng.handler.youtube.handler.entity.VideoMeta;
import liusheng.handler.youtube.handler.entity.YtFile;
import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bytedeco.ffmpeg.avformat.AVIOInterruptCB;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static liusheng.handler.http.utils.DownloadUtils.downloadFile;

/**
 * 2020年:  05 月:  25 日:  11小时:  35分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeDownloaderHandler extends AbstractRetryHandler {
    // 在mp4 模式下，是否转换mp3
    private int mp3;
    private int height;
    private String dir;

    private int mode = 0;

    public YoutubeDownloaderHandler(RetryFailurerHandler retryFailurerHandler, int mp3, int height, String dir) {
        super(retryFailurerHandler);
        this.mp3 = mp3;
        this.height = height;
        this.dir = dir;
    }


    public YoutubeDownloaderHandler(int mp3, int height, String dir) {
        this(null, mp3, height, dir);
    }

    @Override
    protected void handle0(Object object, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        VideoDetails videoDetails = (VideoDetails) data.get("videoDetails");


        String dirName = null;
        if (origin instanceof HandlerData) {
            HandlerData handlerData = (HandlerData) origin;
            Object name = handlerData.getState().get("channelName");
            if (Objects.nonNull(name)) {
                dirName = name.toString();
            }
        }


        VideoMeta videoMeta = videoDetails.getVideoMeta();

        Map<Integer, YtFile> ytFileMap = videoDetails.getYtFileMap();
        if (Objects.isNull(ytFileMap) || ytFileMap.isEmpty()) {
            System.out.println("解析失败：" + data.get("originUrl"));
            throw new ParseExcetion();
        }

        Map<Integer, YtFile> ytFileMapConvert = convert(ytFileMap);
        String title = videoMeta.getTitle();

        Integer height = (Integer) data.get("height");
        String fileName = StringUtils.fileNameHandle(title);

        if ((height == null && this.height == -1) || (height != null && height == -1)) {
            // 下载音频
            YtFile audioFile = ytFileMapConvert.get(-1);
            downloadMp3(handlerContext, dirName, fileName, audioFile);
        } else {
            // 下载视频
            if (height == null) {
                height = this.height;
            }
            YtFile audioFile = ytFileMapConvert.get(-1);

            if (ytFileMap.size() < 2 && Objects.isNull(audioFile)) {
                System.out.println("解析失败：" + data.get("originUrl"));
                throw new ParseExcetion();
            }
            YtFile videoFile = Optional.ofNullable(ytFileMapConvert.get(height))
                    .orElse(ytFileMapConvert.entrySet().stream().filter(p -> p.getKey() != -1).max(Comparator.comparing(Map.Entry::getKey))
                            .get().getValue());

            if (videoFile == null) {
                downloadMp3(handlerContext, dirName, fileName, audioFile);
            } else {
                downloadMp4(handlerContext, dirName, fileName, audioFile, videoFile);
            }
        }


    }

    private void downloadMp4(HandlerContext handlerContext, String dirName, String fileName, YtFile audioFile, YtFile videoFile) throws Exception {
        File channelDir = StringUtils.isEmpty(dirName) ? new File(dir) : new File(dir, dirName);
        if (!channelDir.exists()) {
            channelDir.mkdirs();
        }
        Map<String, Object> data = handlerContext.data();
        Object originUrl = data.get("originUrl");
        DefaultPipeline defaultPipeline = (DefaultPipeline) handlerContext.pipeline();
        File sourceAudioFile = getExistFile("sourceAudioFile", new File(channelDir, fileName + "mp3.temp").getAbsoluteFile(), data);
        File sourceVideoFile = getExistFile("sourceVideoFile", new File(channelDir, fileName + "mp4.temp").getAbsoluteFile(), data);
        File targetFile = new File(channelDir, fileName + ".mp4").getAbsoluteFile();
        File targetFileMp3 = new File(channelDir, fileName + ".mp3").getAbsoluteFile();
        DownloaderController downloaderController = (DownloaderController) data.computeIfAbsent("downloadController", e -> new DefaultDownloaderController());
        downloaderController.setState(DownloaderController.EXECUTE);

        AtomicLong total = (AtomicLong) data.getOrDefault("total", new AtomicLong());
        total.set(0);
        AtomicLong current = (AtomicLong) data.getOrDefault("current", new AtomicLong());
        current.set(0);
        if (mode == 0) {
            // 边下载边合并
            FFmpegFrameGrabber frameGrabber1 =
                    new FFmpegFrameGrabber(getInputStream(audioFile.getUrl(), total));
            // frameGrabber1.setFormat("webm");
            FFmpegFrameGrabber frameGrabber2 =
                    new FFmpegFrameGrabber(getInputStream(videoFile.getUrl(), total));
            //frameGrabber2.setFormat("webm");

            LinkedTransferQueue<Frame> frameBlockingQueue = new LinkedTransferQueue<>();
            DownloadListener downloadListener = (DownloadListener) data.get("downloadListener");

            mergeByGaggber(downloaderController, downloadListener, current, total, targetFile,
                    defaultPipeline, frameGrabber1, frameGrabber2, frameBlockingQueue);

        } else {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            downloadAndMerge(handlerContext, fileName, audioFile, videoFile, data, originUrl, defaultPipeline, sourceAudioFile,
                    sourceVideoFile, targetFile, targetFileMp3, downloaderController, countDownLatch, total, current);
        }

    }

    private void mergeByGaggber(DownloaderController downloaderController, DownloadListener downloadListener, AtomicLong current, AtomicLong total, File targetFile, DefaultPipeline defaultPipeline, FFmpegFrameGrabber frameGrabber1, FFmpegFrameGrabber frameGrabber2, LinkedTransferQueue<Frame> frameBlockingQueue) {

        CountDownLatch countDownLatchStart = new CountDownLatch(2);
        CountDownLatch countDownLatchEnd = new CountDownLatch(2);

        Runnable mp3Runnable = () -> {
            try {
                try {
                    frameGrabber1.start();
                } catch (FrameGrabber.Exception e) {

                } finally {
                    countDownLatchStart.countDown();
                }

                Frame frame = null;

                while ((frame = frameGrabber1.grab()) != null && downloaderController.getState() != DownloaderController.EXCEPTION
                        && downloaderController.getState() != DownloaderController.CANCEL) {
                    frameBlockingQueue.transfer(frame);
                }


            } catch (Exception e) {
                downloaderController.setState(DownloaderController.EXCEPTION);
                e.printStackTrace();
            } finally {
                countDownLatchEnd.countDown();
            }
        };
        Runnable mp4Runnable = () -> {
            try {
                try {
                    frameGrabber2.start();
                } catch (FrameGrabber.Exception e) {

                } finally {
                    countDownLatchStart.countDown();
                }
                Frame frame = null;

                while ((frame = frameGrabber2.grab()) != null && downloaderController.getState() != DownloaderController.EXCEPTION
                        && downloaderController.getState() != DownloaderController.CANCEL) {
                    frameBlockingQueue.transfer(frame);
                }
            } catch (Exception e) {
                downloaderController.setState(DownloaderController.EXCEPTION);
                e.printStackTrace();
            } finally {
                countDownLatchEnd.countDown();
            }
        };

        try {
            defaultPipeline.getHelpExecutorService()
                    .execute(mp3Runnable);
            defaultPipeline.getHelpExecutorService()
                    .execute(mp4Runnable);

            countDownLatchStart.await();
            FrameRecorder recorder = new FFmpegFrameRecorder(targetFile.getAbsoluteFile(), frameGrabber2.getImageWidth(), frameGrabber2.getImageHeight());
            recorder.setFrameRate(frameGrabber2.getFrameRate() > 0 ? frameGrabber2.getFrameRate() : 30);
            recorder.setSampleRate(frameGrabber1.getSampleRate());
            recorder.setFormat("mp4");
            recorder.setAudioChannels(frameGrabber1.getAudioChannels() == 0 ? 2 : frameGrabber1.getAudioChannels());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("threads", "5");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);
            recorder.start();
            while (!frameBlockingQueue.isEmpty() || countDownLatchEnd.getCount() != 0) {
                Frame frame = frameBlockingQueue.peek();

                if (frame != null && frame.samples != null) {
                    current.addAndGet(getSize(frame.samples));
                    if (Objects.nonNull(downloadListener)) {
                        downloadListener.listen(current.get(), total.get());
                    }
                    recorder.setTimestamp(frame.timestamp);
                    recorder.record(frame);

                }
                if (frame != null && frame.image != null) {
                    current.addAndGet(getSize(frame.samples));
                    if (Objects.nonNull(downloadListener)) {
                        downloadListener.listen(current.get(), total.get());
                    }
                    recorder.setTimestamp(frame.timestamp);
                    recorder.record(frame);
                }


                if (frame != null) {
                    System.out.println(frame.timestamp / 1000);
                    frameBlockingQueue.poll();
                }

            }
            recorder.stop();
            recorder.release();
            frameGrabber1.stop();
            frameGrabber1.release();
            frameGrabber2.stop();
            frameGrabber2.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getSize(Buffer[] samples) {
        return Optional.ofNullable(samples)
                .map(buffers -> {
                    return Arrays.asList(buffers)
                            .stream()
                            .mapToLong(Buffer::limit).sum();
                }).orElse(0L);
    }

    private String getInputStream(String audioUrl, AtomicLong total) throws IOException {
        Response response = YoutubeRetrofitUtils
                .getOkHttpClient().newCall(new Request.Builder()
                        .url(audioUrl)
                        .build()).execute();
        ResponseBody responseBody = response.body();
        long length = responseBody.contentLength();

        total.addAndGet(length);
        responseBody.close();
        response.close();
        return DownloadUtils.getUrl(audioUrl);
    }

    private void downloadAndMerge(HandlerContext handlerContext, String fileName, YtFile audioFile, YtFile videoFile, Map<String, Object> data, Object originUrl, DefaultPipeline defaultPipeline, File sourceAudioFile, File sourceVideoFile, File targetFile, File targetFileMp3, DownloaderController downloaderController, CountDownLatch countDownLatch, AtomicLong total, AtomicLong current) throws Exception {
        Runnable mp3Runnable = () -> {
            try {
                String audio = "audio";
                downloadFile(sourceAudioFile, targetFile, audioFile.getUrl(), handlerContext, audio, downloaderController, current, total);
            } catch (Exception e) {
                downloaderController.setState(DownloaderController.EXCEPTION);
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        };
        if (targetFile.exists() && (mp3 != 1 || targetFileMp3.exists())) {
            System.out.println("文件存在 ：" + originUrl);
            TimeUnit.SECONDS.sleep(1);
            return;
        }
        if (targetFile.exists() && mp3 == 1) {
            mp3Runnable.run();
            // 只需要下载mp3
        } else {
            // 下载mp3 和mp4
            defaultPipeline.getHelpExecutorService()
                    .execute(mp3Runnable);
            String video = "video";
            try {
                downloadFile(sourceVideoFile, targetFile, videoFile.getUrl(), handlerContext, video, downloaderController, current, total);
            } catch (Exception e) {
                downloaderController.setState(DownloaderController.EXCEPTION);
                e.printStackTrace();
            }
            countDownLatch.await();
        }

        handleException(downloaderController, sourceAudioFile, sourceVideoFile);

        downloaderController.setState(DownloaderController.DOWNLOAD_FINISHED);
        //
        ConverterListener converterListener = (ConverterListener) data.get("converterListener");
        if (mp3 == 1 && !targetFileMp3.exists()) {
            mergeFile(converterListener, "mp3", targetFileMp3, sourceAudioFile);
        }
        if (!targetFile.exists()) {
            mergeFile(converterListener, "mp4", targetFile, sourceAudioFile, sourceVideoFile);
        }

        downloaderController.setState(DownloaderController.MERGE_FINISHED);
        //
        System.out.println("完成： " + fileName + "=" + audioFile.getUrl());
        Arrays.asList(sourceAudioFile, sourceVideoFile).forEach(File::delete);
    }

    private void handleException(DownloaderController downloaderController, File... sourceFile) throws NoRetryExcetion {
        int state = downloaderController.getState();
        if (state == DownloaderController.CANCEL) {
            Arrays.asList(sourceFile).forEach(File::delete);
            throw new NoRetryExcetion();
        }
        if (state == DownloaderController.EXCEPTION) {
            throw new RuntimeException();
        }
    }

    private File getExistFile(String key, File file, Map<String, Object> data) {
        File sourceFile = (File) data.get(key);
        return Objects.nonNull(sourceFile) ? sourceFile : (File) data.compute(key, (k, v) -> FileUtils.noRepeat(file));
    }

    private void downloadMp3(HandlerContext handlerContext, String dirName, String fileName, YtFile audioFile) throws Exception {
        String suffix = "";

        Map<String, Object> data = handlerContext.data();
        File channelDir = StringUtils.isEmpty(dirName) ? new File(dir) : new File(dir, dirName);
        if (!channelDir.exists()) {
            channelDir.mkdirs();
        }

        File sourceFile = getExistFile("sourceAudioFile", new File(channelDir, fileName + "mp3.temp").getAbsoluteFile(), data);
        File targetFile = new File(channelDir, fileName).getAbsoluteFile();
        DownloaderController downloaderController = (DownloaderController) data.computeIfAbsent("downloadController", e -> new DefaultDownloaderController());
        downloaderController.setState(DownloaderController.EXECUTE);
        AtomicLong total = (AtomicLong) data.getOrDefault("total", new AtomicLong());
        AtomicLong current = (AtomicLong) data.getOrDefault("current", new AtomicLong());
        downloadFile(sourceFile, targetFile, audioFile.getUrl(), handlerContext, suffix, downloaderController, YoutubeRetrofitUtils.getOkHttpClient()
                , current, total);

        handleException(downloaderController, sourceFile);

        ConverterListener converterListener = (ConverterListener) data.get("converterListener");
        if (!targetFile.exists()) {
            mergeFile(converterListener, "mp3", targetFile, sourceFile);
        }

        System.out.println("完成： " + fileName + "=" + audioFile.getUrl());
        Arrays.asList(sourceFile).forEach(File::delete);
    }


    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Map<String, Object> data = handlerContext.data();
        return Objects.nonNull(data.get("downloaded")) && data.get("downloaded").equals(true);
    }


  /*  public static void main(String[] args) throws Exception {
        new YoutubeDownloaderHandler(1, 1080, "")
                .mergeFile(new File("F:\\mp3\\5.mp4"), new File("F:\\mp3\\Aimer『春はゆく』MUSICVIDEO（主演：浜辺美波・劇場版「Fatestaynight[Heaven'sFeel]」Ⅲ.springsong主題歌FULLver.）mp4(1).temp"),
                        new File("F:\\mp3\\Aimer『春はゆく』MUSICVIDEO（主演：浜辺美波・劇場版「Fatestaynight[Heaven'sFeel]」Ⅲ.springsong主題歌FULLver.）mp3(1).temp"));
    }*/

    private void mergeFile(ConverterListener converterListener, String type, File targetFile, File... sourceFile) throws Exception {
        synchronized (targetFile.getAbsolutePath().intern().intern()) {
            targetFile = FileUtils.noRepeat(targetFile);
            String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);

            List<String> commands = new ArrayList<>();

            commands.add(ffmpeg);

            for (int i = 0; i < sourceFile.length; i++) {
                commands.add("-i");
                commands.add("\"" + sourceFile[i].getAbsolutePath() + "\"");
            }
            commands.add("-threads");
            commands.add("5");
            commands.add("-preset");
            //superfast
            commands.add("ultrafast");
            commands.add("\"" + targetFile.toString() + "\"");

            ProcessBuilderUtils.executeAndDiscardOuput(converterListener, type, commands.toArray(new String[0]));

            System.out.println("转换完成" + targetFile.getName());
        }

    }

    private Map<Integer, YtFile> convert(Map<Integer, YtFile> ytFileMap) {

        return ytFileMap
                .entrySet()
                .stream()
                .collect(HashMap::new, (hashMap, integerYtFileEntry) -> {
                    YtFile value = integerYtFileEntry.getValue();

                    int height = value.getFormat().getHeight();
                    YtFile ytFile = hashMap.get(height);
                    if (Objects.isNull(ytFile)) {
                        hashMap.put(height, value);
                        return;
                    }
                    if (Objects.nonNull(ytFile) && (value.getFormat().getExt().equals("webm"))) {
                        hashMap.put(height, value);
                    }

                }, Map::putAll);

    }

    @Override
    protected int getRetryCount() {
        return 5;
    }

    @Override
    protected boolean getExecuteListen() {
        return true;
    }
}
