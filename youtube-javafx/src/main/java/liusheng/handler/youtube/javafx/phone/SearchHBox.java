package liusheng.handler.youtube.javafx.phone;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * 2020年:  06 月:  07 日:  20小时:  08分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class SearchHBox extends HBox {

    private final JFXTextField searchText;
    private final JFXButton searchButton;


    public SearchHBox() {
        super();
        searchText = new JFXTextField();
        searchText.setPromptText("请输入URL");
        searchText.setPrefWidth(200);
        searchText.setPrefHeight(50);
        searchButton = new JFXButton("解析");
        HBox.setMargin(searchText, new Insets(0, 20, 0, 0));
        searchButton.setPrefWidth(60);
        searchButton.setPrefHeight(50);
        getChildren().addAll(searchText,searchButton);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: transparent");
    }

    public JFXTextField getSearchText() {
        return searchText;
    }

    public JFXButton getSearchButton() {
        return searchButton;
    }
}
