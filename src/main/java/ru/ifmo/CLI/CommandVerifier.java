package ru.ifmo.CLI;

import ru.ifmo.console_old.Parameter;

import java.util.HashMap;

public class CommandVerifier {
    private HashMap<String, Parameter> parameters = new HashMap<>();

    public CommandVerifier(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            this.parameters.put(parameter.getName(),parameter);
        }
    }

    public boolean verify(String json){
        for (String s : parameters.keySet()) {

        }
    }


    public Parameter getParameter(String name){
        return parameters.get(name);
    }
}
