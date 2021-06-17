package de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController.StatisticListsController;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class StatisticListsBox extends AnchorPane {

    StatisticListsController statisticListsController;

    public StatisticListsBox(ListManager listManager, BorderPane borderPane){

        super(); // Konstruktor der Elternklasse AnchorPane

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/StatisticListsController.fxml"));
            statisticListsController = new StatisticListsController(listManager,borderPane);
            loader.setController(statisticListsController);
            Node node = loader.load();      // laden FXML rein
            this.getChildren().add(node);   // referenziert auf StatisticListsBox
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public StatisticListsController getStatisticListsController() {
        return statisticListsController;
    }
}
