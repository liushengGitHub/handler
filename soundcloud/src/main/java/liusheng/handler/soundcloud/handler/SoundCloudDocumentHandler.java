package liusheng.handler.soundcloud.handler;

import liusheng.handler.HandlerContext;
import liusheng.handler.http.AbstractDocumentHandler;
import liusheng.handler.http.utils.OkHttpClientUtils;
import okhttp3.Request;

/**
 * 2020年:  05 月:  27 日:  10小时:  50分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoundCloudDocumentHandler extends AbstractDocumentHandler {

    @Override
    protected String getHtmlBody(String url, HandlerContext handlerContext) throws Exception {
        return OkHttpClientUtils.httpClient()
                .newCall(
                        new Request.Builder()
                                .url(url)
                                .build()
                ).execute()
                .body().string();
    }

}
