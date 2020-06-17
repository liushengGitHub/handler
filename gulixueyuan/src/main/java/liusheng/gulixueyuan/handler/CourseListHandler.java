package liusheng.gulixueyuan.handler;

import com.google.gson.reflect.TypeToken;
import liusheng.gulixueyuan.OkHttpClientUtils;
import liusheng.gulixueyuan.entity.VideoInfo;
import liusheng.gulixueyuan.utils.GsonUtils;
import liusheng.handler.Assert;
import liusheng.handler.DefaultPipeline;
import liusheng.handler.Handler;
import liusheng.handler.HandlerContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2020年:  05 月:  09 日:  11小时:  27分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class CourseListHandler implements Handler {
    //http://www.gulixueyuan.com/course/369/
    private static final Pattern patCursorPageListLink = Pattern.compile("(http|https)://(www\\.|m.|)gulixueyuan\\.com/course/\\d+");

    @Override
    public void handle(Object object, HandlerContext handlerContext) throws Exception {
        String url = object.toString() + "/tasks";
        OkHttpClient okHttpClient = OkHttpClientUtils.guliHttpClient();


        Response response = okHttpClient
                .newCall(new Request.Builder()
                        .url(url)
                        .build())
                .execute();

        String body = response.body().string();


        Element element = Jsoup.parse(body)
                .select(".js-hidden-cached-data").first();

        Assert.notNull(element, "1");

        String text = element.text();

        text = unicodeDecode(text);

        List<VideoInfo> list = GsonUtils.gson().fromJson(text, new TypeToken<List<VideoInfo>>() {
        }.getType());

        //http://www.gulixueyuan.com/course/369/task/13283/activity_show
        list.forEach(videoInfo -> {

            String taskId = videoInfo.getTaskId();

            String videoUrl = object.toString() + "/task" + "/" + taskId + "/activity_show";

           /* DefaultPipeline pipeline = new DefaultPipeline();

            pipeline.addHandler(new VideoUrlHandler());

            try {
                pipeline.handle(videoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        });

    }

    @Override
    public boolean support(Object o,HandlerContext handlerContext) throws Exception {
        return patCursorPageListLink.matcher(o.toString()).matches();
    }

    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
}
