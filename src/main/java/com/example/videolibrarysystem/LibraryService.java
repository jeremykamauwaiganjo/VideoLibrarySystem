package com.example.videolibrarysystem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {

    List<String> getMovies() throws RemoteException;

    String addMovie(String movieName) throws RemoteException;

    String rentMovie(String movieName) throws RemoteException;
}