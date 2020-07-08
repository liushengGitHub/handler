package liusheng.handler.acfun.handler;

import liusheng.handler.HandlerContext;
import liusheng.handler.http.AbstractDocumentHandler;
import liusheng.handler.http.utils.OkHttpClientUtils;
import okhttp3.Request;

/**
 * 2020年:  06 月:  17 日:  22小时:  39分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class AcfunDocumentHandler extends AbstractDocumentHandler {
    @Override
    protected String getHtmlBody(String url, HandlerContext handlerContext) throws Exception {
        return OkHttpClientUtils.httpClient()
                .newCall(new Request.Builder()
                        .url(url)
                        .build())
                .execute().body().string();
    }

    @Override
    protected boolean isMatcher(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return handlerDataData.toString().startsWith("https://www.acfun.cn/");
    }
}
