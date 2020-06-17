package liusheng.handler.http.utils;

public class StringUtils {
    public static String fileNameHandle(String name) {
        name = name.replaceAll("[\\\\:*<>?|/\\s\"]", "");
        return name;
    }

    public static boolean isEmpty(String name) {
        return name == null || name.length() == 0;
    }

    public static String htmlAbsolutionPath(String url) {
        if (url.startsWith("http") || url.startsWith("https")) return url;

        if (url.startsWith("//")) return "https:" + url;
        if (url.startsWith("://")) return "https" + url;

        return null;
    }

    public static String delelteHtmlTag(String content) {


        return content.replaceAll("(<.+?>)|(</.+?>)", "");
    }

    public static String urlToFileName(String url) {
        int start = url.lastIndexOf("/");
        if (start == -1) throw new IllegalArgumentException();
        String temp = url.substring(start + 1);
        return temp;
    }

    public static String urlProcess(String url,String parentUrl) {
        if (url.startsWith("http")) {
            return url;
        }
        if (url.startsWith("//")) {
            url = "http:" + url;
            return url;
        }
        if (url.startsWith("/")) {
            return getBaseURL(parentUrl) + url;
        }

        return getRelativeURL(parentUrl) + url;
    }
    public static String getRelativeURL(String url) {
        if (!url.startsWith("http")) {
            throw new IllegalArgumentException();
        }

        int index = url.lastIndexOf("/");
        if (index < 0) throw new IllegalArgumentException();
        return url.substring(0, index + 1);
    }
    public static String getBaseURL(String url) {
        if (!url.startsWith("http")) {
            throw new IllegalArgumentException();
        }
        int index = url.indexOf("//");

        if (index < 0) throw new IllegalArgumentException();

        int end = url.indexOf("/", index + 2);
        if (end < 0) throw new IllegalArgumentException();

        return url.substring(0, end);
    }
}
