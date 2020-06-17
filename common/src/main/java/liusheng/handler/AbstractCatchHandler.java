package liusheng.handler;

/**
 * 2020年:  05 月:  25 日:  22小时:  10分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public abstract class AbstractCatchHandler implements Handler {
    @Override
    public void handle(Object object, HandlerContext handlerContext) throws Exception {
        try {
            handle0(object, handlerContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void handle0(Object object, HandlerContext handlerContext) throws Exception;

}
