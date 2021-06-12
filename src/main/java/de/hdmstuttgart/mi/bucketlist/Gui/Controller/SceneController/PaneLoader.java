package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PaneLoader {


    private BorderPane borderPane;




    // "/fxml/eventlist.fxml"));

    public static AnchorPane loadAnchorPane(Object controller, String fxmlFile) {

        AnchorPane anchorPane = new AnchorPane();

        String filePath = "/fxml/Scenes/" + fxmlFile + ".fxml";

        FXMLLoader loader = new FXMLLoader(PaneLoader.class.getResource(filePath));

        loader.setController(controller);

        try {
            anchorPane= loader.load();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        return anchorPane;
    }


}
