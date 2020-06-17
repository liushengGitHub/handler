package liusheng.handler.youtube.javafx;

import javafx.scene.web.WebView;
import javafx.stage.Stage;
import liusheng.handler.youtube.javafx.phone.DownloadPane;

import java.util.LinkedList;
import java.util.List;

/**
 * 2020年:  06 月:  07 日:  19小时:  55分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class NodeUtils {
    private static Stage primaryStage;
    private static DownloadPane downloadPane;

    private static List<WebView> webViews = new LinkedList<>();

    public static List<WebView> getWebViews() {
        return webViews;
    }


    public static DownloadPane getDownloadPane() {
        return downloadPane;
    }

    public static void setDownloadPane(DownloadPane downloadPane) {
        NodeUtils.downloadPane = downloadPane;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        NodeUtils.primaryStage = primaryStage;
    }
}
