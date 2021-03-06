package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticEventlistController implements Initializable, Listener {

    private final ListManager listManager;
    private final StatisticsManager statisticManager;

    private final Eventlist eventlist;
    private String eventlistName;

    private final BorderPane borderPane;

    private static final Logger log = LogManager.getLogger(StatisticEventlistController.class);


    @FXML
    private Label eventlistNameLabel;

    @FXML
    private Button backButton;

    @FXML
    private ProgressBar progressProgressBar;

    @FXML
    private ImageView backButtonImage;

    @FXML
    private Label listDate;

    @FXML
    private Label listsCompletedEventsPerList;

    @FXML
    private Label listsCreatedEventsLabel;

    @FXML
    private Label listDateLabel;


    @FXML
    private Label listDateTextLabel1, listDateTextLabel2;

    @FXML
    private Label listsLeftDays;

    @FXML
    private Label listPercentageLabel;


    public StatisticEventlistController(ListManager listManager, String eventlistName, BorderPane borderPane) {
        this.listManager = listManager;
        this.eventlistName = eventlistName;
        this.borderPane = borderPane;
        this.statisticManager= new StatisticsManager(listManager);


        this.eventlist = this.listManager.getEventlistByName(eventlistName);
        this.eventlist.addListener(this);
    }


    /**
     * sets the Subheadline with the correct eventlist name
     */
    public void setEventlistNameLabel() {
        log.debug("setEventlistNameLabel() has started");
        this.eventlistNameLabel.setText(this.eventlistName.toUpperCase());
    }



    /**
     * sets the values for the progress bar
     */
    public void setProgressProgressBar(){
        log.debug("setProgressProgressBar() has started");
        this.progressProgressBar.progressProperty().set(statisticManager.calculatePercentageCompletedEventsPerList(this.eventlistName));
    }



    /**
     * sets the label and displays how many events are created in this list
     */
    public void setCreatedEventsPerListLabel(){
        log.debug("setCreatedEventsPerListLabel() has started");
        this.listsCreatedEventsLabel.setText(String.valueOf((statisticManager.countEventsPerList(this.eventlistName))));
    }



    /**
     * sets the label and displays how many events are completed in this list
     */
    public void setCompletedEventsPerListLabel(){
        log.debug("setCompletedEventsPerListLabel() has started");
        this.listsCompletedEventsPerList.setText(String.valueOf((statisticManager.countCompletedEventsPerList(this.eventlistName))));
    }


    /**
     * sets the label and displays how much of the list is completed in percent.
     */
    public void setPercentageLabel(){
        log.debug("setPercentageLabel() has started");
        if(String.valueOf(statisticManager.calculatePercentageCompletedEventsPerListAsString(this.eventlistName)).equals("NaN %")){
            this.listPercentageLabel.setText("no events set or completed yet");
        }else{
            this.listPercentageLabel.setText(String.valueOf((statisticManager.calculatePercentageCompletedEventsPerListAsString(this.eventlistName))));
        }
    }


    /**
     * sets the label and displays when the EVENTLIST is due.
     * if the exipry date is not set for the eventlist, the other labels change as well.
     */
    public void setDateLabel() {
        log.debug("setDateLabel() has started");
        if (eventlist.getExpiryDateString() != null) {
            this.listDateTextLabel1.setText("ON THE");
            this.listDateLabel.setText(eventlist.getExpiryDateString());
            this.listDateTextLabel2.setText("THE EVENTLIST IS DUE");
        }else {
            this.listDateTextLabel1.setText("EXPIRY DATE FOR '" + this.eventlistName.toUpperCase() + "'");
            this.listDateLabel.setText("IS NOT SET.");
            this.listDateTextLabel2.setText("");
        }
    }


    /**
     * sets the label and displays how many days the user got to fullfill his goal to complete a bucketlist
     * if the date is due, the text color changes from white to orange and is displayed as negative value
     */
    public void setLeftDays() {
        log.debug("daysLeft() set on "+ statisticManager.daysLeftAsString(this.eventlistName));
        this.listsLeftDays.setText(statisticManager.daysLeftAsString(this.eventlistName));

        if(statisticManager.daysLeftAsInt(this.eventlistName) < 0){
            this.listsLeftDays.setStyle("-fx-text-fill: #ffa766");
        }
    }


    /**
     * makes it switch to the scene before (statistic scene)
     */
    @FXML
    void switchToStaticticScene() {
        log.debug("switchToStatisticScene has started");
        StatisticController statisticController= new StatisticController(this.listManager,this.borderPane);
        AnchorPane anchorPane= PaneLoader.loadAnchorPane(statisticController,"statistics");
        this.borderPane.setCenter(anchorPane);
    }



    /**
     * shows the StatisticEventlist
     */
    public void showStatisticEventlist(){
        log.debug("showStatisticsEventlist() has started ");

        setEventlistNameLabel();
        setCompletedEventsPerListLabel();
        setCreatedEventsPerListLabel();
        setPercentageLabel();
        setLeftDays();
        log.debug("labels set");

        setProgressProgressBar();
        log.debug("progress bar set");

        setDateLabel();
        log.debug("set date label set");

        log.debug("showStatisticEventlist() method ended");
    }


    /**
     * updates the statisticEventlist scene if any changes occurs
     */
    @Override
    public void update() {
        log.debug("showStatisticEventlist() in update() has started ");
        showStatisticEventlist();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("showStatisticEventlist() in initialize() has started ");

        showStatisticEventlist();
    }
}
