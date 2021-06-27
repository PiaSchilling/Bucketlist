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

    public void setCategoryImageView() {
        URL url = this.getClass().getResource("/images/Dark_Design/Categories_dark/SKILLS.png");
        Image image = new Image(url.toString());

        URL url2 = this.getClass().getResource("/images/Dark_Design/Categories_dark/TRAVEL.png");
        Image image2 = new Image(url2.toString());

        URL url3 = this.getClass().getResource("/images/Dark_Design/Categories_dark/CULTURE.png");
        Image image3 = new Image(url3.toString());

        URL url4 = this.getClass().getResource("/images/Dark_Design/Categories_dark/SHOPPING.png");
        Image image4 = new Image(url4.toString());

        URL url5 = this.getClass().getResource("/images/Dark_Design/Categories_dark/LIFEGOALS.png");
        Image image5 = new Image(url5.toString());

        URL url6 = this.getClass().getResource("/images/Dark_Design/Categories_dark/CULINARY.png");
        Image image6 = new Image(url6.toString());

        URL url7 = this.getClass().getResource("/images/Dark_Design/Categories_dark/EDUCATION.png");
        Image image7 = new Image(url7.toString());

        URL url8 = this.getClass().getResource("/images/Dark_Design/Categories_dark/SPORT.png");
        Image image8 = new Image(url8.toString());

        URL url9 = this.getClass().getResource("/images/Dark_Design/Categories_dark/HOBBY.png");
        Image image9 = new Image(url9.toString());

        URL url10 = this.getClass().getResource("/images/Dark_Design/Categories_dark/FAMILY.png");
        Image image10 = new Image(url10.toString());

        URL url11 = this.getClass().getResource("/images/Dark_Design/Categories_dark/RELATIONSHIP.png");
        Image image11 = new Image(url11.toString());

        URL url12 = this.getClass().getResource("/images/Dark_Design/Categories_dark/FRIENDS.png");
        Image image12 = new Image(url12.toString());

        switch (category) {
            case SKILLS : this.categoryImage.setImage(image); break;

            case TRAVEL :  this.categoryImage.setImage(image2); break;

            case CULTURE :  this.categoryImage.setImage(image3); break;

            case SHOPPING :  this.categoryImage.setImage(image4); break;

            case LIFEGOALS :  this.categoryImage.setImage(image5); break;

            case CULINARY :  this.categoryImage.setImage(image6); break;

            case EDUCATION :  this.categoryImage.setImage(image7); break;

            case SPORT :  this.categoryImage.setImage(image8); break;

            case HOBBY :  this.categoryImage.setImage(image9); break;

            case FAMILY :  this.categoryImage.setImage(image10); break;

            case RELATIONSHIP :  this.categoryImage.setImage(image11); break;

            case FRIENDS :  this.categoryImage.setImage(image12); break;
        }

    }

    public void setCategoryNameLabel(String category) {
        this.categoryNameLabel.setText(category);
    }

    public void setEventAmountLabel(String eventAmount2) {
        this.eventCat.setText(eventAmount2);
    }
}
