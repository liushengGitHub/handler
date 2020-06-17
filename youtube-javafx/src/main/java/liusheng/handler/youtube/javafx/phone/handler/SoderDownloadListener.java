package liusheng.handler.youtube.javafx.phone.handler;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.scene.control.Label;
import liusheng.handler.http.DownloadListener;

/**
 * 2020年:  06 月:  08 日:  22小时:  35分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SoderDownloadListener implements DownloadListener {
    public static final String FORMAT = "下载进度：%4.2f";
    private JFXProgressBar jfxSlider;
    private Label label;

    public SoderDownloadListener(JFXProgressBar jfxSlider, Label label) {
        this.jfxSlider = jfxSlider;
        this.label = label;
    }

    @Override
    public void listen(long current, long total) {
        double progress = current * 1.0 / total;
        Platform.runLater(() -> {
            jfxSlider.setProgress(progress);
            label.setText(String.format(FORMAT, progress * 100) + "%");
        });
    }
}
