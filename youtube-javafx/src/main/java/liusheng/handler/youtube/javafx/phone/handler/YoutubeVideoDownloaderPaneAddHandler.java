package liusheng.handler.youtube.javafx.phone.handler;

import liusheng.handler.HandlerContext;
import liusheng.handler.HandlerData;
import liusheng.handler.http.ChannelInfo;
import liusheng.handler.youtube.handler.entity.VideoDescription;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.utils.YoutubePatternUtils;

import java.util.Map;
import java.util.Optional;

/**
 * 2020年:  06 月:  08 日:  18小时:  58分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeVideoDownloaderPaneAddHandler extends AbstractVideoDownloaderPaneAddHandler {


    public YoutubeVideoDownloaderPaneAddHandler(String dir, boolean flag) {
        super(dir, flag);
    }


    protected VideoInfo getVideoInfoFlagIsFalse(HandlerData origin, HandlerContext handlerContext) {
        HandlerData handlerData = origin;
        return (VideoInfo) handlerData.getState().get("videoInfo");
    }

    protected VideoInfo getVideoInfoFlagIsTrue(Map<String, Object> data) {
        VideoDescription videoDescription = (VideoDescription) data.get("videoDescription");
        return convert(videoDescription);
    }

    private VideoInfo convert(VideoDescription videoDescription) {


        String title = videoDescription.getTitle();
        String author = videoDescription.getAuthor();
        String channelId = videoDescription.getChannelId();

        VideoInfo videoInfo = new VideoInfo();

        videoInfo.setName(title);
        videoInfo.setVideoId(videoDescription.getVideoId());
        videoInfo.setViewCountText(videoDescription.getViewCount());
        videoInfo.setPublishedTimeText(videoDescription.getPublishDate());

        Optional.ofNullable(videoDescription.getThumbnail())
                .map(VideoDescription.ThumbnailBean::getThumbnails)
                .filter(list -> list.size() > 0)
                .map(list -> list.get(list.size() - 1))
                .map(VideoDescription.ThumbnailBean.ThumbnailsBean::getUrl)
                .ifPresent(url -> {
                    videoInfo.setImgSrc(url);
                });

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setChannelId(channelId);
        channelInfo.setImgSrc(videoDescription.getAvatorUrl());
        channelInfo.setName(author);
        videoInfo.setChannelInfo(channelInfo);
        return videoInfo;
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isPage(handlerDataData.toString());
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }
}
