package liusheng.handler.http.utils;

import liusheng.handler.HandlerContext;
import liusheng.handler.http.DownloadListener;
import liusheng.handler.http.DownloaderController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 2020年:  05 月:  27 日:  12小时:  33分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DownloadUtils {
    public static void copy(HandlerContext handlerContext, DownloaderController downloaderController, InputStream inputStream,
                            OutputStream outputStream, String suffix, DownloadListener downloadListener, AtomicLong current, AtomicLong total) throws IOException {
        String key = "startIndex" + suffix;
        Map<String, Object> data = handlerContext.data();
        long size = Objects.isNull(data.get(key)) ? 0 : (long) data.get(key);
        int length = -1;
        byte[] bytes = new byte[102400];
        boolean b = Objects.nonNull(downloaderController);
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
            size += length;
            current.addAndGet(length);
            if (Objects.nonNull(downloadListener)) {
                downloadListener.listen(current.get(), total.get());
            }
            data.put(key, size);
            if (b && downloaderController.getState() == DownloaderController.EXCEPTION  && downloaderController.getState() == DownloaderController.CANCEL) {
                break;
            }
        }
        //100629776
        outputStream.close();
        inputStream.close();
    }

    public static void copy(HandlerContext handlerContext, DownloaderController downloaderController, InputStream inputStream,
                            OutputStream outputStream, String suffix, AtomicLong current, AtomicLong total) throws IOException {
        copy(handlerContext, downloaderController, inputStream, outputStream, suffix, null, current, total);
    }

    public static String getUrl(String url) {
        url = url.replace("\\/", "/");
        return url;
    }

    public static void downloadFile(File file, File targetFile, String url, HandlerContext handlerContext, String suffix,
                                    DownloaderController downloaderController, AtomicLong current, AtomicLong total) throws Exception {
        downloadFile(file, targetFile, url, handlerContext, suffix, downloaderController, OkHttpClientUtils.httpClient(), current, total);
    }

    public static void downloadFile(File file, File targetFile, String url, HandlerContext handlerContext, String suffix,
                                    DownloaderController downloaderController, OkHttpClient httpClient, AtomicLong current, AtomicLong total) throws Exception {
        if (targetFile.exists()) {
            TimeUnit.SECONDS.sleep(1);
            return;
        }
        Map<String, Object> data = handlerContext.data();
        String key = "length" + suffix;
        Long length = (Long) data.get(key);
        DownloadListener downloadListener = (DownloadListener) data.get("downloadListener");
        long fileLength = file.length();

        boolean exists = file.exists();

        // 如果文件已经存在，加入初始下载长度
        String startIndexKey = "startIndex" + suffix;
        if (exists && fileLength > 0 && data.get(startIndexKey) == null) {
            data.put(startIndexKey, fileLength);
        }

        Response response = getResponse(data, suffix, getUrl(url), httpClient);
        // 保存在该文件的初始长度
        if (Objects.isNull(length)) {
            if (exists) {
                length = getResponse(data, suffix, getUrl(url), httpClient).body().contentLength();
            } else {
                length = response.body().contentLength();
            }
            data.put(key, length);
            total.addAndGet(length);
            data.put("total", total);
            data.put("current", current);
        }


        InputStream inputStream = response.body().byteStream();
        FileOutputStream outputStream = new FileOutputStream(file, true);

        copy(handlerContext, downloaderController, inputStream, outputStream, suffix, downloadListener, current, total);

        response.close();
    }

    private static Response getResponse(Map<String, Object> data, String suffix, String url, OkHttpClient httpClient) throws IOException {
        StringBuilder sb = handleRange(data, url, suffix);


        Response response = getResponse(sb.toString(), httpClient);

        System.out.println("开始： " + url);
        return response;
    }

    private static Response getResponse(String sb, OkHttpClient httpClient) throws IOException {
        return httpClient
                .newCall(new Request.Builder()
                        .url(sb.toString())
                        .build()).execute();
    }

    private static StringBuilder handleRange(Map<String, Object> data, String url, String suffix) {
        if (suffix == null) suffix = "";
        Long startIndex = (Long) data.get("startIndex" + suffix);
        Long endIndex = (Long) data.get("endIndex" + suffix);
        StringBuilder sb = new StringBuilder(url);
        if (Objects.nonNull(startIndex)) {
            sb.append("&range=").append(startIndex).append("-");
            if (Objects.nonNull(endIndex)) {
                sb.append(endIndex);
            }
        }
        return sb;
    }
}
