package liusheng.handler.youtube.handler;

import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 2020年:  05 月:  04 日:  12小时:  55分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DefaultYouTubeExtractor extends AbstractYouTubeExtractor {
    private String pageHtml ;

    public String getPageHtml() {
        return pageHtml;
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml;
    }

    @Override
    protected Reader getAllLineFromCache(String url) throws IOException {

        if (StringUtils.isBlank(pageHtml)) {
            pageHtml = getResponse(url).body().string();
        }
        return new StringReader(pageHtml);
    }

    @Override
    protected Reader getAllLine(String hlsvp) throws IOException {
        Response response = getResponse(hlsvp);
        return new StringReader(response.body().string());
    }

    private Response getResponse(String hlsvp) throws IOException {
        return YoutubeRetrofitUtils.getOkHttpClient()
                .newCall(new Request.Builder()
                        .get()
                        .url(hlsvp)
                        .build()).execute();
    }

    @Override
    protected String getVideoInfoLine(String ytInfoUrl) throws IOException {
        Response response = getResponse(ytInfoUrl);
        return response.body().string();
    }
}
