package de.hdmstuttgart.mi.bucketlist.Gui.Controller.PopUpController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyListController {

    private final ListManager listManager;
    private String eventlistName;

    public ModifyListController(ListManager listManager, String eventlistName){
        this.listManager = listManager;
        this.eventlistName = eventlistName;
    }

    @FXML
    private TextField editNameField;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    public void updateName(){
        String newName = this.editNameField.getText();
        this.listManager.updateEventlistName(this.eventlistName,newName);
        this.eventlistName = newName; //todo needed ?

        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    public void deleteList(){
        this.listManager.deleteEventlist(this.eventlistName);
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    public void closeWindow(){
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }
}
