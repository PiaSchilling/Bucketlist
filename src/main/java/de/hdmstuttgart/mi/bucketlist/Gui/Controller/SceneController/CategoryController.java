package de.hdmstuttgart.mi.bucketlist.Gui.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes.CategoryBox;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CategoryController implements Initializable, Listener {

    private final CategoryManager categoryManager;

    public CategoryController(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Label mycategoriesLabel;

    public void showCategories() {
        this.flowpane.getChildren().clear();

      //  AnchorPane pane;

       // for (int i = 0; i < this.categoryManager.getFilledCatgeoryLists().size(); i++) {
           // HashMap<Category, Categorylist> temp = this.categoryManager.getFilledCatgeoryLists();

        for (int i = 0; i < Category.values().length; i++) {
            CategoryBox box = new CategoryBox(categoryManager);
            box.getCategoryBoxController().setCategoryNameLabel(Category.values()[i].toString());
            box.getCategoryBoxController().setCategoryImageView(Category.values()[i]);
            this.flowpane.getChildren().add(box);
        }


            //box.getCategoryBoxController().setEventAmountLabel(temp.size());
           // pane = box;

           // this.flowpane.getChildren().add(pane);
        //}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        showCategories();

    }

    @Override
    public void update() {
        showCategories();
    }


}


