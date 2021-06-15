
package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.EventCreationController;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.CompletedEventBox;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.UncompletedEventBox;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventlistController implements Initializable, Listener {



   private final ListManager listManager;
   // private final StatisticManager statisticManager;

   private final Eventlist eventlist;
   private final String eventlistName;

   private final BorderPane borderPane;

   private static final Logger log = LogManager.getLogger(EventlistController.class);


   public EventlistController(String eventlistName,ListManager listManager,BorderPane borderPane){
      this.listManager = listManager;
      this.eventlistName = eventlistName;
      this.borderPane = borderPane;

      this.eventlist = this.listManager.getEventlistByName(eventlistName);
      this.eventlist.addListener(this);
   }


   @FXML //label for the name of the eventlist
   private Label listNameLabel;

   @FXML
   private FlowPane flowpane;

   @FXML
   private ScrollPane scrollpane;

   @FXML //addEvent
   private Button addEventButton;

   @FXML //is shown if the list date is expired
   private Label expiredLabel;

   @FXML
   private ImageView backButton;


    /**
    * shows all Events in the Eventlist
    * filters whether the event is completed --> use the layout for the completed box, or if its not completed --> use the layout for uncompleted events
    */
   public void showEvents(){

      this.flowpane.getChildren().clear();

      AnchorPane pane;

      //if there arent any events in the eventlist a picture is loaded
      if(this.eventlist.getEvents().size() == 0){
         ImageView imageView = new ImageView();
         URL url = this.getClass().getResource("/images/NoEventsCreated.png");
         Image image = new Image(url.toString());
         imageView.setImage(image);
         this.flowpane.getChildren().add(imageView);
      }

      for (int i = 0; i < this.eventlist.getEvents().size(); i++) {
         Event temp = this.eventlist.getEvents().get(i);
         if(!temp.getIsCompleted()){
            UncompletedEventBox box = new UncompletedEventBox(this.eventlist);
            box.getUncompletedEventController().setEventnameLabel(temp.getEventName());
            box.getUncompletedEventController().setEventcategoryLabel(temp.getEventCategory().toString());
            pane = box;
         }else{
            System.out.println("name" + temp.getEventName());
            System.out.println("url" + temp.getEventImageUrl());
            CompletedEventBox box = new CompletedEventBox(this.eventlist);
            box.getEventCompletedController().setEventNameLabel(temp.getEventName());
            box.getEventCompletedController().setEventCatLabel(temp.getEventCategory().toString());
            box.getEventCompletedController().setEventDateLabel(temp.getEventDateString());
            box.getEventCompletedController().setEventDescrptionLabel(temp.getEventDescription());
            box.getEventCompletedController().setEventImageView(temp.getEventImageUrl());
            pane = box;
         }
         this.flowpane.getChildren().add(pane);
      }
   }

    /**
    * open a dialogue where the user can create a new event
    */
   public void openCreationWindow(){
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setOpacity(0.95);
      Parent popUp;
      stage.initModality(Modality.APPLICATION_MODAL);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUpWindows/EventCreationWindow.fxml"));
      EventCreationController eventCreationController = new EventCreationController(this.eventlist);
      loader.setController(eventCreationController);

      try {
         popUp = loader.load();
         stage.setScene(new Scene(popUp,565,243));
         stage.show();
      } catch (IOException ioException) {
         ioException.printStackTrace();
      }
   }

   /**
    * action for the backButton, switches back to the listsScene
    */
   @FXML
   void switchToListsScene(){
      ListsController listsController = new ListsController(this.listManager,this.borderPane);
      AnchorPane anchorPane = PaneLoader.loadAnchorPane(listsController,"lists");
      this.borderPane.setCenter(anchorPane);
   }

    /**
    * sets the eventlistname as headline
    * if the eventlist expired, the buttons are disabled and a message for the user is set
    * @param location -- not used
    * @param resources -- not used
    */
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      this.listNameLabel.setText(this.eventlistName);
      showEvents();
      //dont show the scrollbar
      this.scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      if(this.eventlist.getExpiryDateString() != null && StatisticsManager.daysLeft(this.eventlist)<0){
         this.addEventButton.setDisable(true);
         this.expiredLabel.setText("This list is expired you cant modify anything anymore");
      }
   }

    /**
    * called by the observed class (here eventlist class)
    */
   @Override
   public void update() {
      showEvents();
      log.info("Listview updated");
      log.info("Events are shown:" + this.eventlist.toString());
      //todo remove this log
      log.info("ListManager:" + this.listManager.getEventlists().toString());
   }

}

