package liusheng.handler.youtube.javafx.phone.handler;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.DataInsert;
import liusheng.handler.HandlerContext;
import liusheng.handler.http.ChannelInfo;
import liusheng.handler.youtube.handler.entity.VideoDescription;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.javafx.phone.ListItemVBox;
import liusheng.handler.youtube.utils.YoutubePatternUtils;

import java.util.Map;
import java.util.Optional;

/**
 * 2020年:  06 月:  08 日:  18小时:  58分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubePaneVideoHandler extends AbstractRetryHandler {
    VBox containerVBox;
    private DataInsert dataInsertDownloader;
    public static final String YOUTUBE_EMBED_URL = "https://www.youtube.com/embed/%s?autoplay=1";
    public static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%s";

    public YoutubePaneVideoHandler(VBox containerVBox, DataInsert dataInsertDownloader) {
        this.containerVBox = containerVBox;
        this.dataInsertDownloader = dataInsertDownloader;
    }


    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        VideoDescription videoDescription = (VideoDescription) data.get("videoDescription");

        VideoInfo videoInfo = convert(videoDescription, handlerDataData);
        Platform.runLater(() -> {
            ObservableList<Node> children = containerVBox.getChildren();
            children.clear();

            ListItemVBox itemVBox = new ListItemVBox(videoInfo, dataInsertDownloader);

            children.add(itemVBox);
        });

    }

    private VideoInfo convert(VideoDescription videoDescription, Object handlerDataData) {


        String title = videoDescription.getTitle();
        String author = videoDescription.getAuthor();
        String channelId = videoDescription.getChannelId();

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setName(title);
        videoInfo.setVideoId(videoDescription.getVideoId());
        videoInfo.setViewCountText(videoDescription.getViewCount());
        videoInfo.setPublishedTimeText(videoDescription.getPublishDate());
        videoInfo.setEmbedUrl(String.format(YOUTUBE_EMBED_URL, videoInfo.getVideoId()));
        videoInfo.setPlayUrl(String.format(YOUTUBE_VIDEO_URL, videoInfo.getVideoId()));
        videoInfo.getData().put("originUrl", handlerDataData.toString());
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
