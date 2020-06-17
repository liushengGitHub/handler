package liusheng.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 2020年:  05 月:  26 日:  16小时:  01分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class HandlerData {
   private Object data;
   private Map<String, Object> state = new HashMap<>();

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public HandlerData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
