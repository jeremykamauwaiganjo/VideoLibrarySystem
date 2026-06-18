module com.example.videolibrarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.videolibrarysystem to javafx.fxml;
    exports com.example.videolibrarysystem;
}
