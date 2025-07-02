package ru.ifmo;

import java.util.HashMap;
import java.util.LinkedList;

public class CommandManagerImpl implements IcommandManager {

    private LinkedList<String> answers;
    private HashMap<String, VerifierCommand> verifierCommandHashMap;
    private HashMap<String, Icommand> commands;


    public CommandManagerImpl() {
        answers=new LinkedList<>();

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
        var a = commands.get(comName);
        if(a==null) return null;
        return a.getVerifierCommand();
    }

    @Override
    public void addCommand(Icommand command) {
        commands.put(command.getName(),command);
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

}
