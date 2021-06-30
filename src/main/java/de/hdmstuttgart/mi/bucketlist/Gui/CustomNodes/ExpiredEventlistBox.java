package de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController.EventlistBoxController;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ExpiredEventlistBox extends AnchorPane implements Box{

    EventlistBoxController eventlistBoxController;

    public ExpiredEventlistBox(ListManager listManager, BorderPane borderPane){

        super();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/ExpiredEventlistBox.fxml"));
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
