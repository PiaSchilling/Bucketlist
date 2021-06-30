package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final ListManager listManager;
    private final ListsController listsController;
    private static final Logger log = LogManager.getLogger(MenuController.class);

    public MenuController(ListManager listManager){
        this.listManager = listManager;
        this.listsController = new ListsController(this.listManager);
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
        log.debug("Started showListScene");
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(listsController, "lists");
        //the borderpane is null when the constructor is called, so it can not be injected to the listsController IN the constructor while instancing the listsController
        this.listsController.injectBorderPane(this.borderPane);
        borderPane.setCenter(anchorPane);
        log.debug("Ended showListScene");
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
        CategoryManager categoryManager = new CategoryManager(this.listManager);
        CategoryController categoryController = new CategoryController(categoryManager);
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(categoryController, "categories");
        borderPane.setCenter(anchorPane);
        log.info("Switched to showCatScene (MenuController");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showListScene();
        setTooltips();
    }

    private void setTooltips(){
        Tooltip listsButtonTt = new Tooltip("see all eventlists");
        listsButtonTt.setShowDelay(Duration.millis(100));
        this.viewListButton.setTooltip(listsButtonTt);

        Tooltip catButtonTt = new Tooltip("see all categories");
        catButtonTt.setShowDelay(Duration.millis(100));
        this.viewCatButton.setTooltip(catButtonTt);

        Tooltip statsButtonTt = new Tooltip("see the statistics");
        statsButtonTt.setShowDelay(Duration.millis(100));
        this.viewStatisticButton.setTooltip(statsButtonTt);
    }
}
