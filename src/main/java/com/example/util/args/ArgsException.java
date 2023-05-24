package com.example.util.args;

public class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = null;
    private ArgsErrorCode errorCode = ArgsErrorCode.OK;

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(ArgsErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ArgsErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ArgsException(ArgsErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public ArgsErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public void setErrorCode(ArgsErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() {
        return switch (errorCode) {
            case OK -> "TILT: ここは実行されないはずです。";
            case UNEXPECTED_ARGUMENT -> String.format("引数 -%c は想定外です。", errorArgumentId);
            case MISSING_STRING -> String.format("次の引数の文字列パラメータが見つかりません -%c。", errorArgumentId);
            case INVALID_INTEGER ->
                    String.format("引数 -%c には整数が必要ですが、次の値が指定されました '%s'。", errorArgumentId, errorParameter);
            case MISSING_INTEGER -> String.format("次の引数の整数パラメータが見つかりません -%c。", errorArgumentId);
            case INVALID_DOUBLE ->
                    String.format("引数 -%c にはdoubleが必要ですが、次の値が指定されました '%s'", errorArgumentId, errorParameter);
            case MISSING_DOUBLE -> String.format("次の引数のdoubleパラメータが見つかりません -%c。", errorArgumentId);
            case INVALID_ARGUMENT_NAME -> String.format("'%c'は、不正な引数名です。", errorArgumentId);
            case INVALID_ARGUMENT_FORMAT -> String.format("'%s' は不正な引数フォーマットです。", errorParameter);
        };
    }

    public enum ArgsErrorCode {
        OK, INVALID_ARGUMENT_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME, MISSING_STRING,
        MISSING_INTEGER, INVALID_INTEGER, MISSING_DOUBLE, INVALID_DOUBLE
    }
}
