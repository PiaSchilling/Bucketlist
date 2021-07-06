package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.CompletedEventBox;
import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.UncompletedEventBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;

/**
 * Controller fot the scene which shows a CategoryList (Events filtered by the Category)
 */
public class CategorylistController implements Initializable {

    private final CategoryManager categoryManager;
    private final Categorylist categorylist;
    private final BorderPane borderPane;

    public CategorylistController(Categorylist categorylist, BorderPane borderPane,CategoryManager categoryManager) {
        this.categorylist = categorylist;
        this.borderPane = borderPane;
        this.categoryManager = categoryManager;
    }

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Label categoriesLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backButtonImage;

    public void showEventinCatBoxes() {
        setScrollBehavior();
        this.flowpane.getChildren().clear();

        for (int i = 0; i < this.categorylist.getEvents().size(); i++) {
            Event temp = this.categorylist.getEvents().get(i);
            AnchorPane pane;
            if(!temp.getIsCompleted()){
                UncompletedEventBox box = new UncompletedEventBox(this.categorylist.getEvents().get(i));
                box.getUncompletedEventController().setEventnameLabel(temp.getEventName());
                box.getUncompletedEventController().setEventcategoryLabel(temp.getEventCategory().toString());
                pane = box;
            }else{
                CompletedEventBox box = new CompletedEventBox(this.categorylist.getEvents().get(i));
                box.getEventCompletedController().setEventNameLabel(temp.getEventName());
                box.getEventCompletedController().setEventCatLabel(temp.getEventCategory().toString());
                box.getEventCompletedController().setEventDateLabel(temp.getEventDateString());
                box.getEventCompletedController().setEventDescrptionLabel(temp.getEventDescription());
                box.getEventCompletedController().setEventImageView(temp.getEventImageUrl());
                pane = box;
            }
            this.flowpane.getChildren().add(pane);
        }
    }


    @FXML
    void switchToCatScene(){
        CategoryController categoryController = new CategoryController(this.categoryManager,this.borderPane);
        categoryController.injectBorderPane(this.borderPane);
        AnchorPane anchorPane = PaneLoader.loadAnchorPane(categoryController,"categories");
        this.borderPane.setCenter(anchorPane);
    }


    /**
     * computes the height of the flowpane according to the number of boxes so
     * the scrollpane will be scrollable if there are many boxes
     */
    private void setScrollBehavior(){
        double height = this.categorylist.getEvents().size() * 130;
        for (int i = 0; i < this.categorylist.getEvents().size(); i++) {
            if(this.categorylist.getEvents().get(i).getIsCompleted()){
                height += 190;
            }
        }
        this.flowpane.setPrefHeight(height);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEventinCatBoxes();

    }
}
