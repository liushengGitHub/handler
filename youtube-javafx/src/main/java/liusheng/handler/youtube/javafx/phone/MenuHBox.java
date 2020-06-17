package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 2020年:  06 月:  08 日:  12小时:  45分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class MenuHBox extends HBox {

    private final JFXButton setting;
    private final JFXButton download;
    private final JFXButton main;

    private final VBox mainVBox;
    private final VBox downloadVBox;
    private final VBox settingBox;

    public VBox getMainVBox() {
        return mainVBox;
    }

    public VBox getDownloadVBox() {
        return downloadVBox;
    }

    public VBox getSettingBox() {
        return settingBox;
    }

    public MenuHBox(VBox middle) {
        super();
        this.main = new JFXButton("Main");
        download = new JFXButton("Download");
        setting = new JFXButton("Setting");

        main.setPrefSize(100,45);
        download.setPrefSize(100,45);
        setting.setPrefSize(100,45);
        mainVBox = new VBox();
        downloadVBox = new VBox();
        settingBox = new VBox();
        this.getChildren().addAll(main, download, setting);
        setting.setOnAction(e->{
            middle.getChildren().clear();
            middle.getChildren().add(settingBox);
        });
        download.setOnAction(e->{
            middle.getChildren().clear();
            middle.getChildren().add(downloadVBox);
        });
        main.setOnAction(e->{
            middle.getChildren().clear();
            middle.getChildren().add(mainVBox);
        });
        main.fire();
    }

    public JFXButton getSetting() {
        return setting;
    }

    public JFXButton getMain() {
        return main;
    }

    public JFXButton getDownload() {
        return download;
    }
}
