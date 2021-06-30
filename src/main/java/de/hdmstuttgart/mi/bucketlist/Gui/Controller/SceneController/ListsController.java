package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController.EventlistCreationController;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.Box;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.EventlistBox;
import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.ExpiredEventlistBox;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * EventlistsController called listsController because otherwise it would be to similar to EvenlistController
 */
public class ListsController implements Initializable, Listener, ListChangeListener<Eventlist> {

    private final ListManager listManager;
    private final StatisticsManager statisticManager;
    private BorderPane borderPane;

    //holds all the eventlistBoxes (GUI elements), static because otherwise the list would be empty after scene change
    //this way, the boxes dont have to be recreated after every scene change
    private static final ObservableList<Box> boxContainer = FXCollections.observableArrayList();

    private static final Logger log = LogManager.getLogger(ListsController.class);

    public ListsController(ListManager listManager) {
        this.listManager = listManager;
        this.statisticManager = new StatisticsManager(listManager);

        this.listManager.addListener(this); //todo reomove

        //add this class as a listener to the observable-list in the listManager, so the GUI updates after changes to the list
        this.listManager.addListListener(this);

        //add a listener to the boxContainer so the GUI can update itself after boxes have been created/removed
        boxContainer.addListener(new ListChangeListener<Box>() {
            @Override
            public void onChanged(Change<? extends Box> change) {
                log.debug("GUI - BoxContainer-onChanged received a change");

                //avoid concurrentModifyException (otherwise addedSubList would be used twice at the same time)
                List<Box> temp = new ArrayList<>();
                while(change.next()){
                    if(change.wasAdded()){
                        for(Box box: change.getAddedSubList()){
                            temp.add(box);
                        }
                        showBoxes(temp);
                    }else if(change.wasRemoved()){
                        unshowBoxes(change.getRemoved());
                    }
                }
            }
        });
    }


    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Button addListButton;

    private ImageView noListsView;


    /**
     * opens a new window which allows the user to add a new eventlist
     */
    public void openCreationWindow() {
        EventlistCreationController eventlistCreationController = new EventlistCreationController(this.listManager);
        PaneLoader.loadPopUpWindow(eventlistCreationController, "EventlistCreationWindow");
    }

    /**
     * creates a EventlistBox (GUI Element) and adds it the the boxContainer
     * @param eventlistname -- the name of the eventlist
     * @param eventCount -- number of events in this list
     * @param expiryDate -- expiry date of the eventlist
     */
    private void createEventlistBox (String eventlistname, String eventCount, String expiryDate){
        EventlistBox eventlistBox = new EventlistBox(this.listManager, this.borderPane);
        System.out.println(this.borderPane);
        eventlistBox.getController().setEventlistname(eventlistname);
        eventlistBox.getController().setNumberOfEvents(eventCount);
        eventlistBox.getController().setExpiryDate(expiryDate);

        boxContainer.add(eventlistBox);
        log.debug("GUI - Box added to container: " + eventlistname);
    }

    /**
     * creates a expiredEventlistBox (GUI Element) and adds it the the boxContainer
     * @param eventlistname -- the name of the eventlist
     * @param eventCount -- number of events in this list
     * @param expiryDate -- expiry date of the eventlist
     */
    private void createExpiredEventlistBox (String eventlistname, String eventCount, String expiryDate){
        ExpiredEventlistBox expiredEventlistBox = new ExpiredEventlistBox(this.listManager, this.borderPane);
        expiredEventlistBox.getController().setEventlistname(eventlistname);
        expiredEventlistBox.getController().setNumberOfEvents(eventCount);
        expiredEventlistBox.getController().setExpiryDate(expiryDate);

        boxContainer.add(expiredEventlistBox);
        log.debug("GUI - Box added to container: " + eventlistname);
    }

    /**
     * calls the right methods for the box-creation of the eventlists
     * createExpiredEventlistBox for expired eventlists and createEventlistBox for the other ones
     * @param eventlists -- the lists for which the boxes should be created
     */
    private void createBoxes(List<? extends Eventlist> eventlists){

        for (int i = 0; i < eventlists.size(); i++) {
            String eventlistname = eventlists.get(i).getName();
            String eventCount = String.valueOf(this.statisticManager.countEventsPerList(eventlistname));
            String expiryDate = eventlists.get(i).getExpiryDateString();

            //if expiry date is in the past, expired box will be loaded
            if (expiryDate != null && this.statisticManager.daysLeftAsInt(eventlistname) < 0) {
                createExpiredEventlistBox(eventlistname,eventCount,expiryDate);
                log.debug("GUI - createExpiredEventlistBox called for " + eventlistname);
            } else {
                createEventlistBox(eventlistname,eventCount,expiryDate);
                log.debug("GUI - createEventlistBox called for " + eventlistname);
            }
        }
    }

    /**
     * deletes the associated box to the lists that were deleted in the listManager
     * identifies the associated box by the eventlistName //todo find a better way doing that (cant use LinkedHashMap because its not observable)
     * @param eventlists -- the lists that were deleted in the listManager
     */
    private void removeBoxes(List<? extends Eventlist> eventlists){
        log.debug("GUI - removeBoxes started");
        for (int i = 0; i < boxContainer.size(); i++) {
            if(boxContainer.get(i) instanceof EventlistBox){
                EventlistBox eventlistBox = (EventlistBox) boxContainer.get(i);
                String boxName = eventlistBox.getController().getEventlistname();

                if( eventlists
                        .stream()
                        .anyMatch(eventlist -> eventlist.getName().equals(boxName))){
                    boxContainer.remove(eventlistBox);
                    log.debug("GUI - " + boxName + "-box removed ");
                }
            }else if(boxContainer.get(i) instanceof ExpiredEventlistBox){
                ExpiredEventlistBox expiredEventlistBox = (ExpiredEventlistBox) boxContainer.get(i);
                String boxName = expiredEventlistBox.getController().getEventlistname();

                if(eventlists
                        .stream()
                        .anyMatch(eventlist -> eventlist.getName().equals(boxName))){
                    boxContainer.remove(expiredEventlistBox);
                    log.debug("GUI - " + boxName + "-box removed ");
                }
            }else{
                log.debug("GUI - No Box removed");
            }
        }
        log.debug("GUI - removeBoxes ended");
    }

    /**
     * adds the boxes which are contained in the boxes list (parameter) to the flowpane
     * runLater: because the persistence Thread might trigger the call of this Method
     * @param boxes -- the boxes which should be added
     */
    private void showBoxes(List<? extends Box> boxes) {
        log.debug("GUI - Show Boxes started");
        Platform.runLater(() -> {
            setScrollBehavior();
            if(boxContainer.size() == 0){
                this.flowpane.getChildren().add(this.noListsView);
            }else{
                this.flowpane.getChildren().remove(this.noListsView);
                this.flowpane.getChildren().addAll((Collection<? extends Node>) boxes);
            }
        });
        log.debug("GUI - Show Boxes ended");
    }

    /**
     * removes the Boxes, which have been deleted, from the flowpane
     * @param boxes -- the Box which should be deleted
     */
    private void unshowBoxes(List<? extends Box> boxes){
        log.debug("GUI - unshowBoxes started");
        flowpane.getChildren().removeAll(boxes);
        setScrollBehavior();

        //show noLists Image if all lists have been removed
        if(boxContainer.size() == 0){
            this.flowpane.getChildren().add(this.noListsView);
        }
        log.debug("GUI - unshowBoxes ended");
    }

    /**
     * computes the height of the flowpane according to the number of boxes so
     * the scrollpane will be scrollable if there are many boxes
     */
    private void setScrollBehavior(){
        double height = (Math.ceil((double)boxContainer.size()/2)) * 130;
        this.flowpane.setPrefHeight(height);
    }

    /**
     * creates the imageView which is shown when no list is created
     */
    private void createNoListsView(){
        ImageView imageView = new ImageView();
        URL url = this.getClass().getResource("/images/NoEventlists.png");
        Image image = new Image(url.toString());
        imageView.setFitHeight(645);
        imageView.setFitWidth(1041);
        imageView.setImage(image);
        this.noListsView = imageView;
    }

    /**
     * does whatever should happen after a model class has been modified
     */
    @Override
    public void update() {
        log.error("NOT IMPLEMENTED YET");
    }

    /**
     * prepares scene: shows the lists (there is only displayed sth. if the boxes are already created and added to the boxContainer)
     * @param location  -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("GUI - initialize");
        setTooltips();
        createNoListsView();
        showBoxes(boxContainer);
    }

    /**
     * Listens the Observable List of the ListManager which contains all Eventlists
     * calls create Boxes if an eventlist was added so a box for the eventlist can be created
     * calls remove Boxees if an eventlist was deleted so the matching box can be deleted
     * @param change -- the change of the eventlists list
     */
    @Override
    public void onChanged(Change<? extends Eventlist> change) {
        log.debug("GUI - Eventlists-onChanged received a change");
        while(change.next()){
            if(change.wasAdded()){
                log.debug("GUI - createBoxes called");
                createBoxes(change.getAddedSubList());
            }else if(change.wasRemoved()){
                log.debug("GUI - removeBoxes called");
                removeBoxes(change.getRemoved());
            }
        }
    }

    /**
     * sets the Tooltips for the whole scene
     */
    private void setTooltips() {
        Tooltip addListTt = new Tooltip("Add a new eventlist");
        addListTt.setShowDelay(Duration.millis(100));
        this.addListButton.setTooltip(addListTt);
    }


    /**
     * set the borderPane (is needed for switching to the eventlistscene
     * cant be set in the constructor (see menuController for further information about that)
     * @param borderPane -- border pane which holds the gui content
     */
    public void injectBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}