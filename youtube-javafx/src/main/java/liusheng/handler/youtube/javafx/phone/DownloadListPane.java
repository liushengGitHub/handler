package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import liusheng.handler.http.DefaultDownloaderController;
import liusheng.handler.http.DownloaderController;
import liusheng.handler.http.utils.ProcessBuilderUtils;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.javafx.NodeUtils;

import java.io.File;
import java.net.URL;

import static liusheng.handler.youtube.javafx.phone.ImageUtils.saveImage;


/**
 * 2020年:  06 月:  08 日:  21小时:  19分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DownloadListPane extends HBox {
    private ImageView imageView;
    private Label nameLabel;
    private Label dirLabel;
    private Label stateLabel;
    private JFXButton pauseButton;
    private JFXButton cancelButton;
    private JFXProgressBar progress;

    public JFXProgressBar getProgress() {
        return progress;
    }

    public JFXButton getCancelButton() {
        return cancelButton;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getDirLabel() {
        return dirLabel;
    }

    public Label getStateLabel() {
        return stateLabel;
    }

    public JFXButton getPauseButton() {
        return pauseButton;
    }

    public DownloadListPane(VideoInfoAndDir videoInfo, DefaultDownloaderController downloaderController) {


        super();
        int width = 295;
        int height = 60;
        int labelHeight = 15;
        VideoInfo infoVideoInfo = videoInfo.getVideoInfo();
        String dir = videoInfo.getDir();
        progress = new JFXProgressBar(0);
        imageView = new ImageView();
        imageView.setFitWidth(height);
        imageView.setFitHeight(height);
        URL url = DownloadListPane.class.getClassLoader().getResource("video.png");
        String urlStr = url.toString();
        Image image = saveImage(urlStr);
        imageView.setImage(image);
        nameLabel = new Label(infoVideoInfo.getName());
        nameLabel.setTooltip(new Tooltip(nameLabel.getText()));
        int middleWidth = width - 3 * height;
        nameLabel.setPrefSize(middleWidth, labelHeight);
        dirLabel = new Label(dir);
        dirLabel.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                try {
                    ProcessBuilderUtils.executeAndDiscardOuput("Explorer", "/e,", "\"" + new File(dir).getAbsolutePath() + "\"");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        dirLabel.setOnMouseDragEntered(e -> {
            dirLabel.setCursor(Cursor.HAND);
        });

        dirLabel.setOnMouseExited(e -> {
            dirLabel.setCursor(Cursor.DEFAULT);
        });
        dirLabel.setPrefSize(middleWidth, labelHeight);
        stateLabel = new Label("正在等待");
        stateLabel.setPrefSize(middleWidth, labelHeight);
        stateLabel.textProperty().addListener((a, o, n) -> {
            stateLabel.setTooltip(new Tooltip(n));
        });
        progress.setPrefSize(middleWidth, labelHeight);
        pauseButton = new JFXButton();
        image = saveImage(DownloadListPane.class.getClassLoader().getResource("pause.png").toString());
        pauseButton.setGraphic(new ImageView(image));
        pauseButton.setOnAction(e -> {
            int state = downloaderController.getState();
            if (state == DownloaderController.PAUSE) {
                Image saveImage = saveImage(DownloadListPane.class.getClassLoader().getResource("play.png").toString());
                pauseButton.setGraphic(new ImageView(saveImage));
                downloaderController.setState(DownloaderController.EXECUTE);
            } else if (state == DownloaderController.EXECUTE) {
                Image saveImage = saveImage(DownloadListPane.class.getClassLoader().getResource("pause.png").toString());
                pauseButton.setGraphic(new ImageView(saveImage));
                downloaderController.setState(DownloaderController.PAUSE);
            }
        });
        cancelButton = new JFXButton();
        image = saveImage(DownloadListPane.class.getClassLoader().getResource("cancel.png").toString());
        cancelButton.setGraphic(new ImageView(image));
        cancelButton.setOnAction(e -> {
            int state = downloaderController.getState();
            if (state != DownloaderController.CANCEL || state != DownloaderController.MERGE_FINISHED) {
                downloaderController.setState(DownloaderController.CANCEL);
                NodeUtils.getDownloadPane().getListView().getItems().remove(videoInfo);
            }
        });
        pauseButton.setPrefSize(height, height);
        cancelButton.setPrefSize(height, height);

        VBox middle = new VBox();
        middle.setPrefSize(middleWidth, height);
        middle.getChildren().addAll(nameLabel, dirLabel, stateLabel, progress);
        this.getChildren().addAll(imageView, middle, pauseButton, cancelButton);
    }


}
