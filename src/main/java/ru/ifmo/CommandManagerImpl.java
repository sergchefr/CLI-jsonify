package ru.ifmo;

import ru.ifmo.localCommands.LocalCommandManager;
import ru.ifmo.Commands.serverCommands.ServerCommandManager;
import ru.ifmo.ConsoleIO;

import java.util.LinkedList;

public class CommandManagerImpl implements IcommandManager {

    private LinkedList<String> answers;
    private LocalCommandManager localCommandManager;
    private ServerCommandManager serverCommandManager;

    public CommandManagerImpl(LocalCommandManager localCommandManager, ServerCommandManager serverCommandManager) {
        this.localCommandManager = localCommandManager;
        this.serverCommandManager = serverCommandManager;
        answers=new LinkedList<>();
    }

    @Override
    public String getAnswers() {
        StringBuilder answer = new StringBuilder();
        answers.stream().forEach(x->answer.append(x+"\n"));
        answers.clear();
        return answer.toString().strip();
    }

    @Override
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    @Override
    public void execute(String command) {
        String answer = localCommandManager.execute(command);
        if(answer!=null) answers.add(answer);
        else{
            serverCommandManager.execute(command);
        }
    }

    @Override
    public VerifierCommand getVerifierCommand(String comName) {
        VerifierCommand vcom = localCommandManager.getVerifierCommand(comName);
        if(vcom!=null) return vcom;
        return serverCommandManager.getVerifierCommand(comName);
    }

    public String localhelp(String command){
        return localCommandManager.help(command);
    }
    public String serverhelp(String command){
        return serverCommandManager.help(command);
    }
    public String localhelp(){
        return localCommandManager.help();
    }
    public String serverhelp(){
        return serverCommandManager.help();
    }

}
