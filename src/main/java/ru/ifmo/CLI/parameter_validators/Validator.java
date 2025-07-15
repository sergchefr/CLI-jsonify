package ru.ifmo.CLI.parameter_validators;


public interface Validator {
    boolean validate(String limitations, String param);
}
