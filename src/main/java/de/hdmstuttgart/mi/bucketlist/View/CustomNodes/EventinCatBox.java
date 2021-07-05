package de.hdmstuttgart.mi.bucketlist.View.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController.EventinCatBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class EventinCatBox extends AnchorPane {

    EventinCatBoxController eventinCatBoxController;

    public EventinCatBox(CategoryManager categoryManager, BorderPane borderPane) {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/CategoryBox.fxml"));
            eventinCatBoxController = new EventinCatBoxController(categoryManager, borderPane);
            loader.setController(eventinCatBoxController);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public EventinCatBoxController getController() {
        return this.eventinCatBoxController;
    }
}
