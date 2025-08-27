package ru.ifmo.CLI.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.CLI.parameter_validators.*;

import java.util.HashMap;

public class ParameterVerifier {
    private String name;
    private String description;
    private String limitations;
    {
        addValidator("string",new StringValidator());
        addValidator("int",new IntValidator());
        addValidator("float",new FloatValidator());
    }

    private static HashMap<String, Validator> validators = new HashMap<>();

    @JsonCreator
    public ParameterVerifier(@JsonProperty("name")String name,@JsonProperty("limitation") String limitations,@JsonProperty("description") String description) {
        this.description = description;
        this.limitations = limitations;
        this.name = name;
    }

    public static ParameterBuilder builder(){
        return new ParameterBuilder();
    }
    public static class ParameterBuilder {
        private String name = "";
        private String description = "";
        private String limitations;

        private ParameterBuilder() {
        }

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



        public ParameterVerifier build() {
            if (description.isEmpty()) description = "нет описания для данного параметра";
            if (name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
            return new ParameterVerifier(name, limitations, description);
        }

    }

    public static void addValidator(String header, Validator validator){
        validators.put(header, validator);
    }

    public boolean verify(String param) throws ValidationException {
        String valName = limitations.split(":")[0].strip();
        if(!validators.containsKey(valName)){

            throw new ValidationException("такого валидатора не существует: "+valName);
        }
        else{
            //System.out.println(validators.get(valName).validate(limitations,param));
            return validators.get(valName).validate(limitations,param);
        }
    }

    public String getDescription(){
        return description;
    }

    public String getLimitations() {
        return limitations;
    }

    public String getName() {
        return name;
    }
}
