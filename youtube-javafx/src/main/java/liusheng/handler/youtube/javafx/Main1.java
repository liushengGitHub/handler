package liusheng.handler.youtube.javafx;

import liusheng.handler.DataInsert;
import liusheng.handler.DefaultPipeline;
import liusheng.handler.bilibili.utils.BilibiliOkHttpClientUtils;
import liusheng.handler.http.DefaultDownloaderController;
import liusheng.handler.http.DownloadListener;
import liusheng.handler.http.DownloaderController;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Main1 {
    public static void main2(String[] args) throws IOException {
        Response execute = BilibiliOkHttpClientUtils
                .httpClient().newCall(new Request.Builder()
                        .url("http://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/52/09/201760952/201760952-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1594152206&gen=playurl&os=kodobv&oi=835891763&trid=101a67974f4e453bb41bb6b3c69bb6bfu&platform=pc&upsig=9f868d14f42a6eebf96cfc25583addb7&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=37044417&orderid=0,3&agrr=1&logo=80000000")
                        .build()).execute();

        InputStream inputStream = execute.body().byteStream();

        FFmpegFrameGrabber frameGrabber1 =
                new FFmpegFrameGrabber(inputStream);
        //frameGrabber1.setFormat("webm");
        FFmpegFrameGrabber frameGrabber2 =
                new FFmpegFrameGrabber(BilibiliOkHttpClientUtils
                        .httpClient().newCall(new Request.Builder()
                                .url("http://upos-sz-mirrorcos.bilivideo.com/upgcxcode/52/09/201760952/201760952-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1594152206&gen=playurl&os=cosbv&oi=835891763&trid=101a67974f4e453bb41bb6b3c69bb6bfu&platform=pc&upsig=31facbe33e08bf3a4d8232edd384dc6e&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=37044417&orderid=0,3&agrr=1&logo=80000000")
                                .build()).execute().body().byteStream());
        // frameGrabber2.setFormat("webm");
        Frame captured_frame = null;


        try {

            frameGrabber1.start();
            frameGrabber2.start();
            FrameRecorder recorder = new FFmpegFrameRecorder("2.mp4", frameGrabber2.getImageWidth(), frameGrabber2.getImageHeight());
            recorder.setFrameRate(frameGrabber2.getFrameRate());
            recorder.setFormat("mp4");
            recorder.setAudioChannels(frameGrabber1.getAudioChannels());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("threads", "5");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG1VIDEO);
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);
            recorder.start();
            while (true) {
                try {
                    Frame frame = frameGrabber1.grab();
                    if (frame != null && frame.samples != null) {
                        recorder.setTimestamp(frame.timestamp);
                        recorder.record(frame);
                    }
                    captured_frame = frameGrabber2.grab();
                    if (captured_frame != null && captured_frame.image != null) {

                        recorder.setTimestamp(captured_frame.timestamp);
                        recorder.record(captured_frame);

                    }


                    if (captured_frame == null && frame == null) {
                        System.out.println("!!! Failed cvQueryFrame");
                        break;
                    } else {
                        if (frame != null) {

                            System.out.println("Main1.main" + frame.timestamp / 1000);
                        }
                        if (captured_frame != null) {

                            System.out.println("Main1.main" + captured_frame.timestamp / 1000);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recorder.stop();
            recorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static long getSize(Buffer[] samples) {
        return Optional.ofNullable(samples)
                .map(buffers -> {
                    return Arrays.asList(buffers)
                            .stream()
                            .mapToLong(Buffer::limit).sum();
                }).orElse(0L);
    }

    private static InputStream getInputStream(String audioUrl, AtomicLong total) throws IOException {
        ResponseBody responseBody = BilibiliOkHttpClientUtils
                .httpClient().newCall(new Request.Builder()
                        .url(audioUrl)
                        .build()).execute().body();
        long length = responseBody.contentLength();

        total.addAndGet(length);
        return responseBody.byteStream();
    }
    private static  void mergeByGaggber(DownloaderController downloaderController, DownloadListener downloadListener, AtomicLong current, AtomicLong total, File targetFile, DefaultPipeline defaultPipeline, FFmpegFrameGrabber frameGrabber1, FFmpegFrameGrabber frameGrabber2, LinkedTransferQueue<Frame> frameBlockingQueue) {

        CountDownLatch countDownLatchStart = new CountDownLatch(2);
        CountDownLatch countDownLatchEnd = new CountDownLatch(2);

        Runnable mp3Runnable = () -> {
            try {
                frameGrabber1.start();
                countDownLatchStart.countDown();

                Frame frame = null;
                frameGrabber1.flush();
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
                frameGrabber2.start();
                countDownLatchStart.countDown();
                Frame frame = null;
                frameGrabber2.flush();
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

        defaultPipeline.getHelpExecutorService()
                .execute(mp3Runnable);
        defaultPipeline.getHelpExecutorService()
                .execute(mp4Runnable);
        FrameRecorder recorder = new FFmpegFrameRecorder(targetFile.getAbsoluteFile(), frameGrabber2.getImageWidth() > 0 ? frameGrabber2.getImageWidth() :
                1376, frameGrabber2.getImageHeight() > 0 ? frameGrabber2.getImageHeight() : 768);
        try {

            countDownLatchStart.await();
            recorder.setFormat("mp4");
            recorder.setAudioChannels(frameGrabber1.getAudioChannels() ==0 ? 2 : frameGrabber1.getAudioChannels());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("threads", "5");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG1VIDEO);
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
                    current.addAndGet(getSize(frame.image));
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    public static void main(String[] args) {
        mergeByGaggber(new DefaultDownloaderController(), new DownloadListener() {
            @Override
            public void listen(long current, long total) {

            }
        },new AtomicLong(),new AtomicLong(),new File("4.mp4"),new DefaultPipeline(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), new DataInsert() {
                    @Override
                    public void addData(Object data) {

                    }
                }),
                new FFmpegFrameGrabber("1.mp3.temp"),
                new FFmpegFrameGrabber("2.mp4.temp"),new LinkedTransferQueue<>());
    }
    public static void main3(String[] args) throws IOException {
        CountDownLatch countDownLatchStart = new CountDownLatch(2);
        CountDownLatch countDownLatchEnd = new CountDownLatch(2);
        // 边下载边合并
        Response execute = BilibiliOkHttpClientUtils
                .httpClient().newCall(new Request.Builder()
                        .url("http://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/19/00/176800019/176800019-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1594154554&gen=playurl&os=kodobv&oi=835891763&trid=19bb024271844058af69b31c958932b3u&platform=pc&upsig=39c7daf37d0f1687c42c487f7efb9ba3&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=37044417&orderid=0,3&agrr=1&logo=80000000")
                        .build()).execute();

        InputStream inputStream = execute.body().byteStream();

        FFmpegFrameGrabber frameGrabber1 =
                new FFmpegFrameGrabber(inputStream);
        //frameGrabber1.setFormat("webm");
        FFmpegFrameGrabber frameGrabber2 =
                new FFmpegFrameGrabber(BilibiliOkHttpClientUtils
                        .httpClient().newCall(new Request.Builder()
                                .url("http://upos-sz-mirrorhw.bilivideo.com/upgcxcode/19/00/176800019/176800019-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1594154554&gen=playurl&os=hwbv&oi=835891763&trid=19bb024271844058af69b31c958932b3u&platform=pc&upsig=61c99813de323da4380c3e397520b97c&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=37044417&orderid=0,3&agrr=1&logo=80000000")
                                .build()).execute().body().byteStream());
        //frameGrabber2.setFormat("webm");

        DownloaderController downloaderController = new DefaultDownloaderController();
        LinkedTransferQueue<Frame> frameBlockingQueue = new LinkedTransferQueue<>();
        Runnable mp3Runnable = () -> {
            try {
                frameGrabber1.start();

                countDownLatchStart.countDown();
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
                frameGrabber2.start();
                countDownLatchStart.countDown();
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
            new Thread
                    (mp3Runnable).start();

            new Thread
                    (mp4Runnable).start();

            countDownLatchStart.await();
            FrameRecorder recorder = new FFmpegFrameRecorder("3.mp4", frameGrabber2.getImageWidth(), frameGrabber2.getImageHeight());
            recorder.setFrameRate(frameGrabber2.getFrameRate());
            recorder.setFormat("mp4");
            recorder.setAudioChannels(frameGrabber1.getAudioChannels());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("threads", "5");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG1VIDEO);
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);
            recorder.start();
            while (!frameBlockingQueue.isEmpty() || countDownLatchEnd.getCount() != 0) {
                try {
                    Frame frame = frameBlockingQueue.peek();
                    if (frame != null && frame.samples != null) {
                        recorder.setTimestamp(frame.timestamp);
                        recorder.record(frame);

                    }
                    if (frame != null && frame.image != null) {
                        recorder.setTimestamp(frame.timestamp);
                        recorder.record(frame);
                    }

                    if (frame != null) {
                        System.out.println(frame.timestamp / 1000);
                        frameBlockingQueue.poll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recorder.stop();
            recorder.release();
            frameGrabber1.stop();
            frameGrabber1.release();
            frameGrabber2.stop();
            frameGrabber2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }
}
