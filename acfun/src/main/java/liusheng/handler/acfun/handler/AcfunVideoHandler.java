package liusheng.handler.acfun.handler;

import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.HandlerContext;
import liusheng.handler.acfun.handler.entity.AVEntity;
import liusheng.handler.http.utils.GsonUtils;
import liusheng.handler.http.utils.ScriptEngineUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.script.ScriptEngine;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 2020年:  06 月:  17 日:  22小时:  44分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class AcfunVideoHandler extends AbstractRetryHandler {
    private static String step1Str = "";
    private static String step2Str = "";


    static {
        try {
            step1Str = IOUtils.toString(AcfunVideoHandler
                    .class.getClassLoader().getResourceAsStream("ac/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(AcfunVideoHandler
                    .class.getClassLoader().getResourceAsStream("ac/step2.js"), "UTF-8");

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
                .filter(s -> s.contains("window.pageInfo"))
                .findFirst().orElse(null);

        if (StringUtils.isBlank(script)) {
            return;
        }

        ScriptEngine engine = ScriptEngineUtils.getEngineByJs();

        engine.eval(step1Str);
        engine.eval(script);
        engine.eval(step2Str);

        String json = (String) engine.get("json");

        AVEntity avEntity = GsonUtils.gson()
                .fromJson(json, AVEntity.class);

        data.put("avEntity", avEntity);
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Object document = handlerContext.data().get("document");
        return handlerDataData.toString().startsWith("https://www.acfun.cn/") && Objects.nonNull(document) && document instanceof Document;
    }

    @Override
    protected int getRetryCount() {
        return 1;
    }
}
