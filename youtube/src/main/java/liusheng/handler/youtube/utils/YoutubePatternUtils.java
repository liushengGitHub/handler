package liusheng.handler.youtube.utils;

import liusheng.handler.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2020年:  05 月:  04 日:  12小时:  20分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubePatternUtils {
    //https://www.youtube.com/results?search_query=%E4%B8%87%E6%9C%89%E5%BC%95%E5%8A%9B
    private static final Pattern patYouTubePageLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)( |\\z|&)");
    private static final Pattern patYouTubeSearchPageLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/results\\?search_query=(.+?)( |\\z|&)");
    private static final Pattern patYouTubePageListLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)&list=(.+?)( |\\z|&)");
    private static final Pattern patYouTubePageChannelVideoLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/(channel|user)/[a-zA-Z0-9_-]+?/videos(\\?(.+?)( |\\z|&))?");

    public static boolean isPage(String url) {
        Assert.notNull(url, "参数不能是空");
        return patYouTubePageLink.matcher(url).matches();
    }

    public static String getVideoId(String url) {
        Assert.notNull(url, "参数不能是空");
        Matcher matcher = patYouTubePageLink.matcher(url);
        return matcher.find() ? matcher.group(3) : "";
    }

    public static boolean isList(String url) {
        Assert.notNull(url, "参数不能是空");
        return patYouTubePageListLink.matcher(url).matches();

    }

    public static boolean isChannelVideo(String url) {
        Assert.notNull(url, "参数不能是空");
        return patYouTubePageChannelVideoLink.matcher(url).matches();

    }

    public static boolean isSearch(String url) {
        return patYouTubeSearchPageLink.matcher(url).matches();
    }
}
