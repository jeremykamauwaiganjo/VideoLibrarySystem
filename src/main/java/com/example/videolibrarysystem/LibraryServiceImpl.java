package com.example.videolibrarysystem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server side implementation of LibraryService.
 * Handles all database operations for the Video Library System.
 * @author YourName
 */
public class LibraryServiceImpl extends UnicastRemoteObject implements LibraryService {

    /**
     * Constructor for LibraryServiceImpl.
     * @throws RemoteException if RMI setup fails
     */
    public LibraryServiceImpl() throws RemoteException {
        super(); // ← fixed: no arguments needed
    }

    /**
     * Gets all active movies from the database.
     * @return list of movie titles
     * @throws RemoteException if connection fails
     */
    @Override
    public List<String> getMovies() throws RemoteException {
        List<String> movies = new ArrayList<>();
        String sql = "SELECT title FROM movies WHERE isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                movies.add(rs.getString("title"));
            }
            System.out.println("Client requested movie list: " + movies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Adds a new movie to the database.
     * @param movieName name of the movie to add
     * @return success or error message
     * @throws RemoteException if connection fails
     */
    @Override
    public String addMovie(String movieName) throws RemoteException {
        String sql = "INSERT INTO movies (title, isactive) VALUES (?, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, movieName);
            pst.executeUpdate();
            System.out.println("Added movie: " + movieName);
            return "SUCCESS: Added '" + movieName + "' to the library.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR: Could not add '" + movieName + "': " + e.getMessage();
        }
    }

    /**
     * Rents a movie by setting it as inactive in the database.
     * @param movieName name of the movie to rent
     * @return success or error message
     * @throws RemoteException if connection fails
     */
    @Override
    public String rentMovie(String movieName) throws RemoteException {
        String sql = "UPDATE movies SET isactive = 0 WHERE title = ? AND isactive = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, movieName);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Rented movie: " + movieName);
                return "SUCCESS: '" + movieName + "' has been checked out.";
            } else {
                return "ERROR: '" + movieName + "' is not available.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR: Could not rent '" + movieName + "': " + e.getMessage();
        }
    }
}