package com.example.exception;

public class PlaywrightDownloaderException extends RuntimeException {

    private int httpStatus;
    private String errorMessage;

    public PlaywrightDownloaderException(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
    }
}
