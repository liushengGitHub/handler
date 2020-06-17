package liusheng.handler.youtube.handler;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

/**
 * 年: 2020  月: 03 日: 29 小时: 20 分钟: 56
 * 用户名: LiuSheng
 */

public class TopVideoExtractor extends ChannelVideoExtractor {
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {

            step2Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("topVideo/step2.js"), "UTF-8");

            step3Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("topVideo/step3.js"), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public TopVideoExtractor(String url) {
        super(url);
    }

    @Override
    protected String getStep2Str() {

        return step2Str;
    }
    @Override
    protected String getStep3Str() {

        return step3Str;
    }
}
