package liusheng.gulixueyuan;

import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 2020年:  05 月:  09 日:  10小时:  56分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class OkHttpClientUtils {
    private static OkHttpClient okHttpClient;
    final static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static AtomicBoolean atomicBoolean  = new AtomicBoolean();
    private static String LOGIN_URL = "http://www.gulixueyuan.com/login";
    private static String LOGIN_URL_POST = "http://www.gulixueyuan.com/login_check";
    static {
        okHttpClient = new OkHttpClient.Builder()
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


    }

    public static OkHttpClient guliHttpClient() throws IOException {
        login();
        return okHttpClient;
    }
    public static void login() throws IOException {
        if (atomicBoolean.compareAndSet(false,true)) {
            String srsf = getCrsf();
            FormBody formBody = new FormBody.Builder()
                    .add("_username", "15664453581")
                    .add("_password", "15664453581")
                    .add("_csrf_token", srsf)
                    .add("_remember_me", "on")
                    .build();

            Request request = new Request.Builder()
                    .post(formBody)
                    .url(LOGIN_URL_POST)
                    .build();

            okHttpClient.newCall(request)
                    .execute();
        }

    }

    private static String getCrsf() throws IOException {
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .build();

        Response response = okHttpClient.newCall(request)
                .execute();

        String body = response.body().string();
        Element element = Jsoup.parse(body).select("input[name='_csrf_token']")
                .first();
        if (Objects.isNull(element) ) {
            throw  new UnsupportedOperationException();
        }
        String val = element.val();
        return val;
    }

    public static void main(String[] args) throws IOException {
        login();
        System.out.println("121");
        Request request = new Request.Builder()
                .url("http://www.gulixueyuan.com/course/369/task/13283/activity_show")
                .build();

        Response response = okHttpClient.newCall(request)
                .execute();
        System.out.println(response.body().string());
    }
}
