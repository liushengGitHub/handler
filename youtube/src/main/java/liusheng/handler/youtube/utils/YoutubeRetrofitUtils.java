package liusheng.handler.youtube.utils;

import liusheng.handler.youtube.interceptor.HeaderInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 2020年:  05 月:  04 日:  12小时:  15分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeRetrofitUtils {

    private static OkHttpClient httpClient;

    static {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("https://www.youtube.com")
                .build();
    }

    private static Retrofit retrofit;

    public static Retrofit retrofit() {
        return  retrofit;
    }

    public static OkHttpClient getOkHttpClient() {

        return httpClient;
    }
}
