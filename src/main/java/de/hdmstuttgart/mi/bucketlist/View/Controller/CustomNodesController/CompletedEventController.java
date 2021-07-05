package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Event;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;

public class CompletedEventController {

    private Eventlist eventlist;
    private String eventName;
    private Event event;

    private static final Logger log = LogManager.getLogger(CompletedEventController.class);

    public CompletedEventController(Eventlist eventlist){
        this.eventlist = eventlist;
    }

    public CompletedEventController(Event event){
        this.event = event;
    }

    @FXML
    private ImageView eventImageView;


    @FXML
    private Button deleteButton;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Text descriptionText;

    @FXML
    private Label completedDateLabel;

    /**
     * sets the image to the imageView
     * if the user selects no image the parameter is going to be null, in this case a default image is loaded
     * if there is a problem loading the image a error image will be loaded
     * @param imageUrl -- the url to the image which the user chose via the file chooser
     */
    public void setEventImageView(String imageUrl) {
        Image image;
        if(imageUrl == null){
            URL url = this.getClass().getResource("/images/defaultEventImage.png");
            image = new Image(url.toString());
            log.debug("Default Image set");
        }else {
            image = new Image(new File(imageUrl).toURI().toString());
            if(image.errorProperty().getValue()){
                URL url = this.getClass().getResource("/images/ErrorImage.jpg");
                image = new Image(url.toString());
                log.debug("Problems loading Image, Error Image will be loaded");
            }else{
                log.debug("Chosen Image set");
            }
        }
        this.eventImageView.setImage(image);
    }

    /**todo make active
     * deletes an event from the eventlist
     */
    private void deleteButtonAction(){
        this.eventlist.deleteEvent(this.eventName);
    }

    public void setEventNameLabel(String name) {
        this.eventName = name;
        this.eventNameLabel.setText(name.toUpperCase());
    }

    public void setEventDescrptionLabel(String descrption) {
        this.descriptionText.setText(descrption);
    }

    public void setEventDateLabel(String date) {
        this.completedDateLabel.setText("COMPLETED ON  " + date);
    }

    public void setEventCatLabel(String category) {
        this.categoryLabel.setText(category);
    }
}
