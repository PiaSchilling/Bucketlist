package de.hdmstuttgart.mi.bucketlist.View.CustomNodes;

import de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController.CategoryBoxController;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryBox extends AnchorPane implements Box{

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

    public CategoryBoxController getController() {
        return this.categoryBoxController;
    }

}
