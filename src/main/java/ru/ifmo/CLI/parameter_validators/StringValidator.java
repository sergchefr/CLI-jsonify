package ru.ifmo.CLI.parameter_validators;

public class StringValidator implements Validator{
    @Override
    public boolean validate(String limitations, String param) {
        if(!limitations.contains(":")) return true;
        String arg = limitations.split(":")[1];
        switch (arg){
            case "no_space":
                return !param.contains(" ");
        }
        return false;
    }
}
