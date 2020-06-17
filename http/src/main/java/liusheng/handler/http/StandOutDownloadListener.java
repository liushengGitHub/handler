package liusheng.handler.http;

/**
 * 2020年:  05 月:  30 日:  09小时:  46分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class StandOutDownloadListener implements DownloadListener {
    @Override
    public void listen(long current, long total) {
        System.out.println(current + "=" + total);
    }
}
