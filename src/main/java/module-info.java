module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires org.apache.commons.io;

    opens de.hdmstuttgart.mi.bucketlist to javafx.fxml;
    exports de.hdmstuttgart.mi.bucketlist;
    opens de.hdmstuttgart.mi.bucketlist.Model to javafx.fxml, com.fasterxml.jackson.databind;
    opens de.hdmstuttgart.mi.bucketlist.Persitance to com.fasterxml.jackson.databind;

}