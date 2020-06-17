package liusheng.handler.soundcloud;

import liusheng.handler.http.utils.GsonUtils;
import liusheng.handler.soundcloud.handler.entity.SoundInfo;
import liusheng.handler.http.utils.OkHttpClientUtils;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;


/**
 * 2020年:  05 月:  27 日:  14小时:  30分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoundCloudUserExtractor {
    private static String CLIENT_ID = "TccDegWC8CpAf2tJsV335VGFRkSPCCF3";
    public static final String LIST_URL = "https://api-v2.soundcloud.com/stream/users/%s?client_id=TccDegWC8CpAf2tJsV335VGFRkSPCCF3&limit=20&offset=0&linked_partitioning=1&app_version=1590494738&app_locale=en";
    private String userId;
    private String url;

    public SoundCloudUserExtractor(String userId) {
        this.userId = userId;
        url = String.format(LIST_URL, userId);
    }

    public SoundInfo init() throws IOException {
        if (StringUtils.isBlank(url)) return null;
        ResponseBody body = OkHttpClientUtils
                .httpClient()
                .newCall(new Request.Builder()
                        .url(url)
                        .build()).execute().body();
        return format(body);
    }

    private SoundInfo format(ResponseBody body) throws IOException {
        String string = body.string();
        SoundInfo soundInfo = GsonUtils.gson()
                .fromJson(string, SoundInfo.class);
        if (Objects.isNull(soundInfo)) {
            url = null;
        } else {
            url = soundInfo.getNext_href();
        }
        if (StringUtils.isNotBlank(url)) {
            if (url.indexOf("?") != -1) {
                url += "&";
            } else {
                url += "?";
            }
            url += "client_id=" + CLIENT_ID;
        }
        return soundInfo;
    }

    public SoundInfo next() throws IOException {
        return init();
    }
}
