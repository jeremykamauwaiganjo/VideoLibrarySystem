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

public class GenresApp extends Application {

    // Make comboBox a class variable so all methods can access it
    private ComboBox<String> comboBox = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");
        TextField textField1 = new TextField();
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(button1, 1, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(comboBox, 1, 2);
        gridPane.add(button2, 1, 3);

        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        gridPane.setStyle("-fx-background-color: BEIGE;");
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        comboBox.setMaxWidth(Double.MAX_VALUE);

        // ── DATABASE: load genres when app opens ──
        loadGenres();

        // ── DATABASE: Save button ──
        button1.setOnAction(e -> {
            String name = textField1.getText();
            if (!name.isEmpty()) {
                saveGenre(name);
                textField1.clear();
            } else {
                System.out.println("Please enter a genre name!");
            }
        });

        // ── DATABASE: Remove button ──
        button2.setOnAction(e -> {
            String selected = comboBox.getValue();
            if (selected != null) {
                removeGenre(selected);
            } else {
                System.out.println("Please select a genre to remove!");
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Genres");
        stage.setScene(scene);
        stage.show();
    }

    // ── SAVE genre to database ──
    private void saveGenre(String genreName) {
        String sql = "INSERT INTO genres (genre, isactive) VALUES (?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, genreName);
            pst.executeUpdate();
            System.out.println("Genre saved: " + genreName);
            loadGenres(); // refresh ComboBox

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── LOAD all active genres into ComboBox ──
    private void loadGenres() {
        String sql = "SELECT genre FROM genres WHERE isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            comboBox.getItems().clear();
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("genre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── REMOVE genre (set isactive = 0) ──
    private void removeGenre(String genreName) {
        String sql = "UPDATE genres SET isactive = 0 WHERE genre = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, genreName);
            pst.executeUpdate();
            System.out.println("Genre removed: " + genreName);
            loadGenres(); // refresh ComboBox

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}