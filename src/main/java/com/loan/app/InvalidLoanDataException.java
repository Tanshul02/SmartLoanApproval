package com.loan.app;

public class InvalidLoanDataException extends Exception {

    public InvalidLoanDataException(String message) {
        super(message);
    }
}
