package liusheng.handler.soundcloud.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.DefaultPipeline;
import liusheng.handler.HandlerContext;
import liusheng.handler.HandlerData;
import liusheng.handler.http.utils.StringUtils;
import liusheng.handler.soundcloud.SoundCloudUserExtractor;
import liusheng.handler.soundcloud.handler.entity.SoundInfo;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2020年:  05 月:  27 日:  14小时:  17分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoundCloudUserHandler extends AbstractRetryHandler {
    public static final String URL_PARTTERN_REGEX = "soundcloud://users:(\\d+)";
    public static final Pattern URL_PARTTERN = Pattern.compile(URL_PARTTERN_REGEX);

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();

        Document document = (Document) data.get("document");

        String body = document.toString();

        Matcher matcher = URL_PARTTERN.matcher(body);
        if (!matcher.find()) {
            return;
        }

        String id = matcher.group(1);

        String title = document.title();
        if (StringUtils.isEmpty(title)) {
            title = id;
        }
        String fileName = StringUtils.fileNameHandle(title);
        SoundCloudUserExtractor userExtractor = new SoundCloudUserExtractor(id);

        SoundInfo next = null;
        List<SoundInfo> list = new LinkedList<>();
        while ((next = userExtractor.next()) != null) {
            list.add(next);
        }
        DefaultPipeline pipeline = (DefaultPipeline) handlerContext.pipeline();


        list.forEach(soundInfo -> {

                Optional
                        .ofNullable(soundInfo)
                        .map(SoundInfo::getCollection)
                        .ifPresent(collectionBeans -> {
                            collectionBeans.stream()
                                    .forEach(collectionBean -> {
                                        pipeline.getMainExecutorService().execute(() -> {
                                            SoundInfo.CollectionBean.TrackBean track = collectionBean.getTrack();
                                            if (Objects.nonNull(track)) {
                                                String permalink_url = track.getPermalink_url();
                                                try {
                                                    System.out.println(permalink_url);
                                                    HandlerData handlerData = new HandlerData(permalink_url);
                                                    handlerData.getState().put("userName", fileName);
                                                    handlerContext.pipeline()
                                                            .handle(handlerData);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    });
                        });
            });


        //soundcloud://users:31109873
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return Pattern.compile("https://soundcloud.com/.*?").matcher(handlerDataData.toString()).matches();
    }
}
