package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.StatisticListsBox;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticController implements Listener, Initializable {


    private final ListManager listManager;
    private final StatisticsManager statisticManager;
    private final BorderPane borderPane;

    private static final Logger log = LogManager.getLogger(StatisticController.class);


    public StatisticController(ListManager listManager, BorderPane borderPane){
        this.listManager = listManager;
        this.borderPane = borderPane;
        this.statisticManager= new StatisticsManager(listManager);

        this.listManager.addListener(this);
    }


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label listsCreatedLabel;

    @FXML
    private Label eventsCompletedLabel;


    public void setCreatedListsLabel(){
        log.debug("setCreatedListsLabel() method started" );
        this.listsCreatedLabel.setText(statisticManager.countLists());
    }



    public void setEventsCompletedLabel(){
        log.debug("setEventsCompletedLabel() method started" );
        this.eventsCompletedLabel.setText(statisticManager.countCompletedEvents());
    }



    public void showStatistics(){
        log.debug("showStatistics() has started ");
        this.flowPane.getChildren().clear();

        setCreatedListsLabel();
        setEventsCompletedLabel();
        log.debug("Labels set");


        for (int i = 0; i < this.listManager.getEventlists().size(); i++) {

            String eventlistname = this.listManager.getEventlists().get(i).getName();
            System.out.println(eventlistname);
            StatisticListsBox statisticListsBox = new StatisticListsBox(this.listManager, this.borderPane);
            statisticListsBox.getStatisticListsController().setEventlistNameLabel(eventlistname);

            this.flowPane.getChildren().add(statisticListsBox);

           this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
           this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
           log.debug("list nr." + i + " finished");

        }
        log.debug("lists set");
        log.debug("showStatistics() ended ");
    }


    @Override
    public void update() {
        log.debug("showStatistics() in update() has started ");
        showStatistics();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("showStatistics() in initialize() has started ");
        showStatistics();
    }


}
