package liusheng.handler.youtube.javafx.hello1;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * 2020年:  06 月:  04 日:  13小时:  55分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        VBox root = new VBox();

        WebView webView = new WebView();

        WebEngine engine = webView.getEngine();
        JavaApp app = new JavaApp();
        engine.getLoadWorker().stateProperty()
                .addListener((a, o, n) -> {


                    if (n == Worker.State.RUNNING){

                    }

                    if (n == Worker.State.SUCCEEDED) {


                        JSObject win
                                = (JSObject) engine.executeScript("window");
                        win.setMember("app", app);
                        engine.executeScript("var oriXOpen = XMLHttpRequest.prototype.open; \n" +
                                "XMLHttpRequest.prototype.open = function(method,url,asncFlag,user,password) {\n" +
                                "    app.hello(url);" +
                                "    oriXOpen.call(this,method,url,asncFlag,user,password); \n" +
                                "};");
                    }
                });
        engine.load("http://www.yxdm.me/resource/9994-11-1.html");

        root.getChildren().add(webView);
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
    }
}
