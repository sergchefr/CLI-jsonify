package ru.ifmo.CLI.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CommandVerifier {
    private String name;
    private String description;
    private ArrayList<ParameterVerifier> parameters;

    @JsonCreator
    public CommandVerifier(@JsonProperty("name") String name,@JsonProperty("description") String description,@JsonProperty("parameters") ArrayList<ParameterVerifier> parameters) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<ParameterVerifier> getParameters() {
        return parameters;
    }
}
