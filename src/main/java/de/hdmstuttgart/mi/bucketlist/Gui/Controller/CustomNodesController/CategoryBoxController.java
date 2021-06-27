package de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController;

import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

import static de.hdmstuttgart.mi.bucketlist.Model.Category.*;

public class CategoryBoxController {

    private final CategoryManager categoryManager;
    private String categoryName;
    private String eventAmount;
    private Category category;

    public CategoryBoxController(CategoryManager categoryManager) {
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
        String completedUrl = urlBase + category.toString() + postfix;
        System.out.println(completedUrl);

        URL url = this.getClass().getResource(completedUrl);
        System.out.println(url);
        Image image = new Image(url.toString());
        this.categoryImage.setImage(image);

    }

    public void setCategoryNameLabel(String category) {
        this.categoryNameLabel.setText(category);
    }

    public void setEventAmountLabel(String eventAmount2) {
        this.eventCat.setText(eventAmount2);
    }
}
