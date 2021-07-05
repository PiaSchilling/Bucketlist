package de.hdmstuttgart.mi.bucketlist.View.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController.UncompletedEventController;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UncompletedEventBox extends AnchorPane {

    private UncompletedEventController uncompletedEventController;

    public UncompletedEventBox(Eventlist eventlist){

        super();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/UncompletedEventBox.fxml"));
            //FXMLLoader loader = new FXMLLoader(new File("src/main/resources/fxml/EventUncompleted.fxml").toURI().toURL());
            this.uncompletedEventController = new UncompletedEventController(eventlist);
            //set the controller for the boxes
            loader.setController(uncompletedEventController);
            Node node = loader.load();
            this.getChildren().add(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public UncompletedEventBox(Event event){
        super();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/UncompletedEventBox.fxml"));
            //FXMLLoader loader = new FXMLLoader(new File("src/main/resources/fxml/EventUncompleted.fxml").toURI().toURL());
            this.uncompletedEventController = new UncompletedEventController(event);
            //set the controller for the boxes
            loader.setController(uncompletedEventController);
            Node node = loader.load();
            this.getChildren().add(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public UncompletedEventController getUncompletedEventController() {
        return this.uncompletedEventController;
    }
}
