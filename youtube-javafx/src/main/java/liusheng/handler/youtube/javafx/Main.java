package liusheng.handler.youtube.javafx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 2020年:  05 月:  30 日:  09小时:  57分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Main extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        AnchorPane pane = new AnchorPane();


        Image img = new Image(Files.newInputStream(Paths.get("C:\\Users\\LiuSheng\\Desktop\\chuyin.jpg")));
        Rectangle2D rectangle2D = Screen.getPrimary().getBounds();
        double width = img.getWidth();
        double height = getHeight(img, rectangle2D);
        String string = Paths.get("C:\\Users\\LiuSheng\\Desktop\\3.m4a").toUri().toURL().toString();
        //string= "https://r2---sn-i3b7knlk.googlevideo.com/videoplayback?expire=1591025619&ei=c8vUXqn3DtiGgQOB_KTACg&ip=2001%3A49f0%3Ad0da%3A3%3A%3A126&id=o-AKn45wZ6B1icuEqz7krSwDqMvex89lSD_4326eH7yuRP&itag=251&source=youtube&requiressl=yes&mh=xH&mm=31%2C29&mn=sn-i3b7knlk%2Csn-i3belnel&ms=au%2Crdu&mv=m&mvi=1&pl=64&initcwndbps=2900000&vprv=1&mime=audio%2Fwebm&gir=yes&clen=3615226&dur=206.381&lmt=1579069106104070&mt=1591003894&fvip=2&keepalive=yes&c=WEB&txp=6301222&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAPB3VvvROQUFKtjS_o5fMmVOwis5BU7b6JQ-9waYITuAAiAb9kqZ18CjYOAHdowvsZczK9iXPgt34hGSFCZBZKeNIw%3D%3D&alr=yes&sig=AOq0QJ8wRQIhAPl3LErspsdUXNCquorfPRDaCQPZCZbVTadfg5WPRLShAiBASy4AufUxFbXGHSQRnwQfxLa1pmonO1SAMio3Id-rHA%3D%3D&cpn=tJUR8k4X9Ym_PXyM&cver=2.20200529.02.0";
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(string));
        Canvas canvas = new Canvas(width, height);
        HBox hBox = new HBox();
        AnchorPane.setTopAnchor(hBox, 0D);
        AnchorPane.setLeftAnchor(hBox, 0D);

        hBox.getChildren().add(canvas);
        hBox.setStyle("-fx-background-color: transparent");
        VBox menu = new VBox();
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: red");
        menu.setPrefSize(width, 30);

        DragWindowHandler dragWindowHandler = new DragWindowHandler(primaryStage);
        menu.setOnMousePressed(dragWindowHandler);
        menu.setOnMouseDragged(dragWindowHandler);
        PathTransition pathTransition = new PathTransition(Duration.ONE, new Line(0, 0, 0, 30));
        pane.setOnMouseMoved(e -> {
            double x = e.getX();
            double y = e.getY();
            if (y >= 0 && y <= menu.getHeight()) {
                menu.setVisible(true);
                pathTransition.play();
            } else {
                menu.setVisible(false);
            }
        });
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        graphicsContext2D.setLineWidth(2.2);
        graphicsContext2D.setStroke(Paint.valueOf("white"));

        List<Lir> list = new LrcParser().parse(new String(Files.readAllBytes(Paths.get("C:\\Users\\LiuSheng\\Desktop\\酷歌词 kugeci.com_小满.lrc"))));
        MediaView mediaView = new MediaView(mediaPlayer);

        int seg = 60;
        Label textLabel = new Label("");
        int[] count = new int[1];
        mediaPlayer.setOnEndOfMedia(() -> {
            count[0] = 0;
        });
        mediaPlayer.currentTimeProperty().addListener((a, o, n) -> {
            Lir lir = list.get(count[0]);
            String text = lir.getText();
            long time = lir.getTime();
            double current = n.toMillis();
            if (time <= current) {
                long n1 = System.currentTimeMillis();
                textLabel.setText(text);
                count[0]++;
            }
        });
    /*    mediaPlayer.setOnPlaying(() -> {
            Duration totalDuration = mediaPlayer.getTotalDuration();
            executorService.execute(() -> {
                double millis = totalDuration.toMillis();
                double current =0;
                int index = 0;
                while (current < millis && index < list.size()) {

                    long start = System.currentTimeMillis();
                    Lir lir = list.get(index);
                    String text = lir.getText();
                    long time = lir.getTime();
                    if (time <= current) {
                        System.out.println(time + "time " + current );
                        Platform.runLater(() -> {
                            textLabel.setText(text);
                        });
                        index++;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        long end = System.currentTimeMillis();
                        current = current + (end -start);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });*/
        mediaPlayer.setAudioSpectrumListener(new AudioSpectrumListener() {
            private int index = 0;

            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                double[] datas = IntStream.range(0, seg)
                        .mapToDouble(i -> {

                            if (i < 5) {
                                return (magnitudes[seg - i] + 60) * 1.5;
                            }
                            return (magnitudes[i - 5] + 60) * 1.5;
                        }).toArray();
                drawCircle(width, height, graphicsContext2D, datas, seg, pane);
            }
        });


       /* textLabel.setStyle("-fx-background-color: transparent");
        textLabel.setTextAlignment(TextAlignment.CENTER);
        textLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 50));
        textLabel.setFill(Paint.valueOf("white"));
        textLabel.setStrokeType(StrokeType.OUTSIDE);
        textLabel.setStrokeWidth(3);
        textLabel.setStroke(Color.BLUE);*/
        //   textLabel.setStrokeWidth(3);
        // textLabel.setEffect(new Shadow(BlurType.GAUSSIAN,Color.BLUE,5));
        //    textLabel.setStroke(Color.BLUE);
        ImageView imageView = new ImageView(img);
       /* AnchorPane.setTopAnchor(imageView,0D);
        AnchorPane.setLeftAnchor(imageView,0D);*/
        textLabel.setBorder(new Border(new BorderStroke(Paint.valueOf("red"), BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT)));
        textLabel.setTextAlignment(TextAlignment.CENTER);
        textLabel.setAlignment(Pos.CENTER);
        textLabel.setPrefWidth(width);
        textLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 50));
        textLabel.setTextFill(Paint.valueOf("white"));
        textLabel.setPrefHeight(70);
        AnchorPane.setLeftAnchor(textLabel, 0D);
        AnchorPane.setBottomAnchor(textLabel, 50D);
        pane.getChildren().addAll(imageView, textLabel, hBox, menu, mediaView);
        pane.setStyle("-fx-background-color: red");
        primaryStage.setWidth(width);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setHeight(height);
        primaryStage.setY(0);
        primaryStage.setX(0);
        primaryStage.setScene(new Scene(pane, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.show();
        mediaPlayer.play();
    }

    private boolean b = false;

    private void drawCircle(double width, double height, GraphicsContext graphicsContext2D, double[] datas, int seg, Pane pane) {
        graphicsContext2D.clearRect(0, 0, width, height);
        double r = height;
        double d = r - Math.sqrt(r * r - height * height / 4);
        //graphicsContext2D.strokeOval(d - 2 * r, height / 2 - r, 2 * r, 2 * r);
        int i = 0;
        double tx1 = 0, ty1 = 0;
        double tx2 = 0, ty2 = 0;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("snow.jpg"), 40, 40, false, false);

        //  Random random = new Random();
        for (double w : datas
        ) {

            double y = i * height / seg;
            double pow = Math.pow(r * r - Math.pow(height / 2 - y, 2), 0.5);
            double r1 = (r - w / 2) / (r);
            double r2 = (r + w / 2) / (r);
            double x1 = r1 * pow - (r - d);
            double y1 = y * r1;
            double x2 = r2 * pow - (r - d);
            double y2 = y * r2;
            graphicsContext2D.strokeLine(tx1, ty1, x1, y1);
            graphicsContext2D.strokeLine(tx2, ty2, x2, y2);
            graphicsContext2D.strokeLine(x1, y1, x2, y2);
            graphicsContext2D.strokeLine(width - tx1, ty1, width - x1, y1);
            graphicsContext2D.strokeLine(width - tx2, ty2, width - x2, y2);
            graphicsContext2D.strokeLine(width - x1, y1, width - x2, y2);
           /* if (i % 5 == 0 && !b) {
                ImageView imageView = new ImageView(image);
                ParallelTransition parallelTransition = new ParallelTransition();
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(4000), imageView);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                Line line = new Line(x2, y2, x2 + 200, y2);
                PathTransition pathTransition = new PathTransition(Duration.millis(4000), line, imageView);
                parallelTransition.getChildren()
                        .addAll(pathTransition, fadeTransition);
                parallelTransition.cycleCountProperty()
                        .addListener((a, o, n1) -> {
                            line.setEndX(x2 + Math.random() * 100 + 100);
                            line.setEndY(y2 - Math.random() * 20 * (random.nextBoolean() ? 1 : -1));
                        });
                parallelTransition.setCycleCount(Transition.INDEFINITE);
                parallelTransition.play();

                pane.getChildren().add(imageView);
            }
*/
            i++;
            tx2 = x2;
            ty2 = y2;
            tx1 = x1;
            ty1 = y1;
        }
        b = true;
    }

    private double getHeight(Image img, Rectangle2D rectangle2D) {
        double height = img.getHeight();
        if (height > rectangle2D.getHeight()) {
            height = rectangle2D.getHeight();
        }
        return height;
    }

    private double[] create() {
        Random random = new Random();
        return random.doubles(100, 0, 30)
                .toArray();
    }
}
