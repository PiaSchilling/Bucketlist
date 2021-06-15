package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.CompleteEventController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.ModifyEventController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.ModifyListController;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UncompletedEventController implements Initializable {

    private final Eventlist eventlist;
    private String eventName;

    public UncompletedEventController(Eventlist eventlist){
        this.eventlist = eventlist;
    }

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private CheckBox checkbox;


    public void setEventnameLabel(String eventname) {
        this.eventName = eventname;
        this.eventNameLabel.setText(eventname.toUpperCase());
    }

    public void setEventcategoryLabel(String eventcategory) {
        this.categoryLabel.setText(eventcategory);
    }

    @FXML//todo rename method (löscht nicht sondern äffnet neues fenster)
    public void deleteEvent(){
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(0.95);
        Parent popUp;
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUpWindows/ModifyListWindow.fxml"));
        ModifyEventController modifyEventController = new ModifyEventController(this.eventlist,this.eventName);
        loader.setController(modifyEventController);

        try {
            popUp = loader.load();
            stage.setScene(new Scene(popUp));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * opens a dialogue where the user can complete the event
     */
    @FXML
    void completeEvent() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(0.95);
        Parent popUp;
        stage.initModality(Modality.APPLICATION_MODAL);
        System.out.println("Test");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUpWindows/CompleteEventWindow.fxml"));
        CompleteEventController completeEventController = new CompleteEventController(this.eventlist, this.eventName);
        System.out.println("in umcompleted: " + this.eventName);
        loader.setController(completeEventController);

        try {
            popUp = loader.load();
            stage.setScene(new Scene(popUp));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * if the eventlist which contains this event is expired the buttons are disabled to prevent interaction
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.eventlist.getExpiryDateString() != null && StatisticsManager.daysLeft(this.eventlist)<0){
            this.deleteButton.setDisable(true);
            this.checkbox.setDisable(true);
        }
    }
}
