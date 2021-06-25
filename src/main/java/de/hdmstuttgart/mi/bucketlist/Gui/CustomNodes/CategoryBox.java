package de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.Gui.Controller.CustomNodesController.CategoryBoxController;
import de.hdmstuttgart.mi.bucketlist.Model.Categorylist;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryBox extends AnchorPane {

    CategoryBoxController categoryBoxController;

    public CategoryBox(CategoryManager categoryManager) {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomNodes/CategoryBox.fxml"));
            categoryBoxController = new CategoryBoxController(categoryManager);
            loader.setController(categoryBoxController);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public CategoryBoxController getCategoryBoxController() {
        return this.categoryBoxController;
    }

}
