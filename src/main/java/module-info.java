module com.example.pharmacyapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pharmacyapp to javafx.fxml;
    exports com.example.pharmacyapp;
    exports com.example.pharmacyapp.controller;
    opens com.example.pharmacyapp.controller to javafx.fxml;
}