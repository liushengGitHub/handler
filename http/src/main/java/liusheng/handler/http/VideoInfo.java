package liusheng.handler.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 年: 2020  月: 03 日: 24 小时: 11 分钟: 36
 * 用户名: LiuSheng
 */

public class VideoInfo {
    private String videoId;
    private String name;
    private String imgSrc;
    private String playlistId;
    private String publishedTimeText;
    private String viewCountText;
    private Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getData() {
        return data;
    }

    private ChannelInfo channelInfo;

    private String embedUrl;

    private String playUrl;

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    public String getPublishedTimeText() {
        return publishedTimeText;
    }

    public void setPublishedTimeText(String publishedTimeText) {
        this.publishedTimeText = publishedTimeText;
    }

    public String getViewCountText() {
        return viewCountText;
    }

    public void setViewCountText(String viewCountText) {
        this.viewCountText = viewCountText;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "videoId='" + videoId + '\'' +
                ", name='" + name + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoInfo videoInfo = (VideoInfo) o;
        return Objects.equals(videoId, videoInfo.videoId) &&
                Objects.equals(name, videoInfo.name) &&
                Objects.equals(imgSrc, videoInfo.imgSrc) &&
                Objects.equals(playlistId, videoInfo.playlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, name, imgSrc, playlistId);
    }
}
