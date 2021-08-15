package com.baeldung.common.web.exception;

public class ApiError {

    private int status;
    private String message;
    private String devMessage;

    public ApiError() {
    }

    public ApiError(int status, String message) {
        this(status, message, null);
    }

    public ApiError(int status, String message, String devMessage) {
        this.status = status;
        this.message = message;
        this.devMessage = devMessage;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }
}
