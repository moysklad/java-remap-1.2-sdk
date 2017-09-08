package com.lognex.api.exception;

public class ConverterException extends Exception {

    public ConverterException(String errorMessage) {
        super(errorMessage);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }
}
