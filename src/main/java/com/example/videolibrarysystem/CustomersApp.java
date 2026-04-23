package com.example.videolibrarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomersApp extends Application {

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Name:");
        Text text2 = new Text("Phone:");
        Text text3 = new Text("Email:");
        Text text4 = new Text("Registered:");

        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        ComboBox<String> registeredComboBox = new ComboBox<>();

        Button saveButton = new Button("Save Customer");
        Button removeButton = new Button("Remove Customer");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(phoneField, 1, 1);
        gridPane.add(text3, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(saveButton, 1, 3);
        gridPane.add(text4, 0, 4);
        gridPane.add(registeredComboBox, 1, 4);
        gridPane.add(removeButton, 1, 5);

        saveButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        removeButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        text3.setStyle("-fx-font: normal bold 20px 'serif'");
        text4.setStyle("-fx-font: normal bold 20px 'serif'");
        gridPane.setStyle("-fx-background-color: BEIGE;");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        removeButton.setMaxWidth(Double.MAX_VALUE);
        nameField.setMaxWidth(Double.MAX_VALUE);
        phoneField.setMaxWidth(Double.MAX_VALUE);
        emailField.setMaxWidth(Double.MAX_VALUE);
        registeredComboBox.setMaxWidth(Double.MAX_VALUE);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
