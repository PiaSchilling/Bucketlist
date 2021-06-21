package de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController;

import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * represents a dialogue box to complete an Event
 * reminder: its not possible to only inject the fitting event because of the listener (eventlist controller can only listen eventlist not event)
 */
public class CompleteEventController implements Initializable {

    private static final Logger log = LogManager.getLogger(CompleteEventController.class);

    private final Eventlist eventlist;
    private final String eventname;

    private String imageUrl;

    ObservableList<Integer> days = FXCollections.observableArrayList();
    ObservableList<Integer> months = FXCollections.observableArrayList();
    ObservableList<Integer> years = FXCollections.observableArrayList();

    public CompleteEventController(Eventlist eventlist, String eventname){
        this.eventlist = eventlist;
        this.eventname = eventname;
    }

    @FXML
    private ComboBox<Integer> dayComboBox;

    @FXML
    private ComboBox<Integer> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private TextArea textarea;

    @FXML
    private Button selectImageButton;

    @FXML
    private Button completeButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button backButton;

    /**
     * completes the event (sets date, description and image)
     * description and image are optional (a default image will be set in the eventCompletedController)
     */
    public void completeEvent(){

        //todo maybe make the date optional as well

        //description is optional
        String description = this.textarea.getText();

        if(this.yearComboBox.getSelectionModel().isEmpty() || this.dayComboBox.getSelectionModel().isEmpty() || this.monthComboBox.getSelectionModel().isEmpty()){
            this.errorLabel.setText("Please select a date");
        }else{
            System.out.println("name " + this.eventname);
            int days = this.dayComboBox.getValue();
            int months = this.monthComboBox.getValue();
            int years = this.yearComboBox.getValue();
            this.eventlist.completeEvent(this.eventname,this.imageUrl,description,days,months,years);



            Stage stage = (Stage) this.completeButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * opens the system own file chooser dialogue so the user can pick a image
     */
    @FXML
    void selectImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File image = fileChooser.showOpenDialog(stage);
        try{
            this.imageUrl = image.getPath();
        }catch (NullPointerException nullPointerException){
            this.imageUrl = null;
            log.debug(nullPointerException.getMessage() + ", no Image chosen, ImageUrl is set to null and a default Image will ne loaded");
        }
    }

    @FXML
    void closeWindow() {
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    /**
     * fills the comboboxes with the values for day, month, year
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i <= 31; i++) {
            this.days.add(i);
        }

        for (int i = 1; i <= 12; i++) {
            this.months.add(i);
        }

        for (int i = 2021; i <= 2100; i++) {
            this.years.add(i);
        }

        this.dayComboBox.setItems(this.days);
        this.monthComboBox.setItems(this.months);
        this.yearComboBox.setItems(this.years);
    }
}
