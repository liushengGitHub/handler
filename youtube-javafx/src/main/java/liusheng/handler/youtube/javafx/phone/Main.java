package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import liusheng.handler.BootStrap;
import liusheng.handler.DataInsert;
import liusheng.handler.Handler;
import liusheng.handler.HandlerContext;
import liusheng.handler.bilibili.handler.*;
import liusheng.handler.bilibili.utils.BilibiliPatternUtils;
import liusheng.handler.youtube.javafx.phone.handler.BilibiliSubVideoHandler;
import liusheng.handler.youtube.FileRetryFailurerHandler;
import liusheng.handler.youtube.handler.*;
import liusheng.handler.youtube.javafx.NodeUtils;
import liusheng.handler.youtube.javafx.phone.handler.*;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2020年:  06 月:  07 日:  19小时:  47分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Main extends Application {

    private ExecutorService mainExecutorService;
    private ExecutorService helpExecutorService;

    @Override
    public void start(Stage primaryStage) throws Exception {


        NodeUtils.setPrimaryStage(primaryStage);

        VBox main = new VBox();
        VBox mainBack = new VBox();
        HeaderHBox headerHBox = new HeaderHBox();

        headerHBox.setPrefHeight(25);
        headerHBox.setPrefWidth(300);
        headerHBox.setStyle("-fx-background-color: transparent");
        SearchHBox searchHBox = new SearchHBox();

        searchHBox.setPrefHeight(50);
        searchHBox.setPrefWidth(300);
        searchHBox.setStyle("-fx-background-color: transparent");

        VBox middle = new VBox();

        middle.setPrefSize(300, 450);
        middle.setStyle("-fx-background-color: transparent");

        VBox containerVBox = new VBox();
        containerVBox.setPrefSize(300, 400);

        MenuHBox menuHBox = new MenuHBox(middle);
        menuHBox.getMainVBox().getChildren().addAll(searchHBox, containerVBox);
        menuHBox.setPrefSize(300, 45);

        main.getChildren().addAll(headerHBox, middle, menuHBox);

        DownloadPane downloadPane = new DownloadPane();

        menuHBox.getDownloadVBox().getChildren().add(downloadPane);

        SettingVBox settingVBox = new SettingVBox();
        settingVBox.setPrefSize(300, 450);
        menuHBox.getSettingBox().getChildren().add(settingVBox);

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("chuyin.jpg");
        Background background = new Background(new BackgroundImage(new Image(inputStream), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        mainBack.setBackground(background);

        main.setStyle("-fx-background-color: transparent");
        mainBack.getChildren().addAll(main);

        AnchorPane anchorPane = new AnchorPane();

        VBox colorBack = new VBox();
        mainBack.setPrefSize(300, 520);
        main.setPrefSize(300, 520);
        colorBack.setPrefSize(300, 520);
        colorBack.setStyle("-fx-background-color: gray");
        colorBack.setOpacity(0.5);
        anchorPane.getChildren().addAll(mainBack, colorBack, main);


        Scene scene = new Scene(anchorPane, 300, 520);
        scene.getStylesheets().add("style.css");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

       /* MainContextMenu mainContextMenu = new MainContextMenu();
        mainContextMenu.addNode(new Label("211212"));
        mainContextMenu.addNode(new Label("211212"));
        mainContextMenu.addNode(new Label("211212"));
        anchorPane.setOnMouseClicked(e->{
            if (e.getButton() == MouseButton.SECONDARY) {
                mainContextMenu.show(e.getScreenX(),e.getScreenY());
            }
        });*/

        mainExecutorService = Executors.newFixedThreadPool(2);
        helpExecutorService = Executors.newCachedThreadPool();

        DataInsert dataInsertDownloader = initBootStrapDownloader(downloadPane);

        DataInsert dataInsert = initBootStrap(containerVBox, dataInsertDownloader);

        searchHBox.getSearchButton().setOnAction(e -> {

            JFXTextField searchText = searchHBox.getSearchText();
            String text = searchText.getText();
            if (StringUtils.isBlank(text)) {
                return;
            }

            ObservableList<Node> list = containerVBox.getChildren();
            NodeUtils.getWebViews().stream()
                    .map(WebView::getEngine)
                    .forEach(e1 -> e1.loadContent(""));
            NodeUtils.getWebViews().clear();

            list.clear();
            list.addAll(new Label("正在加载中"));
            dataInsert.addData(text);
            searchText.requestFocus();
        });

        primaryStage.show();
    }

    private DataInsert initBootStrapDownloader(DownloadPane downloadPane) {
        BootStrap bootStrap = new BootStrap();
        FileRetryFailurerHandler fileRetryFailurerHandler = new FileRetryFailurerHandler("F:\\mp3\\" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + ".txt");
        NodeUtils.setDownloadPane(downloadPane);
        DataInsert dataInsert = bootStrap
                .mainExecutorService(Executors.newFixedThreadPool(3))
                .helpExecutorService(helpExecutorService)
                .addHandler(new YoutubeVideoDownloaderPaneAddHandler("F:\\mp3", false))
              //  .addHandler(new BilibiliVideoDownloaderPaneAddHandler("F:\\mp3", false))
                .addHandler(new YoutubeDocumentHandler(fileRetryFailurerHandler))
                .addHandler(new YoutubeDescriptionHandler())
                .addHandler(new StateToDataHandler())
                .addHandler(new BilibiliPageVideoHandler())
                .addHandler(new BilibiliSubVideoHandler("80"))
                .addHandler(new BilibiliDownloaderHandler("F:\\mp4"))
                .addHandler(new YoutubeVideoDownloaderPaneAddHandler("F:\\mp3", true))
                .addHandler(new YoutubeVideoHandler(fileRetryFailurerHandler))
                .addHandler(new YoutubeDownloaderHandler(fileRetryFailurerHandler, 1, 1080, "F:\\mp3"))
                .start();
        DataInsertUtils.setDataInsertDownloader(dataInsert);
        return dataInsert;
    }

    private DataInsert initBootStrap(VBox containerVBox, DataInsert dataInsertDownloader) {
        BootStrap bootStrap = new BootStrap();
        FileRetryFailurerHandler fileRetryFailurerHandler = new FileRetryFailurerHandler("F:\\mp3\\" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + ".txt");

        DataInsert dataInsert = bootStrap
                .mainExecutorService(mainExecutorService)
                .helpExecutorService(helpExecutorService)
                .addHandler(new BilibiliPageHandler())
                .addHandler(new BilibiliSpaceHandler())
                .addHandler(new BilibiliPaneVideoHandler(containerVBox, dataInsertDownloader))
                .addHandler(new SearchUrlHandler())
                .addHandler(new YoutubeDocumentHandler(fileRetryFailurerHandler))
                .addHandler(new YoutubeDescriptionHandler())
                .addHandler(new YoutubePaneVideoHandler(containerVBox, dataInsertDownloader))
                // .addHandler(new YoutubeVideoHandler(fileRetryFailurerHandler))
                .addHandler(new ChannelVideoHandler(fileRetryFailurerHandler))
                .addHandler(new ChannelVideoHandler())
                .addHandler(new SearchVideoHandler())
                .addHandler(new ChannelInfoExtratorHandler(fileRetryFailurerHandler, containerVBox, dataInsertDownloader))
                //.addHandler(new TimedTextHandler())
                //.addHandler(new DownloadListenerHandler(new StandOutDownloadListener()))
                // .addHandler(new YoutubeDownloaderHandler(fileRetryFailurerHandler, 1, 1080, "F:\\mp3"))
                .start();
        DataInsertUtils.setMainDataInsert(dataInsert);
        return dataInsert;
    }


}
