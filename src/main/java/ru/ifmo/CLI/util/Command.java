package ru.ifmo.CLI.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.ifmo.CLI.input.ComLine;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс, представляющий объект команды. его задача - создать команду с помощью билдера, а затем превратить ее в json.
 */
public class Command {
    private ArrayList<Parameter> parameters;
    private String name;

    public Command(String name, ArrayList<Parameter> parameters){
        this.parameters = parameters;
        this.name = name;
    }
    public static Command interactiveBuilder(CommandVerifier verifier, ComLine inputDevice){
         return new CommandBuilder(verifier).interactiveBuild(inputDevice);
    }

    /**
     * Создание объекта команды, который затем конвертируется в json.
     */
    static public class CommandBuilder{
        private ArrayList<ParameterVerifier> parameterVerifiers;
        private String name;
        private ArrayList<Parameter> parameters = new ArrayList<>();

        /**
         * Вложенный класс, отвечающий за создание объектов, содержащих всю необходимую информацию о введенной команде
        @param verifier CommandVerifier, представляющий структуру команды, которую необходимо создать.
         */
        private CommandBuilder(CommandVerifier verifier){
            name = verifier.getName();
            parameterVerifiers = verifier.getParameters();
        }

        public CommandBuilder(){

        }

        /**
         * Метод, позвляющий в интерактивном режиме создавать объекты, используя определенное устройство ввода
         * @param inputDevice устройство ввода
         */
        public Command interactiveBuild(ComLine inputDevice){
            ArrayList<Parameter> parameters = new ArrayList<>();
            for (ParameterVerifier parameterVerifier : parameterVerifiers) {
                try {
                    boolean a = false;
                    while (!a) {
                        inputDevice.println(parameterVerifier.getLimitations());
                        String arg = inputDevice.read(parameterVerifier.getName()+"=");
                        a=parameterVerifier.verify(arg);
                        if(a) parameters.add(new Parameter(parameterVerifier.getName(), arg));
                    }
                } catch (IOException e) {
                    e.printStackTrace();//TODO сделать норм обработку ошибки
                }
            }
            return new Command(name,parameters);

        }

        public CommandBuilder forceName(String name){
            this.name = name;
            return this;
        }
        public CommandBuilder forceParameter(Parameter parameter){
            parameters.add(parameter);
            return this;
        }

        public boolean hasnext(){
            return parameterVerifiers.size()>parameters.size();
        }

        public String getNextLimitations(){
            return parameterVerifiers.get(parameters.size()).getLimitations();
        }

        public String getNextDescription(){
            return parameterVerifiers.get(parameters.size()).getDescription();
        }

        public String getNextName(){
            return parameterVerifiers.get(parameters.size()).getName();
        }

        //true if collection changed
        public boolean addParameter(String parameter){
            ParameterVerifier verifier= parameterVerifiers.get(parameters.size());
            if (verifier.verify(parameter))
                return parameters.add(new Parameter(verifier.getName(),parameter));
            return false;
        }

        public Command build(){
            return new Command(name,parameters);
        }
    }

    public static CommandBuilder builder(CommandVerifier verifier){
        return new CommandBuilder(verifier);
    }

    /**
     * Метод, возвращающий представление данной команды строкой, в виде json
     */
    public String toJson(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootnote = mapper.createObjectNode();
        rootnote.put("name",name);
        ArrayNode params = mapper.createArrayNode();
        for (Parameter parameter : parameters) {
            ObjectNode par = mapper.createObjectNode()
                    .put("name", parameter.getName())
                    .put("value", parameter.getValue());
            params.add(par);
        }
        rootnote.put("parameters", params);
        return rootnote.toPrettyString();
    }
}
