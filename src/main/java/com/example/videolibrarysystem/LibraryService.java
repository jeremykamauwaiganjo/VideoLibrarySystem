package com.example.videolibrarysystem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI Interface for the Video Library Service.
 * Must be implemented by the server and used by the client.
 * @author YourName
 */
public interface LibraryService extends Remote {

    /**
     * Returns list of all available movies.
     * @return List of movie titles
     * @throws RemoteException if connection fails
     */
    List<String> getMovies() throws RemoteException;

    /**
     * Adds a new movie to the library.
     * @param movieName name of the movie to add
     * @return success or error message
     * @throws RemoteException if connection fails
     */
    String addMovie(String movieName) throws RemoteException;

    /**
     * Rents a movie from the library.
     * @param movieName name of the movie to rent
     * @return success or error message
     * @throws RemoteException if connection fails
     */
    String rentMovie(String movieName) throws RemoteException;
}