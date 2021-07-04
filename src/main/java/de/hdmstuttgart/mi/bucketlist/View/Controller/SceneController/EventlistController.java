
package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController.EventCreationController;
import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.CompletedEventBox;
import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.UncompletedEventBox;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

   @FXML //todo should be a button not an imageview
   private ImageView backButton;

   @FXML
   private ImageView expiredImage;


    /**
    * shows all Events in the Eventlist
    * filters whether the event is completed --> use the layout for the completed box, or if its not completed --> use the layout for uncompleted events
    */
   public void showEvents(){

      setScrollBehavior();
      this.flowpane.getChildren().clear();

      AnchorPane pane;

      //if there arent any events in the eventlist a picture is loaded
      if(this.eventlist.getEvents().size() == 0){
         ImageView imageView = new ImageView();
         URL url = this.getClass().getResource("/images/NoEvent.png");
         Image image = new Image(url.toString());
         imageView.setFitHeight(645);
         imageView.setFitWidth(1041);
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
      EventCreationController eventCreationController = new EventCreationController(this.eventlist);
      PaneLoader.loadPopUpWindow(eventCreationController,"EventCreationWindow");
   }

   /**
    * action for the backButton, switches back to the listsScene
    */
   @FXML
   void switchToListsScene(){
      ListsController listsController = new ListsController(this.listManager);
      listsController.injectBorderPane(this.borderPane);
      AnchorPane anchorPane = PaneLoader.loadAnchorPane(listsController,"lists");
      this.borderPane.setCenter(anchorPane);
   }

   /**
    * computes the height of the flowpane according to the number of boxes so
    * the scrollpane will be scrollable if there are many boxes
    */
   private void setScrollBehavior(){
      double height = this.eventlist.getEvents().size() * 130;
      for (int i = 0; i < this.eventlist.getEvents().size(); i++) {
         if(this.eventlist.getEvents().get(i).getIsCompleted()){
            height += 190;
         }
      }
      this.flowpane.setPrefHeight(height);
   }

    /**
    * sets the eventlistname as headline
    * if the eventlist expired, the buttons are disabled and a message for the user is set
    * @param location -- not used
    * @param resources -- not used
    */
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      //prepare the scene
      this.listNameLabel.setText(this.eventlistName.toUpperCase());
      showEvents();
      setTooltips();
      //disable all actions if the list is expired
      if(this.eventlist.getExpiryDateString() != null && StatisticsManager.daysLeft(this.eventlist)<0){
         log.info("List expired. Actions are disabled");
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
   }

   /**
    * sets the Tooltips for the whole scene
    */
   private void setTooltips(){
     Tooltip addEventTt = new Tooltip("Add a new event");
     addEventTt.setShowDelay(Duration.millis(100));
     this.addEventButton.setTooltip(addEventTt);
   }

}

