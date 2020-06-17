package liusheng.handler;

/**
 * 年: 2020  月: 05 日: 04 小时: 10 分钟: 47
 * 用户名: LiuSheng
 */

public interface Pipeline {
    void  addHandler(Handler handler);

    void handle(Object object) throws Exception;
}
