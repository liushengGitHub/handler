package hello;

import liusheng.handler.BootStrap;
import liusheng.handler.DataInsert;
import liusheng.handler.http.StandOutDownloadListener;
import liusheng.handler.youtube.FileRetryFailurerHandler;
import liusheng.handler.youtube.handler.*;
import liusheng.handler.youtube.utils.YoutubePatternUtils;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * 2020年:  05 月:  04 日:  12小时:  31分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Test1 {

    @Test
    public void test1() throws Exception {
        /* DefaultPipeline defaultPipeline = new DefaultPipeline();*/

      /*  defaultPipeline.addHandler(new YoutubeDocumentHandler());
        defaultPipeline.addHandler(new YoutubeVideoHandler());
        defaultPipeline.addHandler(new YoutubeDescriptionHandler());
        defaultPipeline.addHandler(new TimedTextHandler());
        defaultPipeline.addHandler(new ChannelVideoHandler());

        defaultPipeline.handle("https://www.youtube.com/channel/UCGYmHiw4vrqEeEjzlocYJRg/videos");*/
    }

    @Test
    public void test2() {
        System.out.println(YoutubePatternUtils.isChannelVideo("https://www.youtube.com/channel/UCGYmHiw4vrqEeEjzlocYJRg/videos"));
    }

    @Test
    public void test3() throws Exception {
        BootStrap bootStrap = new BootStrap();

        DataInsert dataInsert = bootStrap
                .mainExecutorService(Executors.newCachedThreadPool())
                .helpExecutorService(Executors.newCachedThreadPool())
                .addHandler(new YoutubeDocumentHandler())
                .addHandler(new YoutubeDescriptionHandler())
                .addHandler(new YoutubeVideoHandler())
                .addHandler(new ChannelVideoHandler())
                .addHandler(new ChannelVideoInfoHandler())
                .addHandler(new TimedTextHandler())
                .start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            dataInsert.addData(scanner.nextLine());
        }
    }

    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        FileRetryFailurerHandler fileRetryFailurerHandler = new FileRetryFailurerHandler("F:\\mp3\\" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + ".txt");
        DataInsert dataInsert = bootStrap
                .mainExecutorService(Executors.newFixedThreadPool(2))
                .helpExecutorService(Executors.newCachedThreadPool())
                .addHandler(new YoutubeDocumentHandler(fileRetryFailurerHandler))
                .addHandler(new YoutubeDescriptionHandler())
                .addHandler(new YoutubeSurfaceHandler("F:\\img"))
                .addHandler(new YoutubeVideoHandler(fileRetryFailurerHandler))
                .addHandler(new ChannelVideoHandler(fileRetryFailurerHandler))
                .addHandler(new ChannelVideoInfoHandler())
                .addHandler(new TimedTextHandler())
                .addHandler(new DownloadListenerHandler(new StandOutDownloadListener()))
                .addHandler(new YoutubeDownloaderHandler(fileRetryFailurerHandler, 1, 1080, "F:\\mp3"))

                .start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            dataInsert.addData(scanner.nextLine());
        }
    }

    @Test
    public void test4() throws Exception {

    }

    public static void main1(String[] args) throws Exception {
        String url = "https://dvr.yout.com/mp3";
        String video_id = "8ksf2zQDpfI";
        String video_url = new String(Base64.getUrlEncoder()
                .encode(URLEncoder.encode("https://www.youtube.com/watch?v=" + video_id, "UTF-8").getBytes()));
        /*
        * video_id: bX6WhQP3Ed4
video_url: aHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1iWDZXaFFQM0VkNA==
format: mp3
title: 遇到 歌詞 Lyrics HD【抖音系列】高音質【HOT TIK TOK】 || CN  -  流行音乐
artist: 方雅賢
start_time: false
end_time: false
thingy: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbF9mb3JfYXBpX2FjY2VzcyI6ImpvaG5AbmFkZXIubXgifQ.YPt3Eb3xKekv2L3KObNqMF25vc2uVCC-aDPIN2vktmA
audio_quality: 128k
        * */

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        return chain.proceed(chain.request());
                    }
                }).build();


        /**
         * title: 暗戀快樂【動態歌詞Lyrics】
         * artist: 于潼
         *
         * content-type: application/x-www-form-urlencoded
         * user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36
         */
        Response response = okHttpClient.newCall(
                new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        .addHeader("Accept", "*/*")
                        .addHeader("Origin", "chrome-extension://aejoelaoggembcahagimdiliamlcdmfm")
                        .addHeader("Accept-Encoding", "gzip, deflate, br")
                        /*
                        *
                        * Sec-Fetch-Site:	none
Sec-Fetch-Mode:	cors
Sec-Fetch-Dest:	empty*/
                        .addHeader("Sec-Fetch-Site", "none")
                        .addHeader("Sec-Fetch-Mode", "cors")
                        .addHeader("Sec-Fetch-Dest", "empty")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                        .post(new FormBody.Builder()
                                .add("video_url", video_url)
                                .add("video_id", video_id)
                                .add("start_time", "false")
                                .add("title", "暗戀快樂【動態歌詞Lyrics】")
                                .add("start_time", "false")
                                .add("artist", "于潼")
                                .add("format", "mp3")
                                .add("audio_quality", "128k")
                                .add("thingy", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbF9mb3JfYXBpX2FjY2VzcyI6ImpvaG5AbmFkZXIubXgifQ.YPt3Eb3xKekv2L3KObNqMF25vc2uVCC-aDPIN2vktmA")
                                .build())
                        .build()
        ).execute();

        InputStream inputStream = response.body().byteStream();

        byte[] bytes = new byte[1024000];

        long i = 0;
        int length = -1;
        while ((length = inputStream.read(bytes)) != -1) {
            i += length;
            System.out.println(i);
        }
    }

    @Test
    public void test5() throws Exception {
        //size=    2441kB time=00:02:36.22 bitrate= 128.0kbits/s speed=10.4x
        String s = new String(UUID.randomUUID().toString() + Math.random()) + 1;
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s.intern()));
        System.out.println(System.identityHashCode(s.intern()));

    }
}
