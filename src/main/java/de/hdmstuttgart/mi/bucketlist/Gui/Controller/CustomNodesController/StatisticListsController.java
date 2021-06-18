package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController.StatisticController;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StatisticListsController {

    private final ListManager listManager;
    private String eventlistName;
    private BorderPane borderPane;

    private static final Logger log = LogManager.getLogger(StatisticListsController.class);

    @FXML
    private Label eventlistNameLabel;


    public StatisticListsController(ListManager listManager, BorderPane borderPane) {
        this.listManager = listManager;
        this.borderPane = borderPane;
    }


    public void setEventlistNameLabel(String eventlistName){
        log.debug("setEventlistNameLabel() for a statistic list has started ");
        this.eventlistName = eventlistName;
        this.eventlistNameLabel.setText(eventlistName.toUpperCase());
    }
}
