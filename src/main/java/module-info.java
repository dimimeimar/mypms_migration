module com.mypms {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Database
    requires java.sql;
    requires com.mysql.cj;

    // Firebase
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.firestore;
    requires firebase.admin;

    // JSON Processing
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    // HTTP Client
    requires java.net.http;

    // Excel Export
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    // Logging
    requires org.slf4j;

    // ControlsFX
    requires org.controlsfx.controls;

    // Open packages to JavaFX
    opens com.mypms to javafx.fxml;
    opens com.mypms.customers.ui to javafx.fxml;
    opens com.mypms.customers.model to javafx.base;
    opens com.mypms.shipments.ui to javafx.fxml;
    opens com.mypms.shipments.model to javafx.base;
    opens com.mypms.antikatavoles.ui to javafx.fxml;
    opens com.mypms.antikatavoles.model to javafx.base;

    // Export packages
    exports com.mypms;
    exports com.mypms.customers.model;
    exports com.mypms.customers.ui;
    exports com.mypms.shipments.model;
    exports com.mypms.shipments.ui;
    exports com.mypms.antikatavoles.model;
    exports com.mypms.antikatavoles.ui;
}
