package liusheng.handler.youtube.handler;

import com.google.gson.reflect.TypeToken;
import liusheng.handler.AbstractCatchHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.youtube.handler.entity.TextTranslate;
import liusheng.handler.http.utils.GsonUtils;
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
 * 2020年:  05 月:  04 日:  13小时:  24分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class TimedTextHandler extends AbstractCatchHandler {
    private static String step1Str = "";
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {
            step1Str = IOUtils.toString(TimedTextHandler
                    .class.getClassLoader().getResourceAsStream("timedText/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(TimedTextHandler
                    .class.getClassLoader().getResourceAsStream("timedText/step2.js"), "UTF-8");
            step3Str = IOUtils.toString(TimedTextHandler
                    .class.getClassLoader().getResourceAsStream("timedText/step3.js"), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    protected void handle0(Object object, HandlerContext handlerContext) throws Exception {
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
        List<TextTranslate> textTranslates = GsonUtils.gson().fromJson(listStr, new TypeToken<List<TextTranslate>>(){}.getType());
        data.put("textTranslates",textTranslates);
    }

    @Override
    public boolean support(Object o,HandlerContext handlerContext) throws Exception {
        return YoutubePatternUtils.isPage(o.toString());
    }
}
