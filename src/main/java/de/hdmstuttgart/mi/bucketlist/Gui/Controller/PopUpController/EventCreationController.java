
package de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController;


import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * called by the eventlistController
 * represents a dialogue box to create new Events
 */
public class EventCreationController implements Initializable {

    //store the categories to display them in a combobox
   private final ObservableList<Category> categories = FXCollections.observableArrayList();
   private final Eventlist eventlist;

    public EventCreationController(Eventlist eventlist){
        this.eventlist = eventlist;
    }

    @FXML
    private TextField eventNameField;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @FXML
    private Label errorLabel;

    /**
     * opens a dialogue where the user can create a new event
     * will not close if the user sets no eventname oder category so its not possible to create events without a name
     * its also not possible to create two events with the same name
     */
    @FXML
    public void createEvent(){
        String eventName = this.eventNameField.getText();
        Category eventCategory = this.categoryComboBox.getValue();

        if(eventName.equals("") || eventCategory == null){
            this.errorLabel.setText("Please enter a name and select a category");
        }else{
            try {
                this.eventlist.addEvent(eventName,eventCategory);
                Stage stage = (Stage) this.createButton.getScene().getWindow();
                stage.close();
            } catch (ElementAlreadyExistsException e) {
                this.errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    void closeWindow() {
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    /**
     * fill the combobox with the categories
     * @param location -- not used
     * @param resources -- not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Category[] categoriesArray = Category.values();
        this.categories.addAll(categoriesArray);
        this.categoryComboBox.setItems(this.categories);
    }


}

