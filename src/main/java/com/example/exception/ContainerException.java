package com.example.exception;

public class ContainerException extends RuntimeException{
    private ErrorCode errorCode = ErrorCode.OK;
    private String errorMessage;

    public ContainerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ContainerException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public enum ErrorCode {
        OK,
        SPIDER_FAILED_BUILD,
        SPIDER_FAILED_INSTANTIATION,
        SPIDER_NAME_NOT_FOUND,
        SETTINGS_MISSING,
        ITEM_PIPELINE_MISSING,
        ITEM_PIPELINE_FAILED_INSTANTIATION,
    }

}
