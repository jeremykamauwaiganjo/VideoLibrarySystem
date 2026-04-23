module com.example.videolibrarysystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.videolibrarysystem to javafx.fxml;
    exports com.example.videolibrarysystem;
}