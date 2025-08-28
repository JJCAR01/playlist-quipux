package com.quipux.playlist.infraestructure.in.exception;

public class PlayListNotFoundException extends RuntimeException {

    public PlayListNotFoundException(String message) {
        super(message);
    }

    public PlayListNotFoundException(String nombre, Throwable cause) {
        super("Playlist no encontrada: " + nombre, cause);
    }
}