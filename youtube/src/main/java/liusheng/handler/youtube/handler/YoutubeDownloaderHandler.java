package liusheng.handler.youtube.handler;

import liusheng.handler.*;
import liusheng.handler.http.DefaultDownloaderController;
import liusheng.handler.http.DownloaderController;
import liusheng.handler.http.utils.ConverterListener;
import liusheng.handler.http.utils.FileUtils;
import liusheng.handler.youtube.handler.entity.VideoDetails;
import liusheng.handler.youtube.handler.entity.VideoMeta;
import liusheng.handler.youtube.handler.entity.YtFile;
import liusheng.handler.http.utils.ProcessBuilderUtils;
import liusheng.handler.http.utils.StringUtils;
import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import org.bytedeco.javacpp.Loader;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicLong total = (AtomicLong) data.getOrDefault("total", new AtomicLong());
        AtomicLong current = (AtomicLong) data.getOrDefault("current", new AtomicLong());
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
