package com.example.appointmentservice.dto;

public class ApiResponseError {
    private String error;
    private String message;
    private String timestamp;

    public ApiResponseError() {}

    public ApiResponseError(String error, String message, String timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
