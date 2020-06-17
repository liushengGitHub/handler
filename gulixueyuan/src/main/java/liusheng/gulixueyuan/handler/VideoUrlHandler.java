package liusheng.gulixueyuan.handler;

import liusheng.gulixueyuan.OkHttpClientUtils;
import liusheng.handler.Assert;
import liusheng.handler.Handler;
import liusheng.handler.HandlerContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Objects;

/**
 * 2020年:  05 月:  09 日:  12小时:  31分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class VideoUrlHandler implements Handler {
    @Override
    public void handle(Object object, HandlerContext handlerContext) throws Exception {
        String url = object.toString();
        String videoUrl = getVideoUrl(url);

        System.out.println(videoUrl);

    }

    private String getVideoUrl(String url) throws IOException {
        OkHttpClient okHttpClient = OkHttpClientUtils.guliHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request)
                .execute();

        String string = response.body().string();


        Element element = Jsoup.parse(string,url)
                .select("#lesson-video-content").first();

        Assert.notNull(element,"element 是空");

        String videoUrl = element.attr("abs:data-url");

        return  videoUrl;

    }

    @Override
    public boolean support(Object o,HandlerContext handlerContext) throws Exception {
        return true;
    }
}
