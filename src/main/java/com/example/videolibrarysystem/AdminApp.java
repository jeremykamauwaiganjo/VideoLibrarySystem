package com.example.videolibrarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Admin launcher for the Video Library System.
 * Run this on the admin computer.
 * Allows managing genres, movies and customers.
 * @author YourName
 */
public class AdminApp extends Application {

    @Override
    public void start(Stage stage) {

        Text title = new Text("Video Library System");
        title.setStyle("-fx-font: normal bold 30px 'serif'; -fx-fill: darkslateblue;");

        Text subtitle = new Text("Admin Panel");
        subtitle.setStyle("-fx-font: normal bold 18px 'serif'; -fx-fill: teal;");

        Button enterButton = new Button("Enter Admin Panel");
        enterButton.setStyle("-fx-background-color: darkslateblue; " +
                "-fx-text-fill: white; -fx-font-size:14pt;");
        enterButton.setMinWidth(250);

        enterButton.setOnAction(e -> openAdminPanel(stage));

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));
        vbox.setStyle("-fx-background-color: BEIGE;");
        vbox.getChildren().addAll(title, subtitle, enterButton);

        stage.setScene(new Scene(vbox, 500, 300));
        stage.setTitle("Admin - Video Library System");
        stage.show();
    }

    /**
     * Opens the admin panel with all management tabs.
     * @param stage the main stage
     */
    private void openAdminPanel(Stage stage) {
        TabPane tabPane = new TabPane();

        // Genres Tab
        Tab genresTab = new Tab("Genres");
        genresTab.setClosable(false);
        genresTab.setContent(new GenresApp().getPane());

        // Movies Tab
        Tab moviesTab = new Tab("Movies");
        moviesTab.setClosable(false);
        moviesTab.setContent(new MoviesApp().getPane());

        // Customers Tab
        Tab customersTab = new Tab("Customers");
        customersTab.setClosable(false);
        customersTab.setContent(new CustomersApp().getPane());

        // Rentals Tab
        Tab rentalsTab = new Tab("Rentals");
        rentalsTab.setClosable(false);
        rentalsTab.setContent(new RentalsApp().getPane());

        tabPane.getTabs().addAll(
                genresTab,
                moviesTab,
                customersTab,
                rentalsTab
        );

        stage.setScene(new Scene(tabPane, 700, 500));
        stage.setTitle("Admin Panel - Video Library System");
    }

    public static void main(String[] args) {
        launch(args);
    }
}