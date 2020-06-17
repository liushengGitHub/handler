package liusheng.handler.http;


import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.RetryFailurerHandler;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 2020年:  05 月:  25 日:  22小时:  10分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public abstract class AbstractDocumentHandler extends AbstractRetryHandler {
    public AbstractDocumentHandler(RetryFailurerHandler retryFailurerHandler) {
        super(retryFailurerHandler);
    }

    public AbstractDocumentHandler() {
        super();
    }

    @Override
    protected void handle0(Object object, Object o, HandlerContext handlerContext) throws Exception {
        String url = object.toString();
        String body = getHtmlBody(url,handlerContext);

        Map<String, Object> data = handlerContext.data();
        // 加入Map钟
        data.put("originUrl", url);
        data.put("document", Jsoup.parse(body, "https://www.youtube.com/"));
    }

    protected abstract String getHtmlBody(String url, HandlerContext handlerContext) throws Exception ;

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return isURL(handlerDataData.toString().trim()) && isMatcher(handlerDataData,origin,handlerContext);
    }

    protected boolean isMatcher(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return true;
    }

    protected  boolean isURL(String toString){
        try {
            new URL(toString);
            return  true;
        } catch (MalformedURLException e) {
            return  false;
        }
    };
}
