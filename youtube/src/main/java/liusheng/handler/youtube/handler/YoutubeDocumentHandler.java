package liusheng.handler.youtube.handler;

import liusheng.handler.HandlerContext;
import liusheng.handler.RetryFailurerHandler;
import liusheng.handler.http.AbstractDocumentHandler;
import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 2020年:  05 月:  04 日:  12小时:  18分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */
@Slf4j
public class YoutubeDocumentHandler extends AbstractDocumentHandler {

    public YoutubeDocumentHandler(RetryFailurerHandler retryFailurerHandler) {
        super(retryFailurerHandler);
    }

    public YoutubeDocumentHandler() {
        super();
    }

    @Override
    protected String getHtmlBody(String url, HandlerContext handlerContext) throws IOException {
        Response response = YoutubeRetrofitUtils.getOkHttpClient()
                .newCall(new Request.Builder()
                        .url(url)
                        .build()
                ).execute();
        String body = response.body().string();
        response.close();
        return body;
    }

    @Override
    protected boolean isMatcher(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return handlerDataData.toString().startsWith("https://www.youtube.com");
    }
}
