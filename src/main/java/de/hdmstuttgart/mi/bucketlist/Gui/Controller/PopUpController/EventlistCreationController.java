package de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;



/**
 * called by the eventlistsController
 * represents a dialogue box to create new Eventlists
 */
public class EventlistCreationController implements Initializable {

    private static final Logger log = LogManager.getLogger(EventlistCreationController.class);

    ObservableList<Integer> days = FXCollections.observableArrayList();
    ObservableList<Integer> months = FXCollections.observableArrayList();
    ObservableList<Integer> years = FXCollections.observableArrayList();

    private final ListManager listManager;

    public EventlistCreationController(ListManager listManager){
        this.listManager = listManager;
    }

    @FXML
    private TextField listNameField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @FXML //will show an error text if the user tries to create a list without name
    private Label errorLabel;

    @FXML
    private ComboBox<Integer> dayBox;

    @FXML
    private ComboBox<Integer> monthBox;

    @FXML
    private ComboBox<Integer> yearBox;


    /** todo doesnt show error wehn date is selected and name already exists
     * creates a new Eventlist
     * if a date is selected, a list with expiry date is created, otherwise one without
     * this method will not allow to create eventlists without a name or two lists with the same name
     */
    public void createEventlist(){

        String eventlistname = this.listNameField.getText();

        if(eventlistname.equals("")){
            this.errorLabel.setText("Please enter a name");
        }else{
            try{
                if(this.yearBox.getSelectionModel().isEmpty() || this.monthBox.getSelectionModel().isEmpty() || this.yearBox.getSelectionModel().isEmpty()){
                    this.listManager.createEventlist(eventlistname);
                    log.debug("Eventlist without expiry Date is created");
                }else{
                    int dayInt = this.dayBox.getValue();
                    int monthInt = this.monthBox.getValue();
                    int yearInt = this.yearBox.getValue();
                    this.listManager.createEventlist(eventlistname,dayInt,monthInt,yearInt);
                    log.debug("GUI - Eventlist with expiry Date is created");
                }
                Stage stage = (Stage) this.createButton.getScene().getWindow();
                stage.close();
                log.debug("GUI - Event added to GUI");
            }catch (ElementAlreadyExistsException exception){
                this.errorLabel.setText(exception.getMessage());
            }
        }
    }

    @FXML
    void closeWindow() {
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    /**
     * fills the comboboxes (for date choosing) with values
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

        this.dayBox.setItems(this.days);
        this.monthBox.setItems(this.months);
        this.yearBox.setItems(this.years);
    }
}
