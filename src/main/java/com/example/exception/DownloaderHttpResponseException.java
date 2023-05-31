package com.example.exception;

public class DownloaderHttpResponseException extends RuntimeException {

    private int httpStatus;
    private String errorMessage;

    public DownloaderHttpResponseException(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
    }
}
