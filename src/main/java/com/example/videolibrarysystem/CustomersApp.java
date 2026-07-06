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

/**
 * Customers GUI for the Video Library System.
 * Allows adding and removing customers from the database.
 * @author YourName
 */
public class CustomersApp extends Application {

    // Class level so all methods can access it
    private ComboBox<String> registeredComboBox = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        // start() now just calls getPane()
        Scene scene = new Scene(getPane());
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns the GridPane for embedding in tabs from AdminApp.
     * @return GridPane with all customer controls
     */
    public GridPane getPane() {
        Text text1 = new Text("Name:");
        Text text2 = new Text("Phone:");
        Text text3 = new Text("Email:");
        Text text4 = new Text("Registered:");

        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();

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

        // Load customers when pane opens
        loadCustomers();

        // Save button
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                saveCustomer(name, phone, email);
                nameField.clear();
                phoneField.clear();
                emailField.clear();
            } else {
                System.out.println("Please fill in all fields!");
            }
        });

        // Remove button
        removeButton.setOnAction(e -> {
            String selected = registeredComboBox.getValue();
            if (selected != null) {
                removeCustomer(selected);
            } else {
                System.out.println("Please select a customer to remove!");
            }
        });

        return gridPane; // ← returns instead of showing stage
    }

    /**
     * Saves a new customer to the database.
     * @param name customer full name
     * @param phone customer phone number
     * @param email customer email address
     */
    private void saveCustomer(String name, String phone, String email) {
        String sql = "INSERT INTO clients (fullname, phone, email, isactive) VALUES (?, ?, ?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, email);
            pst.executeUpdate();
            System.out.println("Customer saved: " + name);
            loadCustomers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all active customers into the ComboBox.
     */
    private void loadCustomers() {
        String sql = "SELECT fullname FROM clients WHERE isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            registeredComboBox.getItems().clear();
            while (rs.next()) {
                registeredComboBox.getItems().add(rs.getString("fullname"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a customer by setting isactive to 0.
     * @param customerName name of the customer to remove
     */
    private void removeCustomer(String customerName) {
        String sql = "UPDATE clients SET isactive = 0 WHERE fullname = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, customerName);
            pst.executeUpdate();
            System.out.println("Customer removed: " + customerName);
            loadCustomers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}