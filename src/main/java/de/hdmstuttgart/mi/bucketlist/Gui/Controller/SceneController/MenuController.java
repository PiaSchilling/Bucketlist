package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final ListManager listManager;
    private static final Logger log = LogManager.getLogger(MenuController.class);

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
        log.info("Switched to showListScene (MenuController");
    }

    @FXML
    void showStatisticScene() {
        StatisticController statisticController = new StatisticController(this.listManager, this.borderPane);
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(statisticController, "statistics");
        borderPane.setCenter(anchorPane);
        log.info("Switched to showStatisticScene (MenuController");

    }

    @FXML
    void showCatScene() {
        CategoryController categoryController = new CategoryController();
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(categoryController, "categories");
        borderPane.setCenter(anchorPane);
        log.info("Switched to showCatScene (MenuController");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showListScene();
    }
}
