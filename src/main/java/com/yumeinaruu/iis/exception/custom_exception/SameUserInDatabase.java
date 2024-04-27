package com.yumeinaruu.iis.exception.custom_exception;

public class SameUserInDatabase extends RuntimeException {
    String message;

    public SameUserInDatabase(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Registration problem. You already have this login: " + message;
    }
}
