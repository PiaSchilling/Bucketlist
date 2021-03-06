package de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the confirmationBox returns true/false resp. yes/no
 * Message (Text in the box) can be set so the box is usable in different use cases
 */
public class ConfirmationController implements Initializable {

    private boolean choice;
    private final String message;

    public ConfirmationController(String message){
        this.message = message;
    }

    @FXML
    private Label messageLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    public void setYes(){
        this.choice = true;
        Stage stage = (Stage) this.messageLabel.getScene().getWindow();
        stage.close();
    }

    public void setNo(){
        this.choice = false;
        Stage stage = (Stage) this.messageLabel.getScene().getWindow();
        stage.close();
    }

    public boolean getChoice(){
        return this.choice;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.messageLabel.setText(this.message);
    }
}
