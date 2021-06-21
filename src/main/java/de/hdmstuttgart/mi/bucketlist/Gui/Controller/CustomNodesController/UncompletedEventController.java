package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.CompleteEventController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.ModifyEventController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.ModifyListController;
import de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController.PaneLoader;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class UncompletedEventController implements Initializable {

    private final Eventlist eventlist;
    private String eventName;

    private static final Logger log = LogManager.getLogger(UncompletedEventController.class);

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
    private CheckBox checkBox;


    public void setEventnameLabel(String eventname) {
        this.eventName = eventname;
        this.eventNameLabel.setText(eventname.toUpperCase());
    }

    public void setEventcategoryLabel(String eventcategory) {
        this.categoryLabel.setText(eventcategory);
    }

    @FXML//todo rename method (löscht nicht sondern äffnet neues fenster)
    public void deleteEvent(){
        ModifyEventController modifyEventController = new ModifyEventController(this.eventlist,this.eventName);
        PaneLoader.loadPopUpWindow(modifyEventController,"ModifyListWindow");
    }

    /**
     * opens a dialogue where the user can complete the event
     */
    @FXML
    void completeEvent() {
        CompleteEventController completeEventController = new CompleteEventController(this.eventlist, this.eventName);
        PaneLoader.loadPopUpWindow(completeEventController,"CompleteEventWindow");

    }

    /**
     * if the eventlist which contains this event is expired the buttons are disabled to prevent interaction
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //disable all actions if the list is expired
        if(this.eventlist.getExpiryDateString() != null && StatisticsManager.daysLeft(this.eventlist)<0){
            log.info("List expired. Actions are disabled");
            this.deleteButton.setDisable(true);
            this.checkBox.setDisable(true);
        }
    }
}
