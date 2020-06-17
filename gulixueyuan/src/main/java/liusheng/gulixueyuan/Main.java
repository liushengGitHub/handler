package liusheng.gulixueyuan;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 2020年:  05 月:  09 日:  10小时:  35分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

//http://www.gulixueyuan.com/course/369/task/13283/activity_show
//http://www.gulixueyuan.com/course/369/task/13283/show
public class Main {
    public static void main(String[] args) throws IOException {
         final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        Response response = okHttpClient.newCall(new Request.Builder()
                .url("https://www.baidu.com")
                .build()).execute();

        okHttpClient.cookieJar();

    }
}
