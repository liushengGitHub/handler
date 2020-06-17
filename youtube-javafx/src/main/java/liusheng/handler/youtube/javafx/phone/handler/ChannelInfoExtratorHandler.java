package liusheng.handler.youtube.javafx.phone.handler;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import liusheng.handler.AbstractRetryHandler;
import liusheng.handler.DataInsert;
import liusheng.handler.HandlerContext;
import liusheng.handler.RetryFailurerHandler;
import liusheng.handler.http.Extractor;
import liusheng.handler.http.VideoInfo;
import liusheng.handler.youtube.javafx.phone.ContainerVBox;
import liusheng.handler.youtube.javafx.phone.ListItemVBox;
import liusheng.handler.youtube.javafx.phone.VideoInfoAndPane;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 2020年:  06 月:  08 日:  16小时:  42分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ChannelInfoExtratorHandler extends AbstractRetryHandler {
    VBox containerVBox;
    private final DataInsert dataInsertDownloader;

    public ChannelInfoExtratorHandler(RetryFailurerHandler retryFailurerHandler, VBox containerVBox, DataInsert dataInsertDownloader) {
        super(retryFailurerHandler);
        this.containerVBox = containerVBox;
        this.dataInsertDownloader = dataInsertDownloader;
    }

    @Override
    protected void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception {
        Map<String, Object> data = handlerContext.data();
        Extractor videoExtractor = (Extractor) data.get("videoExtractor");

        execute(containerVBox, videoExtractor);

    }

    private void execute(VBox containerVBox, Extractor channelVideoExtractor) throws Exception {

        ObservableList<Node> list = containerVBox.getChildren();

        List<VideoInfo> infos = channelVideoExtractor.init();
        if (Objects.nonNull(infos) && !infos.isEmpty()) {

            Platform.runLater(() -> {
                list.clear();
                ContainerVBox containerVBox1 = new ContainerVBox();
                list.add(containerVBox1);
                Collection<? extends VideoInfoAndPane> videoInfoAndPanes = convert(infos, dataInsertDownloader);
                containerVBox1.getListView().getItems().addAll(videoInfoAndPanes);
                containerVBox1.getListView().setOnScroll(new DropEventHandler(channelVideoExtractor, containerVBox1, dataInsertDownloader));
            });
        } else {
            list.add(new Label("没有数据"));
        }

    }

    private static Collection<? extends VideoInfoAndPane> convert(List<VideoInfo> infos, DataInsert dataInsertDownloader) {

        return infos.stream().map(videoInfo -> {
            VideoInfoAndPane videoInfoAndPane = new VideoInfoAndPane();
            videoInfoAndPane.setVideoInfo(videoInfo);
            ListItemVBox listItemVBox = new ListItemVBox(videoInfo, dataInsertDownloader);
            videoInfoAndPane.setPane(listItemVBox);
            return videoInfoAndPane;
        }).collect(Collectors.toList());
    }

    static class DropEventHandler implements EventHandler<ScrollEvent> {

        private int count = 1;
        private int n = 1;
        private Extractor channelVideoExtractor;
        private ContainerVBox containerVBox;
        private DataInsert dataInsertDownloader;

        private AtomicInteger handler = new AtomicInteger();

        public DropEventHandler(Extractor channelVideoExtractor, ContainerVBox containerVBox, DataInsert dataInsertDownloader) {
            this.channelVideoExtractor = channelVideoExtractor;
            this.containerVBox = containerVBox;
            this.dataInsertDownloader = dataInsertDownloader;
        }

        @Override
        public void handle(ScrollEvent event) {
            if (event.getDeltaY() < 0 && ++count > 1 && handler.compareAndSet(0, 1)) {
                count = 0;
                execute(channelVideoExtractor, containerVBox, this);
            }
        }

        private void execute(Extractor channelVideoExtractor, ContainerVBox containerVBox1, EventHandler<ScrollEvent> handler) {
            new Thread(() -> {
                try {
                    List<VideoInfo> infos = channelVideoExtractor.get(n++);
                    JFXListView<VideoInfoAndPane> listView = containerVBox1.getListView();
                    ObservableList<VideoInfoAndPane> items = listView.getItems();
                    if (Objects.nonNull(infos) && !infos.isEmpty()) {

                        Platform.runLater(() -> {
                            Collection<? extends VideoInfoAndPane> videoInfoAndPanes = convert(infos, dataInsertDownloader);
                            int size = items.size();
                            items.addAll(videoInfoAndPanes);
                            listView.scrollTo(size);
                            listView.setOnScroll(handler);
                        });
                    } else {

                    }


                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    this.handler.set(0);
                }
            }).start();
        }
    }

    @Override
    protected boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext) {
        Map<String, Object> data = handlerContext.data();
        Object videoExtractor =  data.get("videoExtractor");
        return Objects.nonNull(videoExtractor) && (videoExtractor instanceof Extractor);
    }
}
