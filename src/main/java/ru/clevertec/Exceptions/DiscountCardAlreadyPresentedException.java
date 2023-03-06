package ru.clevertec.Exceptions;

public class DiscountCardAlreadyPresentedException extends Exception {
    public DiscountCardAlreadyPresentedException(String message) {
        super(message);
    }
}
