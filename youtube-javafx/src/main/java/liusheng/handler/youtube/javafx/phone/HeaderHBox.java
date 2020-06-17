package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import liusheng.handler.youtube.javafx.DragWindowHandler;
import liusheng.handler.youtube.javafx.NodeUtils;

import java.io.InputStream;


/**
 * 2020年:  06 月:  07 日:  19小时:  50分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class HeaderHBox extends HBox {
    private Label icon;
    private Label name;
    private JFXButton minButton;
    private JFXButton closeButton;
    private final HBox middle;

    public HeaderHBox() {
        int height = 25;
        Image image = ImageUtils.saveImage(
                HeaderHBox.class.getClassLoader().getResource("download.png").toString(),25,25
        );
        icon = new Label("", new ImageView(image));
        icon.setPrefHeight(height);
        icon.setPrefWidth(height);
        name = new Label("Youtube下载器");
        name.setAlignment(Pos.CENTER);
        name.setPrefHeight(height);
        name.setPrefWidth(150);
        middle = new HBox();
        middle.setPrefHeight(height);
        middle.setPrefWidth(75);
        InputStream inputStream = HeaderHBox.class.getClassLoader().getResourceAsStream("minus.png");
        InputStream closeInputStream = HeaderHBox.class.getClassLoader().getResourceAsStream("close.png");
        minButton = new JFXButton("", new ImageView(new Image(inputStream, height, height, true, true)));
        minButton.setPrefHeight(height);
        minButton.setPrefWidth(height);
        Stage primaryStage = NodeUtils.getPrimaryStage();
        minButton.setOnAction(e -> {
            primaryStage.setIconified(!primaryStage.isIconified());
        });
        closeButton = new JFXButton("", new ImageView(new Image(closeInputStream, height, height, true, true)));
        closeButton.setPrefHeight(height);
        closeButton.setPrefWidth(height);
        closeButton.setOnAction(e -> {
            primaryStage.close();
        });
        this.getChildren().addAll(icon, name, middle, minButton, closeButton);
        DragWindowHandler windowHandler = new DragWindowHandler(primaryStage);
        this.setOnMousePressed(windowHandler);
        this.setOnMouseDragged(windowHandler);
        this.setStyle("-fx-background-color: transparent");
    }

    public Label getIcon() {
        return icon;
    }

    public Label getName() {
        return name;
    }

    public JFXButton getMinButton() {
        return minButton;
    }

    public JFXButton getCloseButton() {
        return closeButton;
    }

    public HBox getMiddle() {
        return middle;
    }
}
