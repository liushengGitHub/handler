package liusheng.handler.http.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 年: 2020  月: 02 日: 05 小时: 18 分钟: 40
 * 用户名: LiuSheng
 */

public class PatternUtils {
    public static List<String> searchKVs(String content, String key) {
        String regex = String.format("\"%s\"\\s*:\\s*((\"(.+?)\")|(\\d*)|(\\d+\\.\\d+))", key);

        List<String> list = new LinkedList<>();
        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            int start = group.indexOf(":");
            int i = group.indexOf("\"", start);
            String str = null;
            if (i != -1) {
                str = matcher.group(3);
            } else {
                i = group.indexOf(".", start);

                if (i == -1) {
                    str = matcher.group(4);
                } else {
                    str = matcher.group(5);
                }
            }
            list.add(str);
        }

        return list;
    }

    public static String searchKV(String content, String key) {
        String regex = String.format("\"%s\"\\s*:\\s*((\"(.+?)\")|(\\d*)|(\\d+\\.\\d+))", key);

        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            int start = group.indexOf(":");
            int i = group.indexOf("\"", start);
            if (i != -1) {
                return matcher.group(3);
            } else {
                i = group.indexOf(".", start);

                if (i == -1) {
                    return matcher.group(4);
                } else {
                    return matcher.group(5);
                }
            }
        }

        return null;
    }
    public static String searchKVAttr(String content, String key) {
        String regex = String.format("%s\\s*=\\s*((\"(.+?)\")|(\\d*)|(\\d+\\.\\d+))", key);

        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            int start = group.indexOf(":");
            int i = group.indexOf("\"", start);
            if (i != -1) {
                return matcher.group(3);
            } else {
                i = group.indexOf(".", start);

                if (i == -1) {
                    return matcher.group(4);
                } else {
                    return matcher.group(5);
                }
            }
        }

        return null;
    }
}
