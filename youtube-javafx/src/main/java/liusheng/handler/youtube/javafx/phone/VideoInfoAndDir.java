package liusheng.handler.youtube.javafx.phone;

import javafx.scene.layout.Pane;
import liusheng.handler.http.VideoInfo;

/**
 * 2020年:  06 月:  08 日:  21小时:  35分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class VideoInfoAndDir {
    private VideoInfo videoInfo;
    private String dir;
    private Pane pane;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
