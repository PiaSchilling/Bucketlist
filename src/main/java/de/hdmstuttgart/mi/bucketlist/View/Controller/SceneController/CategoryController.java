package de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController;

import de.hdmstuttgart.mi.bucketlist.View.CustomNodes.CategoryBox;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.*;

/**
 * the controller for the scene which shows an overview of the categories
 */
public class CategoryController implements Initializable {
    private final CategoryManager categoryManager;
    //holds the filled categoryLists
    private final HashMap<Category, Categorylist> map;
    //holds the gui-boxes
    private final List<Node> boxContainer = new ArrayList<>();
    private BorderPane borderPane;

    //for the progressbar
    private final DoubleProperty counterProperty = new SimpleDoubleProperty();
    private double counter = 0;


    public CategoryController(CategoryManager categoryManager, BorderPane borderPane) {
        this.categoryManager = categoryManager;
        this.borderPane = borderPane;
        this.map = this.categoryManager.getFilledCatgeoryLists();
    }

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private Label mycategoriesLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label loadingLabel;

    /**
     * creates the boxes which represent the categories and add them to the boxContainer
     */
    public void createCategoryBoxes() {
        this.flowpane.getChildren().clear();
        Arrays.stream(Category.values()).forEach(category -> {
            Categorylist categorylist = this.map.get(category);
            CategoryBox box = new CategoryBox(categorylist, this.borderPane, this.categoryManager);
            box.getController().setCategoryNameLabel(category.toString());
            box.getController().setCategoryImageView(category);
            box.getController().setEventAmountLabel(map.get(category).getEvents().size());

            //the progress shows how many boxes are already created
            this.boxContainer.add(box);
            this.counter = (double) this.boxContainer.size() / 13;
            this.counterProperty.set(counter);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //progressbar shows the progress of the listLoading
        this.progressBar.progressProperty().bind(counterProperty);

        //start a service to load the lists in the background (takes more than 4 seconds) to keep the gui responsive while loading
        Service<Object> loaderService = new Service<>() {
            @Override
            protected Task<Object> createTask() {
                return new Task<>() {
                    @Override
                    protected Object call() throws Exception {
                        createCategoryBoxes();
                        return null;
                    }
                };
            }
        };

        loaderService.start();
        //entertain the user while the service loads the lists
        loaderService.setOnRunning(event -> {
            ImageView gifView = new ImageView();
            gifView.setFitHeight(500);
            gifView.setPreserveRatio(true);
            Image image = new Image(Objects.requireNonNull(this.getClass().getResource("/images/nemo.gif")).toExternalForm());
            gifView.setImage(image);
            this.flowpane.setAlignment(Pos.TOP_CENTER);
            this.flowpane.getChildren().addAll(gifView);
        });
        //unshow the progressbar und remove the gif
        loaderService.setOnSucceeded(event -> {
            this.flowpane.setAlignment(Pos.TOP_LEFT);
            this.progressBar.setVisible(false);
            this.loadingLabel.setVisible(false);
            this.flowpane.getChildren().clear();
            this.flowpane.getChildren().addAll(this.boxContainer);
        });
    }


    public void injectBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}



