module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires org.apache.commons.io;


    opens de.hdmstuttgart.mi.bucketlist to javafx.fxml, javafx.graphics;
    exports de.hdmstuttgart.mi.bucketlist ;

    opens de.hdmstuttgart.mi.bucketlist.Model to javafx.fxml, com.fasterxml.jackson.databind;
    opens de.hdmstuttgart.mi.bucketlist.Persistance to com.fasterxml.jackson.databind;

    opens de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController to javafx.fxml, javafx.graphics, javafx.controls;
    exports de.hdmstuttgart.mi.bucketlist.View.Controller.SceneController to javafx.fxml, javafx.graphics, javafx.controls;

    opens de.hdmstuttgart.mi.bucketlist.View.Controller.CustomNodesController  to  javafx.fxml;
    opens de.hdmstuttgart.mi.bucketlist.View.Controller.PopUpController to javafx.fxml;

}