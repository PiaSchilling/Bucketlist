package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final ListManager listManager;

    public MenuController(ListManager listManager){
        this.listManager = listManager;
    }


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
        ListsController eventlistController = new ListsController(this.listManager, this.borderPane);
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(eventlistController, "lists");
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showListScene();
    }
}
