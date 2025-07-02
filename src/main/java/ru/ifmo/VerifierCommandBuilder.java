package ru.ifmo;

import java.util.ArrayList;

/**
 *Создает объекты, описывающие ограничения конкретного класса
 */
public class VerifierCommandBuilder {
    private String name="";
    private String description="";
    private ArrayList<Parameter> parameters=new ArrayList<>();
    private ArrayList<String> flags=new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addParamameter(Parameter parameter){
        if(parameter!=null) parameters.add(parameter);
    }

    public void addFlag(String flag){
        flags.add(flag);
    }

    public VerifierCommand build(){
        if(description.isEmpty()) description="нет описания для данной команды";
        //System.out.println(description);
        if(name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
        return new VerifierCommand(name,description,parameters.toArray(new Parameter[0]), flags.toArray(new String[0]));
    }

}
