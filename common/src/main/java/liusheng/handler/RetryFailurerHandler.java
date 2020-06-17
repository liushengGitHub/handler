package liusheng.handler;

/**
 * 年: 2020  月: 05 日: 26 小时: 18 分钟: 48
 * 用户名: LiuSheng
 */

public interface RetryFailurerHandler {
     void handle(Object source,Throwable throwable) throws Exception;
}
