package ru.ifmo.CLI.parameter_validators;

import java.util.Locale;

public class StringValidator implements Validator{
    @Override
    public boolean validate(String limitations, String param) {
        limitations= limitations.toLowerCase(Locale.ROOT);
        if(!limitations.contains(":")) return true;
        String arg = limitations.split(":")[1];
        switch (arg){
            case "no_space":
                return !param.contains(" ");
            case "":
                return true;
        }
        return false;
    }
}
