package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.ConfirmationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;



public class PaneLoader {

    private static final Logger log = LogManager.getLogger(PaneLoader.class);

    public static AnchorPane loadAnchorPane(Object controller, String fxmlFile) {

        AnchorPane anchorPane = new AnchorPane();

        String filePath = "/fxml/Scenes/" + fxmlFile + ".fxml";

        FXMLLoader loader = new FXMLLoader(PaneLoader.class.getResource(filePath));

        loader.setController(controller);
        System.out.println(fxmlFile);

        try {
            anchorPane= loader.load();
        }catch (IOException ioException){
            log.error(ioException.getMessage());

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
            log.error(ioException.getMessage());
        }

        stage.setScene(new Scene(popUp));
        stage.show();
    }

    public static boolean loadConfirmationWindow(String message){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(PaneLoader.class.getResource("/fxml/PopUpWindows/ConfirmationWindow.fxml"));

        ConfirmationController confirmationController = new ConfirmationController(message);
        loader.setController(confirmationController);

        Parent popUp = null;

        try {
            popUp = loader.load();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }

        stage.setScene(new Scene(popUp));
        stage.showAndWait();

        boolean choice = confirmationController.getChoice();

        return choice;
    }


}
