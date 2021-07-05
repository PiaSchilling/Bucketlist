package de.hdmstuttgart.mi.bucketlist.View.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController.CompletedEventController;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CompletedEventBox extends AnchorPane {

   CompletedEventController completedEventController;

    public CompletedEventBox(Eventlist eventlist){
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/CompletedEventBox.fxml"));
            completedEventController = new CompletedEventController(eventlist);
            loader.setController(completedEventController);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public CompletedEventBox(Event event){
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/CompletedEventBox.fxml"));
            completedEventController = new CompletedEventController(event);
            loader.setController(completedEventController);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public CompletedEventController getEventCompletedController() {
        return this.completedEventController;
    }
}
