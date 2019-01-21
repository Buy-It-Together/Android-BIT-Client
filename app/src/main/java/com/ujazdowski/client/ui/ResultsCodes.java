package com.ujazdowski.client.ui;

public enum ResultsCodes {
    SUCCESS(1),
    FAILURE(2);

    private int statusCode;

    ResultsCodes(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
