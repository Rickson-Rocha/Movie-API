package com.br.movies.exceptions;

public class FileExistsException extends RuntimeException {

    public FileExistsException(String message) {
        super(message);
    }
}
