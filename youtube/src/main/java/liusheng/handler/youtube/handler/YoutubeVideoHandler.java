package liusheng.handler.youtube.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.Handler;
import liusheng.handler.HandlerContext;
import liusheng.handler.RetryFailurerHandler;
import liusheng.handler.youtube.handler.entity.VideoDetails;
import liusheng.handler.youtube.utils.YoutubePatternUtils;

import java.util.Map;

/**
 * 2020年:  05 月:  04 日:  12小时:  43分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeVideoHandler extends AbstractRetryHandler {


    public YoutubeVideoHandler(RetryFailurerHandler retryFailurerHandler) {
        super(retryFailurerHandler);
    }

    public YoutubeVideoHandler() {
        this(null);
    }

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        DefaultYouTubeExtractor defaultYouTubeExtractor = new DefaultYouTubeExtractor();
        defaultYouTubeExtractor.setPageHtml(data.get("document").toString());
        VideoDetails details = defaultYouTubeExtractor.extract(handlerDataData.toString());
        data.put("videoDetails", details);
        data.put("downloaded", true);
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isPage(handlerDataData.toString())
                && !YoutubePatternUtils.isList(handlerDataData.toString());
    }
}
