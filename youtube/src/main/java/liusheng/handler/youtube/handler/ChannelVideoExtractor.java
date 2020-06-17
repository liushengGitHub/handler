package liusheng.handler.youtube.handler;

import liusheng.handler.youtube.AbstractChannelVideoExtractor;
import liusheng.handler.youtube.handler.entity.NextKeyEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


/**
 * 年: 2020  月: 03 日: 23 小时: 17 分钟: 40
 * 用户名: LiuSheng
 */

public class ChannelVideoExtractor extends AbstractChannelVideoExtractor<NextKeyEntity> {

    private static String step1Str = "";
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {
            step1Str = IOUtils.toString(liusheng.handler.youtube.handler.ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("channelVideo/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(liusheng.handler.youtube.handler.ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("channelVideo/step2.js"), "UTF-8");
            step3Str = IOUtils.toString(liusheng.handler.youtube.handler.ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("channelVideo/step3.js"), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ChannelVideoExtractor(String url) {
        super(url);
    }

    @Override
    protected void handleNext(String next) {
        if (StringUtils.isBlank(next.trim())) {
            nextKeyEntity = null;
        } else {
            nextKeyEntity = gson.fromJson(next, NextKeyEntity.class);
            nextKeyEntity.setCtoken(nextKeyEntity.getContinuation());
        }
    }

    protected String getStep2Str() {
        return step2Str;
    }

    protected String getStep1Str() {
        return step1Str;
    }
    protected String getStep3Str() {
        return step3Str;
    }
}
