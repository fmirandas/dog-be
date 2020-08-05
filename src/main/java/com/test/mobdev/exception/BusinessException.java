package com.test.mobdev.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6955566026736004771L;

    public BusinessException(String message) {
        super(message);
    }

}
