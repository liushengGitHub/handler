package liusheng.handler.youtube.javafx.phone;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import liusheng.handler.DataInsert;
import liusheng.handler.HandlerData;
import liusheng.handler.http.ChannelInfo;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.javafx.NodeUtils;
import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;


/**
 * 2020年:  06 月:  07 日:  20小时:  26分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ListItemVBox extends AnchorPane {

    private final MenuItem closeMenu;
    private final MenuItem copyMenu;
    private ImageView imageView;
    private ImageView icon;
    private Label title;
    private Label time;

    private WebView webView = new WebView();
    private final MenuItem downlaodMenu;
    private final MenuItem openMenu;
    private final Label playLabel;

    public MenuItem getDownlaodMenu() {
        return downlaodMenu;
    }

    public MenuItem getOpenMenu() {
        return openMenu;
    }

    public ListItemVBox(VideoInfo videoInfo, DataInsert dataInsertDownloader) {
        super();
        int width = 295;
        int height = 150;
        int botWidth = 50;
        int botHeight = 50;
        VBox main = new VBox();

        main.setPrefSize(width, height + botHeight);

        playLabel = new Label("", new ImageView(ImageUtils.saveImage(ListItemVBox.class.getClassLoader().getResource("play.png").toString())));
        playLabel.setVisible(false);
        playLabel.setPrefSize(botWidth, botHeight);
        playLabel.setPrefSize(50, 50);
        playLabel.setStyle("-fx-background-color: transparent");
        AnchorPane.setTopAnchor(playLabel, (height - botHeight) / 2.0);
        AnchorPane.setLeftAnchor(playLabel, (width - botWidth) / 2.0);

        VBox imageVBox = new VBox();
        imageView = new ImageView();
        main.setOnMouseEntered(e -> {
            double x = e.getX();
            double y = e.getY();

            if (x >= 0 && x <= width && y >= 0 && y <= (height + botHeight)) {
                playLabel.setVisible(true);
            } else {
                playLabel.setVisible(false);
            }

        });
        main.setOnMouseMoved(e -> {
            double x = e.getX();
            double y = e.getY();

            if (x >= 0 && x <= width && y >= 0 && y <= height) {
                playLabel.setVisible(true);
            } else {
                playLabel.setVisible(false);
            }

        });
        main.setOnMouseExited(e -> {
            double x = e.getX();
            double y = e.getY();
            if (x < 0 || x > width || y > (height + botHeight) || y < 0) {
                playLabel.setVisible(false);
            }
        });

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageVBox.setPrefSize(width, height);
        imageVBox.getChildren().add(imageView);
        webView.setPrefSize(width, height);
        webView.setContextMenuEnabled(false);

        icon = new ImageView();
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        ContextMenu iconMenu = new ContextMenu();
        ContextMenu contextMenu = new ContextMenu();

        initMenuContext(iconMenu, contextMenu, videoInfo);

        title = new Label(videoInfo.getName());
        time = new Label(videoInfo.getViewCountText() + "." + videoInfo.getPublishedTimeText());
        HBox botBox = new HBox();
        botBox.setPrefSize(width, botHeight);

        VBox titleBox = new VBox();
        int prefWidth = width - botWidth;
        titleBox.setPrefSize(prefWidth, botHeight);

        title.setPrefSize(prefWidth, 25);
        time.setPrefSize(prefWidth, 25);
        titleBox.getChildren().addAll(title, time);
        botBox.getChildren().addAll(icon, titleBox);

        main.getChildren().addAll(imageVBox, botBox);

        this.getChildren().addAll(main, playLabel);

        handleImage(videoInfo.getImgSrc(), width, height, imageView);
        Optional.ofNullable(videoInfo.getChannelInfo())
                .map(ChannelInfo::getImgSrc)
                .ifPresent(url -> {
                    handleImage(url, width, height, icon);
                });
        this.setStyle("-fx-background-color: transparent;");

        downlaodMenu = new MenuItem("下载");
        openMenu = new MenuItem("观看");
        closeMenu = new MenuItem("取消");
        copyMenu = new MenuItem("复制链接");
        String data = "https://www.youtube.com/watch?v=" + videoInfo.getVideoId();
        copyMenu.setOnAction(e -> {
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            systemClipboard.setContent(Collections.singletonMap(DataFormat.PLAIN_TEXT, data));
        });

        WebEngine engine = webView.getEngine();
        openMenu.setOnAction(e -> {
            playVideo(videoInfo, imageVBox, engine);
        });
        playLabel.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1 && e.getButton() == MouseButton.PRIMARY) {
                playVideo(videoInfo, imageVBox, engine);
            }
        });
        downlaodMenu.setOnAction(e -> {
            System.out.println("开始下载");

            HandlerData handlerData = new HandlerData(videoInfo.getPlayUrl());
            handlerData.getState().put("videoInfo", videoInfo);
            dataInsertDownloader.addData(handlerData);
        });
        contextMenu.getItems().addAll(downlaodMenu, openMenu, copyMenu, closeMenu);
        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(this, e.getScreenX(), e.getScreenY());
            }
            imageVBox.requestFocus();
        });

    }

    private void initMenuContext(ContextMenu iconMenu, ContextMenu contextMenu, VideoInfo videoInfo) {
        MenuItem menuItem = new MenuItem("复制Channel链接");
        MenuItem closeItem = new MenuItem("取消");

        menuItem.setOnAction(e -> {
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            ChannelInfo channelInfo = videoInfo.getChannelInfo();
            String data = StringUtils.isNotBlank(channelInfo.getChannelUrl())
                    ? channelInfo.getChannelUrl() : "https://www.youtube.com/channel/" + channelInfo.getChannelId();
            systemClipboard.setContent(Collections.singletonMap(DataFormat.PLAIN_TEXT, data));
        });

        iconMenu.getItems().addAll(menuItem, closeItem);
        icon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                iconMenu.show(icon, e.getScreenX(), e.getScreenY());
                e.consume();
            }
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
            icon.requestFocus();
        });
    }

    private void playVideo(VideoInfo videoInfo, VBox imageVBox, WebEngine engine) {
        ObservableList<Node> children = imageVBox.getChildren();
        children.clear();
        NodeUtils.getWebViews().add(webView);
        children.add(webView);
        this.getChildren().remove(playLabel);
        Object originUrl = videoInfo.getData().get("originUrl");
        engine.load(liusheng.handler.http.utils.StringUtils.urlProcess(videoInfo.getEmbedUrl(), originUrl.toString()));

    }

    private void handleImage(String url, int width, int height, ImageView imageView) {
        if (StringUtils.isBlank(url)) return;
        Image image1 = ImageUtils.get(url);
        if (Objects.isNull(image1)) {
            new Thread(() -> {
                try {
                    downloadConvert(width, height, url, imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            imageView.setImage(image1);
        }
    }

    private void downloadConvert(int width, int height, String url, ImageView imageView) throws IOException {

        ResponseBody body = YoutubeRetrofitUtils.getOkHttpClient()
                .newCall(new Request.Builder().url(url).build()).execute().body();

        File target = null;
        if (body.contentType().equals(MediaType.parse("image/webp"))) {
            File source = File.createTempFile("webp", ".webp");
            target = File.createTempFile("png", ".png");
            FileOutputStream output = new FileOutputStream(source);
            InputStream input = body.byteStream();
            IOUtils.copy(input, output);
            output.close();
            input.close();
            Convert.convert(source, target);
            source.delete();
        } else {
            target = File.createTempFile("png", ".png");
            FileOutputStream output = new FileOutputStream(target);
            InputStream input = body.byteStream();
            IOUtils.copy(input, output);
            output.close();
            input.close();
        }

        Image image = new Image(target.toURI().toURL().toString(), width, height, true, true);
        imageView.setImage(image);
        ImageUtils.put(url, image);
        target.deleteOnExit();


    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getIcon() {
        return icon;
    }

    public Label getTitle() {
        return title;
    }

    public Label getTime() {
        return time;
    }
}
