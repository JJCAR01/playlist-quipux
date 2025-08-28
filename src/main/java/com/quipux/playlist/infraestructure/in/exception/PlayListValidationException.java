package com.quipux.playlist.infraestructure.in.exception;

public class PlayListValidationException extends IllegalArgumentException {

    public PlayListValidationException(String message) {
        super(message);
    }

    public PlayListValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
