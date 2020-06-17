package liusheng.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * 2020年:  05 月:  04 日:  10小时:  48分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DefaultPipeline implements Pipeline {
    private ExecutorService mainExecutorService;
    private ExecutorService helpExecutorService;
    private DataInsert dataInsert;
    private List<Handler> handlers = new CopyOnWriteArrayList<>();

    public DefaultPipeline(ExecutorService mainExecutorService, ExecutorService helpExecutorService,DataInsert dataInsert) {
        this.mainExecutorService = mainExecutorService;
        this.helpExecutorService = helpExecutorService;
        this.dataInsert = dataInsert;
    }

    public DataInsert getDataInsert() {
        return dataInsert;
    }

    public ExecutorService getMainExecutorService() {
        return mainExecutorService;
    }

    public ExecutorService getHelpExecutorService() {
        return helpExecutorService;
    }

    public void addHandler(Handler handler) {
        Assert.notNull(handler,"Handler 不能是空");
        handlers.add(handler);
    }

    public void handle(Object object) throws Exception{
        DefaultHandlerContext handlerContext = new DefaultHandlerContext(this);
        Assert.notNull(object,"参数 不能是空");
        for (Handler handler : handlers) {
            if (handler.support(object,handlerContext)) {
                handlerContext.setHandler(handler);
                handler.handle(object,handlerContext);
            }
        }
    }
}
