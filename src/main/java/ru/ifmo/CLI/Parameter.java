package ru.ifmo.CLI;

import ru.ifmo.CLI.parameter_validators.Validator;

import java.math.BigDecimal;
import java.util.HashMap;

public class Parameter {
    private String name;
    private String description;
    private String limitations;
    private static HashMap<String, Validator> validators;

    public Parameter(String name, String limitations, String description) {
        this.description = description;
        this.limitations = limitations;
        this.name = name;
    }
    public static ParameterBuilder builder(){
        return new ParameterBuilder();
    }
    static class ParameterBuilder {
        private String name = "";
        private String description = "";
        private String limitations;

        public ParameterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ParameterBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ParameterBuilder limitations(String limitations) {
            this.limitations = limitations;
            return this;
        }



        public Parameter build() {
            if (description.isEmpty()) description = "нет описания для данного параметра";
            if (name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
            return new Parameter(name, limitations, description);
        }

    }

    public static void addValidator(String header, Validator validator){
        validators.put(header, validator);
    }

    public boolean verify(String param){
        String valName = limitations.split(":")[0].strip();
        if(!validators.containsKey(valName)) return false;
        else{
            return validators.get(valName).validate(limitations,param);
        }
    }
}
