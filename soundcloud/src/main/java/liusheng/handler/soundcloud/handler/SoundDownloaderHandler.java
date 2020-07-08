package liusheng.handler.soundcloud.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.HandlerData;
import liusheng.handler.http.DefaultDownloaderController;
import liusheng.handler.http.utils.DownloadUtils;
import liusheng.handler.http.utils.OkHttpClientUtils;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2020年:  05 月:  27 日:  12小时:  34分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoundDownloaderHandler extends AbstractRetryHandler {
    public static Pattern URL_PARTTERN = Pattern.compile("\"url\"\\s*:\"\\s*(http.*?)\"");

    private String dir;

    public SoundDownloaderHandler(String dir) {
        this.dir = dir;
    }

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        List<String> urls = (List<String>) data.get("urls");

        if (urls.isEmpty()) {
            return;
        }
        String fileName = (String) data.get("fileName");
        String dirName = null;
        if (origin instanceof HandlerData) {
            HandlerData handlerData = (HandlerData) origin;
            Object name = handlerData.getState().get("userName");
            if (Objects.nonNull(name)) {
                dirName = name.toString();
            }
        }


        String audioUrl = urls.stream()
                .filter(url -> url.contains("progressive"))
                .findFirst()
                .orElse(urls.get(0));
        String body = OkHttpClientUtils
                .httpClient()
                .newCall(new Request.Builder()
                        .url(audioUrl)
                        .build()).execute().body().string();
        Matcher matcher = URL_PARTTERN
                .matcher(body);

        if (matcher.find()) {
            String url = matcher.group(1);
            File dirFile = new File(dir);
            if (StringUtils.isNotBlank(dirName)) {
                dirFile = new File(dir, dirName);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
            }
            InputStream inputStream = OkHttpClientUtils
                    .httpClient()
                    .newCall(new Request.Builder().url(url).build())
                    .execute().body().byteStream();
            File file = new File(dirFile, fileName + ".mp3");
            System.out.println("开始" + file.getAbsolutePath().toString());
            if (file.exists()){
                inputStream.close();
                return;
            }
            DownloadUtils.copy(handlerContext, new DefaultDownloaderController(), inputStream, new FileOutputStream(file), "",
                    new AtomicLong(),new AtomicLong());
            System.out.println("结束" + file.getAbsolutePath().toString());
        }
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Map<String, Object> data = handlerContext.data();
        Object urls = data.get("urls");
        return Objects.nonNull(urls) && (urls instanceof List);
    }
}
