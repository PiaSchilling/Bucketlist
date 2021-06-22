package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.EventlistCreationController;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.EventlistBox;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.ExpiredEventlistBox;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * EventlistsController called listsController because otherwise it would be to similar to EvenlistController
 */
public class ListsController implements Initializable, Listener {

    private final ListManager listManager;
    private final StatisticsManager statisticManager;
    private final BorderPane borderPane;

    public ListsController(ListManager listManager, BorderPane borderPane){
        this.listManager = listManager;
        this.statisticManager = new StatisticsManager(listManager);
        this.borderPane = borderPane;

        this.listManager.addListener(this);
    }


    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Button addListButton;


    /**
     * opens a new window where a new Eventlist can be added
     */
   public void openCreationWindow()  {
       EventlistCreationController eventlistCreationController = new EventlistCreationController(this.listManager);
       PaneLoader.loadPopUpWindow(eventlistCreationController,"EventlistCreationWindow");
   }

    /**
     * shows all the eventlists which are currently created
     * to make it possible to add new lists, the flowpane is cleared at the beginning
     * chooses whether all events in the list are completed different nodes
     */
    private void showLists(){

        //clear and then show all new
        this.flowpane.getChildren().clear();

        //sets an image if there arent any eventlists created
        if(this.listManager.getEventlists().size() == 0){
            ImageView imageView = new ImageView();
            URL url = this.getClass().getResource("/images/NoEventlists.png");
            Image image = new Image(url.toString());
            imageView.setFitHeight(645);
            imageView.setFitWidth(1041);
            imageView.setImage(image);
            this.flowpane.getChildren().add(imageView);
        }

        for (int i = 0; i < this.listManager.getEventlists().size(); i++) {

            String eventlistname = this.listManager.getEventlists().get(i).getName();
            String eventCount = String.valueOf(this.statisticManager.countEventsPerList(eventlistname));
            String expiryDate = this.listManager.getEventlists().get(i).getExpiryDateString();

            //if expiry date is in the past, expired box will be loaded
            if(expiryDate != null && this.statisticManager.daysLeftAsInt(eventlistname) < 0){
                ExpiredEventlistBox expiredEventlistBox = new ExpiredEventlistBox(this.listManager,this.borderPane);
                expiredEventlistBox.getEventlistBoxController().setEventlistname(eventlistname);
                expiredEventlistBox.getEventlistBoxController().setNumberOfEvents(eventCount);
                expiredEventlistBox.getEventlistBoxController().setExpiryDate(expiryDate);
                this.flowpane.getChildren().add(expiredEventlistBox);
            }else{
                EventlistBox eventlistBox = new EventlistBox(this.listManager,this.borderPane);
                eventlistBox.getEventlistBoxController().setEventlistname(eventlistname);
                eventlistBox.getEventlistBoxController().setNumberOfEvents(eventCount);
                eventlistBox.getEventlistBoxController().setExpiryDate(expiryDate);
                this.flowpane.getChildren().add(eventlistBox);
            }
        }
    }

    /**
     * does whatever should happen after a model class has been modified
     */
    @Override
    public void update(){
        showLists();
    }

    /**
     * prepare scene: showLists, setTooltips and dont show scrollbar
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLists();
        setTooltips();
        this.scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * sets the Tooltips for the whole scene
     */
    private void setTooltips(){
        Tooltip addListTt = new Tooltip("Add a new eventlist");
        addListTt.setShowDelay(Duration.millis(100));
        this.addListButton.setTooltip(addListTt);
    }
}
