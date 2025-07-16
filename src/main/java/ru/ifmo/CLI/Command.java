package ru.ifmo.CLI;

import ru.ifmo.CLI.input.ComLine;

import java.io.IOException;
import java.util.ArrayList;

public class Command {
    private ArrayList<Parameter> parameters;
    private String name;
    //класс, представляющий объект команды. его задача - создать команду с помощью билдера, а затем превратить ее в json.

    public Command(String name, ArrayList<Parameter> parameters){
        this.parameters = parameters;
        this.name = name;
    }
    public Command interactiveBuilder(String name, String filePath,ComLine inputDevice){
         return new CommandBuilder(name, filePath).interactiveBuild(inputDevice);
    }

    static class CommandBuilder{
        private ArrayList<ParameterVerifier> parameterVerifiers;
        private String name;
        private ArrayList<Parameter> parameters = new ArrayList<>();

        protected CommandBuilder(String comname, String filePath){
            name = comname;
            parameterVerifiers = new ArrayList<>();//TODO сделать чтение из xml!
        }
        private CommandBuilder(){

        }
        public Command interactiveBuild(ComLine inputDevice){
            ArrayList<Parameter> parameters = new ArrayList<>();
            for (ParameterVerifier parameterVerifier : parameterVerifiers) {
                try {
                    boolean a = true;
                    while (a) {
                        inputDevice.print(parameterVerifier.getName()+": "+ parameterVerifier.getDescription() + "\nlimitations: " + parameterVerifier.getLimitations() + "\n>>>");
                        String arg = inputDevice.read();
                        a=parameterVerifier.verify(arg);
                        if(!a) parameters.add(new Parameter(parameterVerifier.getName(), arg));
                    }
                } catch (IOException e) {
                    e.printStackTrace();//TODO сделать норм обработку ошибки
                }
            }
            return new Command(name,parameters);

        }

        public CommandBuilder name(String name){
            this.name = name;
            return this;
        }
        public CommandBuilder parameter(Parameter parameter){
            parameters.add(parameter);
            return this;
        }
        public Command build(){
            return new Command(name,parameters);
        }


    }
    public static CommandBuilder builder(){
        return new CommandBuilder();
    }

    public String toJson(){
        StringBuilder json = new StringBuilder();
        json.append("{\n\tname:\"").append(name).append("\",\n[");
        for (Parameter parameter : parameters) {
            json.append(parameter.toJson());
        }
        json.deleteCharAt(json.length()-1).append("]\n}");
        return json.toString();
    }
}
