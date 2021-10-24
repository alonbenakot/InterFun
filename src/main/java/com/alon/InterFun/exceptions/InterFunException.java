package com.alon.InterFun.exceptions;

// note
public class InterFunException extends Exception {

    public InterFunException(String message) {
        super(message);
    }

    public InterFunException(String message, Throwable cause) {
        super(message, cause);
    }
}
