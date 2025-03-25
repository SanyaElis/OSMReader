package ru.vsu.cs.eliseev.osmreader.exceptions;

public class DataImportException extends RuntimeException {
    public DataImportException(String message, Throwable cause) {
        super(message, cause);
    }
}