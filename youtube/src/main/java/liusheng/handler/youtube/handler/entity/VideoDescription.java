package liusheng.handler.youtube.handler.entity;

import java.util.List;

/**
 * 2020年:  05 月:  04 日:  13小时:  36分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class VideoDescription  {
    /**
     * videoId : c2pYQ2PcVnE
     * title : Nightcore - 人間だった ♫(Lyrics)
     * lengthSeconds : 240
     * keywords : ["日文"]
     * channelId : UCj7FqAeSxSO7X-8yIRvNOQw
     * isOwnerViewing : false
     * shortDescription : ♡感謝你留下喜歡和訂閱♡
     打開鈴鐺接收最新消息→ 🔔
     訂閱頻道：https://goo.gl/4TLiU5

     ♪ 親親2o音樂LîvË【Nightcore】♪
     ►https://www.youtube.com/channel/UCj7FqAeSxSO7X-8yIRvNOQw?disable_polymer=true
     ►https://www.facebook.com/20MusicLive

     ♫ 歌手：Rinana
     ♫ 歌名：人間だった
     ♫ 歌曲：https://youtu.be/3jmztQnb7Zs

     ★illustration：https://www.pixiv.net/artworks/79224995
     ◎ Artwork by：きさらぎゆり
     ● https://www.pixiv.net/users/3197898
     ● https://kisaragi-yuri.tumblr.com/
     ● https://twitter.com/kisaragi_h29

     #親親2o音樂LîvË#Nightcore

     ©️ COPYRIGHT / LICENSING
     If any producer/label/artist has an issue with any of the songs or pictures please send me an e-mail to (20musiclive@gmail.com) and the video will be taken down immediately.
     * isCrawlable : true
     * thumbnail : {"thumbnails":[{"url":"https://i.ytimg.com/vi/c2pYQ2PcVnE/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCewYRbNg1jNFKe9Zc14r8HQu-ugg","width":168,"height":94},{"url":"https://i.ytimg.com/vi/c2pYQ2PcVnE/hqdefault.jpg?sqp=-oaymwEYCMQBEG5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAXST9WDudeHsxU6OJgoNQW4LcrJQ","width":196,"height":110},{"url":"https://i.ytimg.com/vi/c2pYQ2PcVnE/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLBidhFT2SZ8SirDCK3e1aztt2sK-A","width":246,"height":138},{"url":"https://i.ytimg.com/vi/c2pYQ2PcVnE/hqdefault.jpg?sqp=-oaymwEZCNACELwBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLBZytzxKEVsrlhz09WhnrLjEOBo6A","width":336,"height":188},{"url":"https://i.ytimg.com/vi_webp/c2pYQ2PcVnE/maxresdefault.webp","width":1920,"height":1080}]}
     * averageRating : 4.9946017
     * allowRatings : true
     * viewCount : 23399
     * author : 親親2o音樂LîvË【Nightcore】
     * isPrivate : false
     * isUnpluggedCorpus : false
     * isLiveContent : false
     */

    private String videoId;
    private String title;
    private String lengthSeconds;
    private String channelId;
    private boolean isOwnerViewing;
    private String shortDescription;
    private boolean isCrawlable;
    private ThumbnailBean thumbnail;
    private double averageRating;
    private boolean allowRatings;
    private String viewCount;
    private String author;
    private String avatorUrl;
    private boolean isPrivate;
    private boolean isUnpluggedCorpus;
    private boolean isLiveContent;
    private List<String> keywords;

    private String publishDate;


    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAvatorUrl() {
        return avatorUrl;
    }

    public void setAvatorUrl(String avatorUrl) {
        this.avatorUrl = avatorUrl;
    }

    public VideoDescription() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(String lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public boolean isIsOwnerViewing() {
        return isOwnerViewing;
    }

    public void setIsOwnerViewing(boolean isOwnerViewing) {
        this.isOwnerViewing = isOwnerViewing;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isIsCrawlable() {
        return isCrawlable;
    }

    public void setIsCrawlable(boolean isCrawlable) {
        this.isCrawlable = isCrawlable;
    }

    public ThumbnailBean getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailBean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isAllowRatings() {
        return allowRatings;
    }

    public void setAllowRatings(boolean allowRatings) {
        this.allowRatings = allowRatings;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isIsUnpluggedCorpus() {
        return isUnpluggedCorpus;
    }

    public void setIsUnpluggedCorpus(boolean isUnpluggedCorpus) {
        this.isUnpluggedCorpus = isUnpluggedCorpus;
    }

    public boolean isIsLiveContent() {
        return isLiveContent;
    }

    public void setIsLiveContent(boolean isLiveContent) {
        this.isLiveContent = isLiveContent;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public static class ThumbnailBean {
        private List<ThumbnailsBean> thumbnails;

        public List<ThumbnailsBean> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<ThumbnailsBean> thumbnails) {
            this.thumbnails = thumbnails;
        }

        public static class ThumbnailsBean {
            /**
             * url : https://i.ytimg.com/vi/c2pYQ2PcVnE/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCewYRbNg1jNFKe9Zc14r8HQu-ugg
             * width : 168
             * height : 94
             */

            private String url;
            private int width;
            private int height;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
