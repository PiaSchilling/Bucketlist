package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class PaneLoader {


    //private BorderPane borderPane;




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

    public static void loadPopUpWindow(Object controller, String fxml){

        String fxmlPath = "/fxml/PopUpWindows/" + fxml + ".fxml";

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(0.95);

        FXMLLoader loader = new FXMLLoader(PaneLoader.class.getResource(fxmlPath));

        loader.setController(controller);

        Parent popUp = null;

        try {
            popUp = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        stage.setScene(new Scene(popUp));
        stage.show();
        System.out.println("Loaded");
    }


}
