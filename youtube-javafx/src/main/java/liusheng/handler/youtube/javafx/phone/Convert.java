package liusheng.handler.youtube.javafx.phone;

import com.luciad.imageio.webp.WebPReadParam;
import io.github.biezhi.webp.WebpIO;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 2020年:  06 月:  08 日:  09小时:  30分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Convert {

    public synchronized static void convert(File source , File target) throws IOException {

        WebpIO.create()
                .toNormalImage(source,target);

    }
}
