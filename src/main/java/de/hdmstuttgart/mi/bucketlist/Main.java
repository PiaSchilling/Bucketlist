package de.hdmstuttgart.mi.bucketlist;

import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.MenuController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.Threads.PersistenceRunnable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * this is going to be the entry point for our application
 */
public class Main extends Application {

    private final ListManager listManager = new ListManager();
    private Stage window;

    public static void main(String[] args) {
/*
        //the only listManager that should exist
        ListManager listManager = new ListManager();
        //gets the listManager handed over, so it can use it too
        CategoryManager categoryManager = new CategoryManager(listManager);

        listManager.createEventlist("List1");
        listManager.createEventlist("List2");
        listManager.createEventlist("List3",4,6,2021);

        listManager.addEventToList("event1",Category.SKILLS,"List1");
        listManager.addEventToList("event2",Category.SKILLS,"List1");
        listManager.addEventToList("event3",Category.SKILLS,"List1");

        listManager.addEventToList("event1",Category.SKILLS,"List2");
        listManager.addEventToList("event2",Category.SKILLS,"List2");
        listManager.addEventToList("event3",Category.SKILLS,"List2");

        listManager.addEventToList("event1",Category.SKILLS,"List3");
        listManager.addEventToList("event2",Category.SKILLS,"List3");
        listManager.addEventToList("event3",Category.SKILLS,"List3");


        listManager.completeEvent("event1","List1","URL","Beschreibung",3,6,2021);
        listManager.completeEvent("event1","List2","URL","Beschreibung",12,12,2021);
        listManager.completeEvent("event1","List3","URL","Beschreibung",12,12,2021);

        listManager.save();
        listManager.load();


        StatisticsManager statisticsManager = new StatisticsManager(listManager);

        System.out.println( "days left: " + statisticsManager.daysLeft("List3"));
        System.out.println("coutn lists: " + statisticsManager.countLists());
        System.out.println("completed events: " + statisticsManager.countCompletedEvents());
        System.out.println(statisticsManager.countCompletedEventsPerList("List2"));
        System.out.println(statisticsManager.calculatePercentageCompletedEventsPerList("List1"));

        System.out.println(categoryManager.getFilledCatgeoryLists().get(Category.SKILLS));*/


        // GUI
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
