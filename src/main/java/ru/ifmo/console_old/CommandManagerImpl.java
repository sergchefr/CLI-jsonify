package ru.ifmo.console_old;

import java.util.HashMap;
import java.util.LinkedList;

public class CommandManagerImpl implements IcommandManager {

    private LinkedList<String> answers;
    private HashMap<String, VerifierCommand> verifierCommandHashMap;
    private HashMap<String, Icommand> commands;
    private Reader reader;
    private String configname;

    private static CommandManagerImpl instance;


    private CommandManagerImpl(String filename) {
        configname=filename;
        verifierCommandHashMap = new HashMap<>();
        commands=new HashMap<>();

        answers=new LinkedList<>();
        reader = new XMLreader(filename);
    }

    @Override
    public String getAnswers() {
        StringBuilder answer = new StringBuilder();
        answers.forEach(x->answer.append(x+"\n"));
        answers.clear();
        return answer.toString().strip();
    }

    @Override
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    @Override
    public void execute(String command) throws IllegalArgumentException {
        Icommand com = commands.get(command.split(" ")[0]);
        if(com==null) throw new IllegalArgumentException("такой команды нет");

        String answer = com.execute(command);
        if(answer!=null) answers.add(answer);
    }

    @Override
    public VerifierCommand getVerifierCommand(String comName) {
        return verifierCommandHashMap.get(comName);
    }
    public void addCommand(Icommand command) {
        commands.put(command.getName(),command);
        VerifierCommand verifierCommand = command.getVerifierCommand();
        if (verifierCommand==null) verifierCommand = reader.getVerifierCommand(command.getName());
        verifierCommandHashMap.put(verifierCommand.getName(), verifierCommand);
    }

    public String help(String comName){
        var a =  getVerifierCommand(comName);
        if(a==null) return "";
        StringBuilder resp = new StringBuilder(comName + ": " + a.getDescription() + "\nпараметры:\n");
        for (Parameter parameter : a.getParameters()) {
            resp.append(parameter.getDescription()).append("\n");
        }
        return resp.toString().strip();
    }

    public String help(){
        StringBuilder tmp= new StringBuilder();
        for (String s : commands.keySet()) {
            tmp.append(s).append(": ").append(getVerifierCommand(s).getDescription()).append("\n");
        }
        return tmp.toString().strip();
    }

    public static CommandManagerImpl getInstance(){
        if (instance==null) instance = new CommandManagerImpl("C:/Users/Сергей/IdeaProjects/AISmartHome/src/main/resources/Commands.xml");
        return instance;
    }



}
