package liusheng.handler.http.utils;

import okhttp3.OkHttpClient;

/**
 * 2020年:  05 月:  27 日:  10小时:  52分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class OkHttpClientUtils {

    private static OkHttpClient okHttpClient;
    static {
        okHttpClient = new OkHttpClient();
    }
    public static OkHttpClient httpClient() {

        return okHttpClient;
    }
}
