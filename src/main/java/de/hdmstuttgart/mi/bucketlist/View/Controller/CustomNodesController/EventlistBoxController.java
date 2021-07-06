package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.ModifyListController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.EventlistController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EventlistBoxController implements Initializable{

    private final ListManager listManager;
    private String eventlistname;
    private final BorderPane borderPane;
    private final Eventlist eventlist;
    private final StringProperty numberOfEvents;


    public EventlistBoxController(ListManager listManager, BorderPane borderPane, String eventlistname){
        this.listManager = listManager;
        this.borderPane = borderPane;
        this.eventlistname = eventlistname;
        this.eventlist = this.listManager.getEventlistByName(eventlistname);
        this.numberOfEvents = this.eventlist.getListSizeProperty();
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
       // this.numberOfEventsLabel.setText(numberOfEvents + " EVENTS");
        this.numberOfEvents.set(numberOfEvents + " EVENTS");
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

    /**
     * deletes the eventlist (asks for confirmation beforehand)
     */
    @FXML
    void deleteEventlist() {
        if(PaneLoader.loadConfirmationWindow("Are you sure you want to delete the list ?")){
            this.listManager.deleteEventlist(this.eventlistname);
        }
    }

    /**
     * switch into the scene of the eventlist represented by the Eventlistbox
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

    /**
     * bind the label to the property so its updating after changes
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.numberOfEventsLabel.textProperty().bind(this.numberOfEvents);
    }
}
