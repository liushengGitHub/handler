package liusheng.handler.youtube.handler;

import liusheng.handler.Handler;
import liusheng.handler.HandlerContext;
import liusheng.handler.http.DownloadListener;

import java.util.Map;

/**
 * 2020年:  05 月:  30 日:  09小时:  48分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DownloadListenerHandler implements Handler {
    private DownloadListener downloadListener;

    public DownloadListenerHandler(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public void handle(Object object, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        data.put("downloadListener", downloadListener);
    }

    @Override
    public boolean support(Object o, HandlerContext handlerContext) throws Exception {
        return true;
    }
}
