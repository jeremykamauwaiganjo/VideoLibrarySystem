package com.example.videolibrarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClientApp extends Application {

    // ── SET SERVER IP HERE BEFORE RUNNING ──
    private static final String SERVER_IP = "192.168.56.1";

    @Override
    public void start(Stage stage) {
        try {
            // Step 1: Connect to RMI server
            System.out.println("Connecting to server at " + SERVER_IP + "...");
            Registry registry = LocateRegistry.getRegistry(SERVER_IP, 1099);
            LibraryService service =
                    (LibraryService) registry.lookup("VideoLibraryService");

            System.out.println("✅ RMI Connected!");

            System.out.println("✅ Database connected!");

            // Step 3: Open Customer GUI directly
            stage.setScene(new Scene(
                    new RentalsApp().getPane(), 700, 500));
            stage.setTitle("Customer Panel - Video Library System");
            stage.show();

        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}