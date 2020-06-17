package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXListView;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * 2020年:  06 月:  07 日:  20小时:  50分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class ContainerVBox extends VBox {
    private JFXListView<   VideoInfoAndPane > listView;


    public ContainerVBox() {
        super();
        listView = new JFXListView();
        listView.setVerticalGap(2D);

        listView.setPrefSize(300, 400);
        listView.getStyleClass().add("searchList");
        listView.setCellFactory((list) -> {
            ListCell<VideoInfoAndPane> listCell = new ListCell<VideoInfoAndPane>() {
                @Override
                protected void updateItem(VideoInfoAndPane item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && Objects.nonNull(item)) {
                        setGraphic(item.getPane());
                    } else {
                        setGraphic(null);
                    }

                }

            };
            listCell.setPrefSize(295, 200);
            listCell.setAlignment(Pos.CENTER);
            listCell.setStyle("-fx-background-color: transparent;");
            return listCell;
        });
        getChildren().add(listView);
        this.setStyle("-fx-background-color: transparent;");
    }

    public JFXListView<VideoInfoAndPane> getListView() {
        return listView;
    }
}
