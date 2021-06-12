package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MenuController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button viewCatButton;

    @FXML
    private Button viewListButton;

    @FXML
    private Button viewStatisticButton;


    @FXML
    void showListScene() {
        EventlistController eventlistController = new EventlistController();
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(eventlistController, "eventlists");
        borderPane.setCenter(anchorPane);
    }

    @FXML
    void showStatisticScene() {
        StatisticController statisticController = new StatisticController();
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(statisticController, "statistics");
        borderPane.setCenter(anchorPane);

    }

    @FXML
    void showCatScene() {
        CategoryController categoryController = new CategoryController();
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(categoryController, "categories");
        borderPane.setCenter(anchorPane);

    }


}
