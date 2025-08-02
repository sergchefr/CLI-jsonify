package ru.ifmo.CLI.parameter_validators;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
