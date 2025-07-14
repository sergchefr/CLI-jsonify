package ru.ifmo.console_old;

public class ParameterBuilder {
    private String name="";
    private String description="";
    private String limitations;

    public ParameterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ParameterBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ParameterBuilder setLimitations(String limitations) {
        this.limitations = limitations;
        return this;
    }

    public Parameter getParameter(){
        if(description.isEmpty()) description="нет описания для данного параметра";
        if(name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
        return new Parameter(name,limitations,description);
    }
}
