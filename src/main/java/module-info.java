module com.example.pharmacyapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.pharmacyapp to javafx.fxml;
    exports com.example.pharmacyapp;
    exports com.example.pharmacyapp.controller;
    opens com.example.pharmacyapp.controller to javafx.fxml;
    exports com.example.pharmacyapp.Model;
    exports com.example.pharmacyapp.DAO;
    opens com.example.pharmacyapp.Model to javafx.base, javafx.fxml;

}