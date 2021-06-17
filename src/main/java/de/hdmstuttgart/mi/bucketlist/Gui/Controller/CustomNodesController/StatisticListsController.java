package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class StatisticListsController {

    private final ListManager listManager;
    private String eventlistName;
    private BorderPane borderPane;

    @FXML
    private Label eventlistNameLabel;


    public StatisticListsController(ListManager listManager, BorderPane borderPane) {
        this.listManager = listManager;
        this.borderPane = borderPane;
    }


    public void setEventlistNameLabel(String eventlistName){
        this.eventlistName = eventlistName;
        this.eventlistNameLabel.setText(eventlistName.toUpperCase());
    }
}
