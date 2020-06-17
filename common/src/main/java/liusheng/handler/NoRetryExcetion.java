package liusheng.handler;

/**
 * 2020年:  06 月:  09 日:  08小时:  13分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class NoRetryExcetion extends Exception {
    public NoRetryExcetion(String message) {
        super(message);
    }

    public NoRetryExcetion() {
        super();
    }
}
