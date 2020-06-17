package liusheng.handler.youtube.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.HandlerData;
import liusheng.handler.youtube.handler.entity.VideoDescription;
import liusheng.handler.http.utils.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * 2020年:  05 月:  26 日:  22小时:  20分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeSurfaceHandler extends AbstractRetryHandler {
    private String dir;

    public YoutubeSurfaceHandler(String dir) {
        this.dir = dir;
    }

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        VideoDescription videoDescription = (VideoDescription) handlerContext.data().get("videoDescription");

        String title = StringUtils.fileNameHandle(videoDescription.getTitle());
        File dirFile = getDir(origin);
        File file = new File(dirFile, title + ".png");
        if (file.exists()) {
            return;
        }
        Optional.ofNullable(videoDescription.getThumbnail())
                .map(VideoDescription.ThumbnailBean::getThumbnails)
                .filter(list -> list.size() > 0)
                .map(list -> list.get(list.size() - 1))
                .map(VideoDescription.ThumbnailBean.ThumbnailsBean::getUrl)
                .ifPresent(url -> {
                    try {
                        IOUtils.write(IOUtils
                                .toByteArray(new URL(url)), new FileOutputStream(
                                file
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    private File getDir(Object origin) {
        String dirName = null;
        if (origin instanceof HandlerData) {
            HandlerData handlerData = (HandlerData) origin;
            Object name = handlerData.getState().get("channelName");
            if (Objects.nonNull(name)) {
                dirName = name.toString();
            }
        }
        File dirFile = StringUtils.isEmpty(dirName) ? new File(dir) : new File(dir, dirName);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return dirFile;
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Object videoDescription = handlerContext.data().get("videoDescription");
        return Objects.nonNull(videoDescription) && (videoDescription instanceof VideoDescription);
    }
}
