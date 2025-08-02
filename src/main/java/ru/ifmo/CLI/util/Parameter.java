package ru.ifmo.CLI.util;


/**
 * Класс, содержащий информацию о параметре, готов к переводу в json;
 */
public class Parameter {
    private final String name;
    private final String value;

    public Parameter(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
