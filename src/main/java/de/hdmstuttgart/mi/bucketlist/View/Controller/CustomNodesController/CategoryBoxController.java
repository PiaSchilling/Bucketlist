package de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.CategorylistController;
import de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController.PaneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryBoxController implements Initializable {

    private final Categorylist categorylist;
    private final CategoryManager categoryManager;
    private final BorderPane borderPane;

    public CategoryBoxController(Categorylist categorylist, BorderPane borderPane, CategoryManager categoryManager) {
        this.categorylist = categorylist;
        this.borderPane = borderPane;
        this.categoryManager = categoryManager;
    }

    @FXML
    private Label categoryNameLabel;

    @FXML
    private Label eventCat;

    @FXML
    private ImageView categoryImage;

    public void setCategoryImageView(Category category) {
        String urlBase = "/images/Dark_Design/Categories_dark/";
        String postfix = ".png";
        String completedUrl = urlBase + category.toString().toLowerCase() + postfix;
        URL url = this.getClass().getResource(completedUrl);
        Image image = new Image(url.toString());
        this.categoryImage.setImage(image);
    }

    public void setCategoryNameLabel(String category) {
        this.categoryNameLabel.setText(category);
    }

    public void setEventAmountLabel(int amount) {
        if(amount == 1){
            this.eventCat.setText(amount + " Event");
        }else{
            this.eventCat.setText(amount + " Events");
        }
    }

    /**
     * switches in the categoryListScene
     */
    @FXML
    void switchToCatlistScene() {
        CategorylistController categorylistController = new CategorylistController(this.categorylist, this.borderPane,this.categoryManager);
        AnchorPane anchorPane = PaneLoader.loadAnchorPane(categorylistController,"categorylist");
        this.borderPane.setCenter(anchorPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.categoryNameLabel.setText(this.categorylist.getListCategory().toString());
        this.eventCat.setText(this.categorylist.getEvents().size() + " Events");
    }
}
