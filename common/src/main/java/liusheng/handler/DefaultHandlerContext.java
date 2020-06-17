package liusheng.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 2020年:  05 月:  04 日:  10小时:  50分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DefaultHandlerContext implements HandlerContext {
    private Handler handler;
    Pipeline pipeline;

    public DefaultHandlerContext(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    private Map<String, Object> data = new HashMap<>();

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Handler handler() {
        return handler;
    }

    @Override
    public Map<String, Object> data() {
        return data;
    }

    @Override
    public Pipeline pipeline() {
        return pipeline;
    }
}
