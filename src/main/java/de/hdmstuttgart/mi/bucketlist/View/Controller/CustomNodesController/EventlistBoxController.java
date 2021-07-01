package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.ModifyListController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.EventlistController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EventlistBoxController {

    private final ListManager listManager;
    private String eventlistname;
    private final BorderPane borderPane;


    public EventlistBoxController(ListManager listManager, BorderPane borderPane){
        this.listManager = listManager;
        this.borderPane = borderPane;
    }

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberOfEventsLabel;

    @FXML
    private Label expiryDateLabel;

    @FXML
    private Button deleteButton;


    public void setEventlistname(String eventlistname){
        this.eventlistname = eventlistname;
        this.nameLabel.setText(eventlistname.toUpperCase());
    }

    public void setNumberOfEvents(String numberOfEvents){
        this.numberOfEventsLabel.setText(numberOfEvents + " EVENTS");
    }

    public void setExpiryDate(String expiryDate){
        if(expiryDate == null) {
            this.expiryDateLabel.setText("NOT SET");
        }else if(StatisticsManager.daysLeft(this.listManager.getEventlistByName(this.eventlistname))<0){
            String temp = this.listManager.getEventlistByName(this.eventlistname).getExpiryDateString();
            this.expiryDateLabel.setText("EXPIRED SINCE " + temp);
        }else{
            String temp = this.listManager.getEventlistByName(this.eventlistname).getExpiryDateString();
            this.expiryDateLabel.setText("DUE TO " + temp);
        }
    }



    @FXML //todo rename method (löscht nicht sondern äffnet neues fenster)
    void deleteEventlist() {
        ModifyListController modifyListController = new ModifyListController(this.listManager,this.eventlistname);
        PaneLoader.loadPopUpWindow(modifyListController,"ModifyListWindow");

    }

    /**
     * switch into the scene of the evenlist represented by the Eventlistbox
     */
    @FXML
    void switchToListScene() {
            EventlistController eventlistController = new EventlistController(this.eventlistname, this.listManager,this.borderPane);
            AnchorPane anchorPane = PaneLoader.loadAnchorPane(eventlistController,"eventlist");
            this.borderPane.setCenter(anchorPane);
    }

    public String getEventlistname(){
        return this.eventlistname;
    }
}
