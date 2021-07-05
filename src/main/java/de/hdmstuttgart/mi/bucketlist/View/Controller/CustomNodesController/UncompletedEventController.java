package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.CompleteEventController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.ModifyEventController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class UncompletedEventController implements Initializable {

    private Eventlist eventlist;
    private String eventName;
    private Event event;

    private static final Logger log = LogManager.getLogger(UncompletedEventController.class);

    //this constructor is used by the eventlistScene
    public UncompletedEventController(Eventlist eventlist){
        this.eventlist = eventlist;
    }

    //this constructor is used by the categoryListScene
    public UncompletedEventController(Event event){
        this.event = event;
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
        setTooltips();

        if(this.eventlist == null){
            //disable the actions if the eventlist is null (this is the case when the class is used in the CategorylistScene)
            this.deleteButton.setDisable(true);
            this.checkBox.setDisable(true);
        } else if(this.eventlist.getExpiryDateString() != null && StatisticsManager.daysLeft(this.eventlist)<0){
            //disable all actions if the list is expired
            log.info("List expired. Actions are disabled");
            this.deleteButton.setDisable(true);
            this.checkBox.setDisable(true);
        }
    }

    /**
     * sets the Tooltips for the whole scene
     */
    private void setTooltips(){
        Tooltip checkBoxTt = new Tooltip("Complete the event");
        checkBoxTt.setShowDelay(Duration.millis(100));
        Tooltip deleteButtonTt = new Tooltip("Modify the event");
        deleteButtonTt.setShowDelay(Duration.millis(100));
        this.checkBox.setTooltip(checkBoxTt);
        this.deleteButton.setTooltip(deleteButtonTt);
    }
}
