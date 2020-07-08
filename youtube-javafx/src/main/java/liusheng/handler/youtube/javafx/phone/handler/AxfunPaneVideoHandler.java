package liusheng.handler.youtube.javafx.phone.handler;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.DataInsert;
import liusheng.handler.HandlerContext;
import liusheng.handler.acfun.handler.entity.AVEntity;
import liusheng.handler.bilibili.handler.entity.BilibiliVideoInfo;
import liusheng.handler.bilibili.utils.BilibiliPatternUtils;
import liusheng.handler.http.ChannelInfo;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.javafx.phone.ListItemVBox;

import java.util.Map;

/**
 * 2020年:  06 月:  08 日:  18小时:  58分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class AxfunPaneVideoHandler extends AbstractRetryHandler {
    private static final String ACFUN_EMBED_URL = "https://www.acfun.cn/player/ac%s";
    private static final String ACFUN_VIDEO_URL = "https://www.acfun.cn/v/ac%s";
    VBox containerVBox;
    private DataInsert dataInsertDownloader;

    public AxfunPaneVideoHandler(VBox containerVBox, DataInsert dataInsertDownloader) {
        this.containerVBox = containerVBox;
        this.dataInsertDownloader = dataInsertDownloader;
    }


    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        AVEntity avEntity = (AVEntity) data.get("avEntity");

        VideoInfo videoInfo = convert(avEntity, handlerDataData);
        Platform.runLater(() -> {
            ObservableList<Node> children = containerVBox.getChildren();
            children.clear();

            ListItemVBox itemVBox = new ListItemVBox(videoInfo, dataInsertDownloader);

            children.add(itemVBox);
        });

    }

    private VideoInfo convert(AVEntity avEntity, Object handlerDataData) {

        /**
         *
         * <iframe style="min-width: 500px;min-height: 300px"   src="https://www.acfun.cn/player/ac16184862" id="ACPlayer-re"  scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"></iframe>
         */

        String title = avEntity.getTitle();
        String videoId = avEntity.getCurrentVideoId() + "";

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.getData().put("originUrl", handlerDataData.toString());
        videoInfo.setEmbedUrl(String.format(ACFUN_EMBED_URL,videoId));
        videoInfo.setPlayUrl(String.format(ACFUN_VIDEO_URL, videoId));
        videoInfo.setName(title);
        videoInfo.setVideoId(videoId);
        videoInfo.setViewCountText(avEntity.getViewCountShow());
        videoInfo.setPublishedTimeText(avEntity.getCreateTime() + "");
        videoInfo.setImgSrc(avEntity.getCoverUrl());
        AVEntity.UserBean user = avEntity.getUser();
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setChannelId(user.getId());
        channelInfo.setImgSrc(user.getHeadUrl());
        channelInfo.setName(user.getName());
        videoInfo.setChannelInfo(channelInfo);
        return videoInfo;
    }


    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return handlerDataData.toString().startsWith("https://www.acfun.cn/");
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }
}
