package com.example.videolibrarysystem;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create registry on port 1099 (no IP needed here)
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create the service implementation
            LibraryService service = new LibraryServiceImpl();

            // Register the service with a name clients will look up
            registry.rebind("VideoLibraryService", service);

            System.out.println("RMI Video Library Server is running...");
            System.out.println("Listening on port 1099");
            System.out.println("Your IP: 10.55.6.23");
            System.out.println("Waiting for clients to connect...");

        } catch (Exception e) {
            System.err.println("Server error: " + e.toString());
            e.printStackTrace();
        }
    }
}

