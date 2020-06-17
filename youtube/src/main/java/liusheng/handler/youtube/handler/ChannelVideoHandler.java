package liusheng.handler.youtube.handler;

import liusheng.handler.*;
import liusheng.handler.youtube.utils.YoutubePatternUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * 2020年:  05 月:  04 日:  18小时:  09分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ChannelVideoHandler extends AbstractRetryHandler {

    public ChannelVideoHandler(RetryFailurerHandler retryFailurerHandler) {
        super(retryFailurerHandler);
    }

    public ChannelVideoHandler() {
    }

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        ChannelVideoExtractor videoExtractor = new ChannelVideoExtractor(handlerDataData.toString());
        Document document = (Document) data.get("document");
        videoExtractor.setContent(document.toString());
        String channelName = Optional
                .ofNullable(document.select("meta[property='og:title']")
                        .first())
                .map(e -> e.attr("content"))
                .orElse(document.title());
        if (StringUtils.isBlank(channelName)) {
            channelName = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        }
        data.put("channelName", channelName);
        data.put("videoExtractor", videoExtractor);
    }


    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isChannelVideo(handlerDataData.toString());
    }
}
