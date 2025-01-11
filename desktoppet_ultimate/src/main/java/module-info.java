module local.desktoppet {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.media;
    requires transitive java.xml.bind;
    requires transitive java.prefs;
    requires transitive java.sql;
    requires transitive mysql.connector.java;
    requires javafx.base; 

    opens local.desktoppet to javafx.fxml;
    opens local.desktoppet.view to javafx.fxml;
    opens local.desktoppet.model to javafx.fxml;
    exports local.desktoppet;
    exports local.desktoppet.model; 
}
