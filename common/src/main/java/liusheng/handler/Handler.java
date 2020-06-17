package liusheng.handler;

/**
 * 2020年:  05 月:  04 日:  10小时:  39分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public interface Handler {

    void handle(Object object,HandlerContext handlerContext) throws Exception;

    boolean support(Object o,HandlerContext handlerContext) throws Exception;
}
