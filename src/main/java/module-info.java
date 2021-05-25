module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;

    opens de.hdmstuttgart.mi.bucketlist to javafx.fxml;
    exports de.hdmstuttgart.mi.bucketlist.Model;
    opens de.hdmstuttgart.mi.bucketlist.Model to javafx.fxml;
    opens de.hdmstuttgart.mi.bucketlist.Persitance to de.hdmstuttgart.mi.bucketlist.Model;
}