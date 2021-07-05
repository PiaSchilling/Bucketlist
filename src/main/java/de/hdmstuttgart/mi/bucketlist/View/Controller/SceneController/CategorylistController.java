package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.EventinCatBox;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;

public class CategorylistController implements Initializable, Listener {

    private final CategoryManager categoryManager;
    //holds the filled categoryLists
    private final HashMap<Category, Categorylist> map;
    //holds the gui-boxes
    private final List<Node> boxContainer = new ArrayList<>();
    private BorderPane borderPane;

    public CategorylistController(CategoryManager categoryManager, BorderPane borderPane) {
        this.categoryManager = categoryManager;
        map = this.categoryManager.getFilledCatgeoryLists();
        this.borderPane = borderPane;
    }

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Label categoriesLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backButtonImage;

    public void showEventinCatBoxes() {
        this.flowpane.getChildren().clear();
        Arrays.stream(Category.values()).forEach(category -> {
            EventinCatBox box = new EventinCatBox(categoryManager, borderPane);
            box.getController().setCategoryLabel(category.toString());
            box.getController().setEventNameLabel(String.valueOf(map.get(category).getEvents()));

            //the progress shows how many boxes are already created
            this.boxContainer.add(box);

        });
    }


    @FXML
    void switchToCatScene(){
        CategoryController categoryController = new CategoryController(this.categoryManager);
        categoryController.injectBorderPane(this.borderPane);
        AnchorPane anchorPane = PaneLoader.loadAnchorPane(categoryController,"categories");
        this.borderPane.setCenter(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEventinCatBoxes();

    }

    @Override
    public void update() {
        showEventinCatBoxes();

    }
}
