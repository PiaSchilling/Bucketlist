package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class EventinCatBoxController {

    private final CategoryManager categoryManager;
    private String eventName;
    private Category category;
    private final BorderPane borderPane;


    public EventinCatBoxController(CategoryManager categoryManager, BorderPane borderPane) {
        this.categoryManager = categoryManager;
        this.borderPane = borderPane;
    }

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label categoryLabel;

    public void setEventNameLabel(String eventName) {
        this.eventNameLabel.setText(eventName);
    }

    public void setCategoryLabel(String category) {
        this.categoryLabel.setText(category);
    }

}
