package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;


import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.CompleteEventController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.ModifyListController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController.EventlistController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController.MenuController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EventlistBoxController {

    private final ListManager listManager;
    private String eventlistname;
    private BorderPane borderPane;


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
        if(expiryDate == null){
            this.expiryDateLabel.setText("NOT SET");
        }else{
            String temp = this.listManager.getEventlistByName(this.eventlistname).getExpiryDateString();
            this.expiryDateLabel.setText("DUE TO " + temp);
        }
    }

    @FXML //todo rename method (löscht nicht sondern äffnet neues fenster)
    void deleteEventlist() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(0.95);
        stage.initModality(Modality.APPLICATION_MODAL);

        Parent popUp;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUpWindows/ModifyListWindow.fxml"));
        ModifyListController modifyListController = new ModifyListController(this.listManager,this.eventlistname);
        loader.setController(modifyListController);

        try {
            popUp = loader.load();
            stage.setScene(new Scene(popUp));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * switch into the scene of the evenlist represented by the Eventlistbox
     */
    @FXML
    void switchToListScene() {
            EventlistController eventlistController = new EventlistController(this.eventlistname, this.listManager);
            AnchorPane anchorPane = PaneLoader.loadAnchorPane(eventlistController,"Eventlist");
            this.borderPane.setCenter(anchorPane);
    }
}
