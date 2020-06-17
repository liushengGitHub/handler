package liusheng.handler.youtube.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.http.utils.ScriptEngineUtils;
import liusheng.handler.youtube.utils.YoutubePatternUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 年: 2020  月: 03 日: 28 小时: 22 分钟: 46
 * 用户名: LiuSheng
 */

public class YoutubeListHandler extends AbstractRetryHandler {
    private static String step1Str = "";
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {
            step1Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("listVideo/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("listVideo/step2.js"), "UTF-8");
            step3Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("listVideo/step3.js"), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        Document document = (Document) data.get("document");
        String script = document.select("script")
                .stream()
                .map(Element::html)
                .filter(html ->
                        html.contains("window[\"ytInitialData\"]"))
                .findFirst().orElseThrow(UnsupportedOperationException::new);
        ScriptEngine engine = ScriptEngineUtils.getEngineByJs();

        engine.eval(step1Str);

        engine.eval(script);
        engine.eval(step2Str);

        Invocable invocable = (Invocable) engine;

        String listStr = (String) invocable.invokeFunction("getList");

        Gson gson = new Gson();

        List<VideoInfo> videoInfoList = gson.fromJson(listStr, new TypeToken<List<VideoInfo>>() {
        }.getType());
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isList(handlerDataData.toString());
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }
}
