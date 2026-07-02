 package com.example.videolibrarysystem;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class RMIClient {
    public static void main(String[] args) {
        try {
            String serverIP = "192.168.56.1";

            System.out.println("Connecting to server at " + serverIP + "...");
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);

            LibraryService library = (LibraryService) registry.lookup("VideoLibraryService");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to RMI Server!");
            System.out.println("─────────────────────────────────");
            System.out.println("Commands:");
            System.out.println("  LIST         → show all movies");
            System.out.println("  ADD [name]   → add a movie");
            System.out.println("  RENT [name]  → rent a movie");
            System.out.println("  EXIT         → quit");
            System.out.println("─────────────────────────────────");

            while (true) {
                System.out.print("Enter Command > ");
                String input = scanner.nextLine().trim();

                if ("EXIT".equalsIgnoreCase(input)) {
                    System.out.println("Goodbye!");
                    break;

                } else if ("LIST".equalsIgnoreCase(input)) {
                    System.out.println("Movies: " + library.getMovies());

                } else if (input.toUpperCase().startsWith("ADD ")) {
                    String movie = input.substring(4).trim();
                    System.out.println(library.addMovie(movie));

                } else if (input.toUpperCase().startsWith("RENT ")) {
                    String movie = input.substring(5).trim();
                    System.out.println(library.rentMovie(movie));

                } else {
                    System.out.println(" Unknown command. Try: LIST | ADD [name] | RENT [name] | EXIT");
                }
            }
            scanner.close();

        } catch (Exception e) {
            System.err.println("Client error: " + e.toString());
            e.printStackTrace();
        }
    }
}
