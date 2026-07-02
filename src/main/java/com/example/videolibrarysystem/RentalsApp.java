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

public class RentalsApp extends Application {

    private ComboBox<String> customerComboBox = new ComboBox<>();
    private ComboBox<String> genreComboBox = new ComboBox<>();
    private ComboBox<String> moviesComboBox = new ComboBox<>();
    private ComboBox<String> borrowedComboBox = new ComboBox<>();
    private ComboBox<String> returnedComboBox = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Customer:");
        Text text2 = new Text("Genre:");
        Text text3 = new Text("Movies:");
        Text text4 = new Text("Borrowed:");
        Text text5 = new Text("Returned:");

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

        // ── DATABASE: Load customers and genres when app opens ──
        loadCustomers();
        loadGenres();

        // ── When genre changes load its movies ──
        genreComboBox.setOnAction(e -> {
            String selectedGenre = genreComboBox.getValue();
            if (selectedGenre != null) {
                loadMoviesByGenre(selectedGenre);
            }
        });

        // ── When customer changes load their borrowed and returned ──
        customerComboBox.setOnAction(e -> {
            String selectedCustomer = customerComboBox.getValue();
            if (selectedCustomer != null) {
                loadBorrowed(selectedCustomer);
                loadReturned(selectedCustomer);
            }
        });

        // ── Save Rental button ──
        saveButton.setOnAction(e -> {
            String customer = customerComboBox.getValue();
            String movie = moviesComboBox.getValue();
            if (customer != null && movie != null) {
                saveRental(customer, movie);
            } else {
                System.out.println("Please select a customer and a movie!");
            }
        });

        // ── Return Movie button ──
        returnButton.setOnAction(e -> {
            String customer = customerComboBox.getValue();
            String movie = borrowedComboBox.getValue();
            if (customer != null && movie != null) {
                returnMovie(customer, movie);
            } else {
                System.out.println("Please select a customer and a borrowed movie!");
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Rentals");
        stage.setScene(scene);
        stage.show();
    }

    // ── LOAD customers ──
    private void loadCustomers() {
        String sql = "SELECT fullname FROM clients WHERE isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            customerComboBox.getItems().clear();
            while (rs.next()) {
                customerComboBox.getItems().add(rs.getString("fullname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── LOAD genres ──
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

    // ── LOAD movies by genre ──
    private void loadMoviesByGenre(String genre) {
        String sql = "SELECT m.title FROM movies m " +
                "JOIN genres g ON m.genre_id = g.id " +
                "WHERE g.genre = ? AND m.isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, genre);
            ResultSet rs = pst.executeQuery();
            moviesComboBox.getItems().clear();
            while (rs.next()) {
                moviesComboBox.getItems().add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── SAVE rental ──
    private void saveRental(String customerName, String movieTitle) {
        String sql = "INSERT INTO rentals (client_id, movie_id, returned) " +
                "VALUES (" +
                "(SELECT id FROM clients WHERE fullname = ?), " +
                "(SELECT id FROM movies WHERE title = ?), " +
                "0)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, customerName);
            pst.setString(2, movieTitle);
            pst.executeUpdate();
            System.out.println("Rental saved!");
            loadBorrowed(customerName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── LOAD borrowed movies ──
    private void loadBorrowed(String customerName) {
        String sql = "SELECT m.title FROM rentals r " +
                "JOIN movies m ON r.movie_id = m.id " +
                "JOIN clients c ON r.client_id = c.id " +
                "WHERE c.fullname = ? AND r.returned = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, customerName);
            ResultSet rs = pst.executeQuery();
            borrowedComboBox.getItems().clear();
            while (rs.next()) {
                borrowedComboBox.getItems().add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── RETURN a movie ──
    private void returnMovie(String customerName, String movieTitle) {
        String sql = "UPDATE rentals SET returned = 1 " +
                "WHERE client_id = (SELECT id FROM clients WHERE fullname = ?) " +
                "AND movie_id = (SELECT id FROM movies WHERE title = ?) " +
                "AND returned = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, customerName);
            pst.setString(2, movieTitle);
            pst.executeUpdate();
            System.out.println("Movie returned!");
            loadBorrowed(customerName);
            loadReturned(customerName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── LOAD returned movies ──
    private void loadReturned(String customerName) {
        String sql = "SELECT m.title FROM rentals r " +
                "JOIN movies m ON r.movie_id = m.id " +
                "JOIN clients c ON r.client_id = c.id " +
                "WHERE c.fullname = ? AND r.returned = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, customerName);
            ResultSet rs = pst.executeQuery();
            returnedComboBox.getItems().clear();
            while (rs.next()) {
                returnedComboBox.getItems().add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
