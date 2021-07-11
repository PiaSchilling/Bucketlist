package de.hdmstuttgart.mi.bucketlist;

import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.MenuController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.Threads.PersistenceRunnable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;


/**
 * this is going to be the entry point for our application
 */
public class Main extends Application {

    private final ListManager listManager = new ListManager();
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.window = stage;
        //this.listManager.load();

        PersistenceRunnable persistenceRunnable = new PersistenceRunnable(this.listManager);
        Thread persistenceThread = new Thread(persistenceRunnable,"Persistence");
        persistenceThread.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scenes/menu.fxml"));
        MenuController menuController = new MenuController(this.listManager);
        loader.setController(menuController);
        Parent parent = loader.load();

        Scene scene1 = new Scene(parent);

        URL url = this.getClass().getResource("/images/Logo_TitleBar.png");
        Image image=new Image(url.toString());
        //com.apple.eawt.Application.getApplication().setDockIconImage(image);
        stage.getIcons().add(image);


        stage.setTitle("The Bucketlist");

        stage.setScene(scene1);
        stage.show();

        //defines what should happen, when window is closed
        this.window.setOnCloseRequest(e -> {
            e.consume();
            closeProgramm();
        });
    }

    /**
     * opens confimrationbox
     * if user selects yes -> window will be closed after saving everything
     * no -> dialogue closes, nothing will happen
     */
    private void closeProgramm(){
        if(PaneLoader.loadConfirmationWindow("Are you sure you want to quit ?")){
            this.listManager.save();
            this.window.close();
        }
    }
}
