package liusheng.handler.youtube.javafx.phone;

import javafx.application.Platform;
import javafx.scene.control.Label;
import liusheng.handler.http.utils.ConverterListener;
import liusheng.handler.http.utils.StringUtils;
import liusheng.handler.youtube.Speed;
import liusheng.handler.youtube.SpeedConverter;

/**
 * 2020年:  06 月:  09 日:  00小时:  41分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class PaneConverterListener implements ConverterListener {
    public static final String FORMAT = "转换%s:%s";
    private Label label;
    private SpeedConverter speedConverter = new SpeedConverter();

    public PaneConverterListener(Label label) {
        this.label = label;
    }

    @Override
    public void listen(String line, String type) {
        Speed speed = speedConverter.convert(line);
        String size = speed.getSize();
        if (!StringUtils.isEmpty(size)) {
            Platform.runLater(() -> {
                label.setText(String.format(FORMAT, type, size));
            });
        }

    }
}
