package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CategoryBoxController {

    private final CategoryManager categoryManager;
    private String categoryName;
    private String eventAmount;

    public CategoryBoxController(CategoryManager categoryManager) { this.categoryManager = categoryManager; }

    @FXML
    private Label categoryNameLabel;

    @FXML
    private Label eventCat;

    @FXML
    private ImageView categoryImage;

    public void setCategorySKILLSImageView() {
        String imageName = Category.SKILLS.toString();

        Image image = new Image("/resources/images/Dark_Design/Categories_dark/SKILLS.png");

        this.categoryImage.setImage(image);
    }

    public void setCategoryTRAVELImageView() {
        String imageName = Category.TRAVEL.toString();

        Image image = new Image("/resources/images/Dark_Design/Categories_dark/TRAVEL.png");

        this.categoryImage.setImage(image);
    }

    public void setCategoryCULTUREImageView() {
        String imageName = Category.CULTURE.toString();

        Image image = new Image("/resources/images/Dark_Design/Categories_dark/CULTURE.png");

        this.categoryImage.setImage(image);
    }

    public void setCategoryNameLabel(String category) {
        this.categoryNameLabel.setText(category);
    }

    public void setEventAmountLabel(String eventAmount2) {
        this.eventCat.setText(eventAmount2);
    }
}
