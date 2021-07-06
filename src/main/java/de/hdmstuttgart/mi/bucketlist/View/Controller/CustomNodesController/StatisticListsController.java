package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.StatisticEventlistController;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StatisticListsController {

    private final ListManager listManager;
    private String eventlistName;
    private final BorderPane borderPane;

    private static final Logger log = LogManager.getLogger(StatisticListsController.class);

    @FXML
    private Label eventlistNameLabel;


    public StatisticListsController(ListManager listManager, BorderPane borderPane) {
        this.listManager = listManager;
        this.borderPane = borderPane;
    }

    /**
     * sets the Headline in the 2nd Statistic Scene and displays the name of the handed over eventlist
     * @param eventlistName - the name which should be set to the label
     */
    public void setEventlistNameLabel(String eventlistName){
        log.debug("setEventlistNameLabel() method has started ");
        this.eventlistName = eventlistName;
        this.eventlistNameLabel.setText(eventlistName.toUpperCase());
    }

    /**
     * makes it switch to the 2nd Statistic Scene
     */
    @FXML
    public void switchToStatisticListScene() {
        log.debug("switchToStatisticListScene() in StatisticListsController has started");
        StatisticEventlistController statisticEventlistController= new StatisticEventlistController(this.listManager, this.eventlistName,this.borderPane);
        AnchorPane anchorPane = PaneLoader.loadAnchorPane(statisticEventlistController,"statisticEventlist");
        this.borderPane.setCenter(anchorPane);
    }
}
