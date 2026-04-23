package com.example.videolibrarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RentalsApp extends Application {

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Customer:");
        Text text2 = new Text("Genre:");
        Text text3 = new Text("Movies:");
        Text text4 = new Text("Borrowed:");
        Text text5 = new Text("Returned:");

        ComboBox<String> customerComboBox = new ComboBox<>();
        ComboBox<String> genreComboBox = new ComboBox<>();
        ComboBox<String> moviesComboBox = new ComboBox<>();
        ComboBox<String> borrowedComboBox = new ComboBox<>();
        ComboBox<String> returnedComboBox = new ComboBox<>();

        Button saveButton = new Button("Save Rental");
        Button returnButton = new Button("Return Movie");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 500);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(customerComboBox, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(genreComboBox, 1, 1);
        gridPane.add(text3, 0, 2);
        gridPane.add(moviesComboBox, 1, 2);
        gridPane.add(saveButton, 1, 3);
        gridPane.add(text4, 0, 4);
        gridPane.add(borrowedComboBox, 1, 4);
        gridPane.add(returnButton, 1, 5);
        gridPane.add(text5, 0, 6);
        gridPane.add(returnedComboBox, 1, 6);

        saveButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        returnButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        text3.setStyle("-fx-font: normal bold 20px 'serif'");
        text4.setStyle("-fx-font: normal bold 20px 'serif'");
        text5.setStyle("-fx-font: normal bold 20px 'serif'");
        gridPane.setStyle("-fx-background-color: BEIGE;");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        returnButton.setMaxWidth(Double.MAX_VALUE);
        customerComboBox.setMaxWidth(Double.MAX_VALUE);
        genreComboBox.setMaxWidth(Double.MAX_VALUE);
        moviesComboBox.setMaxWidth(Double.MAX_VALUE);
        borrowedComboBox.setMaxWidth(Double.MAX_VALUE);
        returnedComboBox.setMaxWidth(Double.MAX_VALUE);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Rentals");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
