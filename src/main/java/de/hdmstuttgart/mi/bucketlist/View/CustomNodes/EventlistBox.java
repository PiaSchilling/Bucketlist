package de.hdmstuttgart.mi.bucketlist.View.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController.EventlistBoxController;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * custom node which displays an eventlist
 */
public class EventlistBox extends AnchorPane implements Box{

    EventlistBoxController eventlistBoxController;

    public EventlistBox(ListManager listManager, BorderPane borderPane){

        super();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/EventlistBox.fxml"));
            eventlistBoxController = new EventlistBoxController(listManager,borderPane);
            loader.setController(eventlistBoxController);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public EventlistBoxController getController() {
        return eventlistBoxController;
    }
}
