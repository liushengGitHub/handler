package liusheng.handler.youtube.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.youtube.handler.entity.VideoDescription;
import liusheng.handler.http.utils.GsonUtils;
import liusheng.handler.http.utils.ScriptEngineUtils;
import liusheng.handler.youtube.utils.YoutubePatternUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.IOException;
import java.util.Map;

/**
 * 2020年:  05 月:  04 日:  13小时:  14分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class YoutubeDescriptionHandler extends AbstractRetryHandler {
    private static String step1Str = "";
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {
            step1Str = IOUtils.toString(YoutubeDescriptionHandler
                    .class.getClassLoader().getResourceAsStream("videoDescription/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(YoutubeDescriptionHandler
                    .class.getClassLoader().getResourceAsStream("videoDescription/step2.js"), "UTF-8");
            step3Str = IOUtils.toString(YoutubeDescriptionHandler
                    .class.getClassLoader().getResourceAsStream("videoDescription/step3.js"), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void handle0(Object object, Object o, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        Document document = (Document) data.get("document");

        String script = document.select("script")
                .stream()
                .map(Element::html)
                .filter(html ->
                        html.contains("ytplayer.config"))
                .findFirst().orElseThrow(UnsupportedOperationException::new);
        ScriptEngine engine = ScriptEngineUtils.getEngineByJs();

        engine.eval(step1Str);

        engine.eval(script);
        engine.eval(step2Str);

        Invocable invocable = (Invocable) engine;

        String listStr = (String) invocable.invokeFunction("getList");

        VideoDescription videoDescription = GsonUtils.gson().fromJson(listStr, VideoDescription.class);

        data.put("videoDescription", videoDescription);
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        return YoutubePatternUtils.isPage(handlerDataData.toString());
    }
}
