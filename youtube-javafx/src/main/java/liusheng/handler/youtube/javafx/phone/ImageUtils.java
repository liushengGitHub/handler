package liusheng.handler.youtube.javafx.phone;

import javafx.scene.image.Image;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2020年:  06 月:  08 日:  08小时:  08分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ImageUtils {
    private static Map<String, Image> imageMap = new ConcurrentHashMap<>();

    public static void put(String key, Image value) {
        imageMap.put(key, value);
    }

    public static Image get(String key) {
        if (key == null) return null;
        return imageMap.get(key);
    }

    public static Image saveImage(String urlStr) {
        return saveImage(urlStr, 40, 40);
    }

    public static Image saveImage(String urlStr, int width, int height) {
        Image image = ImageUtils.get(urlStr);
        if (Objects.isNull(image)) {
            image = new Image(urlStr, width, height, true, true);
            ImageUtils.put(urlStr, image);
        }
        return image;
    }
}
