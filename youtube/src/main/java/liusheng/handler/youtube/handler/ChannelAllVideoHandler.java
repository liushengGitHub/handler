package liusheng.handler.youtube.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.utils.YoutubePatternUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2020年:  06 月:  08 日:  16小时:  35分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ChannelAllVideoHandler extends AbstractRetryHandler {
    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        ChannelVideoExtractor videoExtractor = (ChannelVideoExtractor) data.get("videoExtractor");
        List<List<VideoInfo>> list = new ArrayList<>();
        List<VideoInfo> videoInfos = videoExtractor.init();
        if (videoInfos != null) {
            list.add(videoInfos);
        }
        int i = 1;
        while ((videoInfos = videoExtractor.get(i++)) != null) {
            list.add(videoInfos);
        }

        List<VideoInfo> videoInfoList = list.stream().flatMap(List::stream).collect(Collectors.toList());

        data.put("videoInfos", videoInfoList);
    }


    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isChannelVideo(handlerDataData.toString());
    }
}
