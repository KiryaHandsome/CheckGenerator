package org.testproj.Exceptions;

public class DiscountCardAlreadyPresentedException extends Exception {
    public DiscountCardAlreadyPresentedException(String message) {
        super(message);
    }
}
