package de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyEventController {

    private final Eventlist eventlist;
    private String eventName;

    public ModifyEventController(Eventlist eventlist, String eventName){
        this.eventlist = eventlist;
        this.eventName = eventName;
    }

    @FXML
    private TextField editNameField;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    void closeWindow() {
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteList() {
        this.eventlist.deleteEvent(this.eventName);
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateName() {
        String newName = this.editNameField.getText();
        if(newName == null || newName.isBlank()){
            this.errorLabel.setText("Name can not be empty");
        }else{
            try{
                this.eventlist.updateEventName(this.eventName,newName);
                this.eventName = newName; //todo needed ?
                Stage stage = (Stage) this.backButton.getScene().getWindow();
                stage.close();
            }catch (ElementAlreadyExistsException e){
                this.errorLabel.setText(e.getMessage());
            }
        }
    }
}
