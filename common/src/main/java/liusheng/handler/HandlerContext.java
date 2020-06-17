package liusheng.handler;

import java.util.Map;

/**
 * 2020年:  05 月:  04 日:  10小时:  42分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public interface HandlerContext {
    Handler handler();
    // 存储中间数据
    Map<String, Object> data();
    Pipeline pipeline();
}
