package liusheng.handler.soundcloud.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2020年:  05 月:  27 日:  11小时:  06分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoundUrlHandler extends AbstractRetryHandler {

    public static final String URL_PARTTERN_REGEX = "\"url\"\\s*:\"\\s*(https://api-v2.soundcloud.com/media.*?)\"";
    public static final Pattern URL_PARTTERN = Pattern.compile(URL_PARTTERN_REGEX);

    private static String CLIENT_ID = "TccDegWC8CpAf2tJsV335VGFRkSPCCF3";

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();

        Document document = (Document) data.get("document");

        String body = document.toString();

        Matcher matcher = URL_PARTTERN.matcher(body);
        String title = document.title();
        if (StringUtils.isBlank(title)) {
            title = UUID.randomUUID().toString();
        }
        title = liusheng.handler.http.utils.StringUtils.fileNameHandle(title);

        List<String> urls = new ArrayList<>();
        while (matcher.find()) {

            String url = matcher.group(1);
            if (url.indexOf("?") != -1) {
                url += "&";
            } else {
                url += "?";
            }
            url += "client_id=" + CLIENT_ID;
            urls.add(url);
        }
        data.put("urls", urls);
        data.put("fileName", title);
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return Pattern.compile("https://soundcloud.com/.*?/.*?").matcher(handlerDataData.toString()).matches();
    }

    public static void main(String[] args) {
        String input = "\"url\":\"https://api-v2.soundcloud.com/media/soundcloud:tracks:756114190/9db01029-503d-49bd-8a08-6e3a5523c4ad/stream/hls?client_id=TccDegWC8CpAf2tJsV335VGFRkSPCCF3\"";

        System.out.println(input);
        Matcher matcher = URL_PARTTERN.matcher(
                input);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
