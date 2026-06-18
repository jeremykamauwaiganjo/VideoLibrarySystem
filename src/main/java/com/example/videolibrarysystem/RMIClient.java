/*package com.example.videolibrarysystem;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("*Naila's IP*", 1099);

            LibraryService library = (LibraryService) registry.lookup("VideoLibraryService");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to RMI Server!");
            System.out.println("Commands: LIST, ADD [name], RENT [name], EXIT");

            while (true) {
                System.out.print("Enter Command > ");
                String input = scanner.nextLine().trim();

                if ("EXIT".equalsIgnoreCase(input)) {
                    break;
                } else if ("LIST".equalsIgnoreCase(input)) {
                    System.out.println("Movies: " + library.getMovies());
                } else if (input.toUpperCase().startsWith("ADD ")) {
                    String movie = input.substring(4);
                    System.out.println(library.addMovie(movie));
                } else if (input.toUpperCase().startsWith("RENT ")) {
                    String movie = input.substring(5);
                    System.out.println(library.rentMovie(movie));
                } else {
                    System.out.println("Unknown command.");
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}*/
