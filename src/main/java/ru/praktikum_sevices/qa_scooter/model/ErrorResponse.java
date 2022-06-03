package ru.praktikum_sevices.qa_scooter.model;

public class ErrorResponse {
    int code;
    String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorCreateCourierResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
