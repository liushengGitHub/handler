package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXListView;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * 2020年:  06 月:  08 日:  21小时:  28分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class DownloadPane extends VBox {

    private final JFXListView<VideoInfoAndDir> listView;

    public JFXListView<VideoInfoAndDir> getListView() {
        return listView;
    }

    public DownloadPane() {
        super();
        listView = new JFXListView();
        listView.getStyleClass().add("downloadList");
        listView.getSelectionModel().selectedItemProperty();
        listView.setCellFactory(list -> {
            ListCell<VideoInfoAndDir> listCell = new ListCell<VideoInfoAndDir>() {
                @Override
                protected void updateItem(VideoInfoAndDir item, boolean empty) {
                    super.updateItem(item, empty);
                    if (Objects.nonNull(item) && !empty) {
                        setGraphic(item.getPane());
                        listView.refresh();
                    } else {
                        setGraphic(null);
                    }
                }
            };
            listCell.setAlignment(Pos.CENTER);
            listCell.setStyle("-fx-background-color: transparent");
            listCell.setPrefSize(295, 60);
            return listCell;
        });
        setStyle("-fx-background-color: transparent");
        this.getChildren().addAll(listView);
        listView.setPrefSize(300,450);
    }
}
