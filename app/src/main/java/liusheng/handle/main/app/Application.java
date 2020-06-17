package liusheng.handle.main.app;

import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import liusheng.handle.main.action.DragAction;

/**
 * 2020年:  05 月:  04 日:  14小时:  24分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Application extends javafx.application.Application {


    private DragAction dragAction = new DragAction();
    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox();

        root.setOnMousePressed(dragAction.getOnMousePressed(primaryStage));

        root.setOnMouseDragged(dragAction.getOnMouseDragged(primaryStage));


        root.getChildren().add(createMenu(primaryStage));
        root.getChildren().add(createWebView());

        Scene scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private Node createMenu(Stage primaryStage) {
        TitleMenuBarHBox titleMenuBarHBox = new TitleMenuBarHBox(primaryStage);

        return titleMenuBarHBox;
    }

    private Node createWebView() {
        WebView webView = new WebView();
        webView.setPrefSize(800,550);
        WebEngine engine = webView.getEngine();
        String url = Application.class.getClassLoader()
                .getResource("static/index.html").toExternalForm();
        engine.load(url);
        engine.setJavaScriptEnabled(true);
        engine.getLoadWorker().stateProperty()
                .addListener((a,o,n)->{
                    if (n == Worker.State.SUCCEEDED) {
                        for (int i = 0; i < 100; i++) {
                            engine.executeScript("app.size='" + i + "%';");
                        }
                    }
                });
        return webView;
    }
}
