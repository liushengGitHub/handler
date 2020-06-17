package liusheng.handler.youtube.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.DefaultPipeline;
import liusheng.handler.HandlerContext;
import liusheng.handler.HandlerData;
import liusheng.handler.http.VideoInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 2020年:  05 月:  29 日:  14小时:  41分钟:
 * 用户名: 👨‍LiuSheng👨‍
 *
 * d对应VideoInfos 数据进行处理
 * */

public class ChannelVideoInfoHandler extends AbstractRetryHandler {
    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        Collection<VideoInfo> videoInfos = (Collection<VideoInfo>) data.get("videoInfos");
        if (videoInfos.isEmpty()) return;

        String channelName = Optional.ofNullable(data.get("channelName")).map(Object::toString).filter(StringUtils::isNotBlank).orElse(UUID.randomUUID().toString());
        handleVideoInfos(handlerContext, videoInfos, channelName);
    }

    private void handleVideoInfos(HandlerContext handlerContext, Collection<VideoInfo> videoInfos, String channelName) {
        DefaultPipeline pipeline = (DefaultPipeline) handlerContext.pipeline();


        videoInfos.forEach(videoInfo -> {
            pipeline.getMainExecutorService()
                    .execute(() -> {
                        String videoId = videoInfo.getVideoId();
                        String url = "https://www.youtube.com/watch?v=" + videoId;
                        try {
                            HandlerData handlerData = new HandlerData(url);
                            handlerData.getState().put("channelName", channelName);
                            pipeline.handle(handlerData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Map<String, Object> data = handlerContext.data();
        Object videoInfos = data.get("videoInfos");
        return videoInfos != null && (videoInfos instanceof Collection);
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }
}
