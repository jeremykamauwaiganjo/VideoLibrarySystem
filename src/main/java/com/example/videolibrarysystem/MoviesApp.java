package com.example.videolibrarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MoviesApp extends Application {

    // Move ComboBoxes to class level so all methods can access them
    private ComboBox<String> genreComboBox = new ComboBox<>();
    private ComboBox<String> registeredComboBox = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Genres:");
        Text text2 = new Text("Name:");
        Text text3 = new Text("Registered:");

        TextField nameField = new TextField();

        Button saveButton = new Button("Save Movie");
        Button removeButton = new Button("Remove Movie");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(genreComboBox, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(saveButton, 1, 2);
        gridPane.add(text3, 0, 3);
        gridPane.add(registeredComboBox, 1, 3);
        gridPane.add(removeButton, 1, 4);

        saveButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        removeButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        text3.setStyle("-fx-font: normal bold 20px 'serif'");
        gridPane.setStyle("-fx-background-color: BEIGE;");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        removeButton.setMaxWidth(Double.MAX_VALUE);
        genreComboBox.setMaxWidth(Double.MAX_VALUE);
        registeredComboBox.setMaxWidth(Double.MAX_VALUE);
        nameField.setMaxWidth(Double.MAX_VALUE);

        // ── DATABASE: Load genres when app opens ──
        loadGenres();

        // ── When genre changes, load movies for that genre ──
        genreComboBox.setOnAction(e -> {
            String selectedGenre = genreComboBox.getValue();
            if (selectedGenre != null) {
                loadMoviesByGenre(selectedGenre);
            }
        });

        // ── DATABASE: Save Movie button ──
        saveButton.setOnAction(e -> {
            String genre = genreComboBox.getValue();
            String name = nameField.getText();

            if (genre != null && !name.isEmpty()) {
                saveMovie(name, genre);
                nameField.clear();
            } else {
                System.out.println("Please select a genre and enter a movie name!");
            }
        });

        // ── DATABASE: Remove Movie button ──
        removeButton.setOnAction(e -> {
            String selected = registeredComboBox.getValue();
            if (selected != null) {
                removeMovie(selected);
            } else {
                System.out.println("Please select a movie to remove!");
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

    // ── LOAD all active genres into genreComboBox ──
    private void loadGenres() {
        String sql = "SELECT genre FROM genres WHERE isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            genreComboBox.getItems().clear();
            while (rs.next()) {
                genreComboBox.getItems().add(rs.getString("genre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── LOAD movies by selected genre into registeredComboBox ──
    private void loadMoviesByGenre(String genre) {
        String sql = "SELECT m.title FROM movies m " +
                "JOIN genres g ON m.genre_id = g.id " +
                "WHERE g.genre = ? AND m.isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, genre);
            ResultSet rs = pst.executeQuery();
            registeredComboBox.getItems().clear();
            while (rs.next()) {
                registeredComboBox.getItems().add(rs.getString("title"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── SAVE movie to database ──
    private void saveMovie(String title, String genre) {
        String sql = "INSERT INTO movies (genre_id, title, isactive) " +
                "VALUES ((SELECT id FROM genres WHERE genre = ?), ?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, genre);
            pst.setString(2, title);
            pst.executeUpdate();
            System.out.println("Movie saved: " + title);
            loadMoviesByGenre(genre); // refresh registered ComboBox

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── REMOVE movie (set isactive = 0) ──
    private void removeMovie(String movieTitle) {
        String sql = "UPDATE movies SET isactive = 0 WHERE title = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, movieTitle);
            pst.executeUpdate();
            System.out.println("Movie removed: " + movieTitle);
            String selectedGenre = genreComboBox.getValue();
            if (selectedGenre != null) {
                loadMoviesByGenre(selectedGenre); // refresh ComboBox
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}