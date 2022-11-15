module qamtwo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;

    opens qamtwo.c195 to javafx.fxml;
    opens helper to javafx.base;
    exports model;
    opens model to javafx.fxml;
    exports control;
    opens control to javafx.fxml;
    exports qamtwo.c195;
}