package liusheng.handler.http.utils;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 2020年:  05 月:  04 日:  13小时:  37分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ScriptEngineUtils {

    private static ScriptEngineManager manager  = new ScriptEngineManager();

    public static ScriptEngine getEngineByJs() {
       return  manager.getEngineByExtension("js");
    }
}
