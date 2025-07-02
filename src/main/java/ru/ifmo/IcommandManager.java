package ru.ifmo;

public interface IcommandManager {

    //void shutdown();
//    String help();
//    String help(String command);
    void execute(String command);
    VerifierCommand getVerifierCommand(String comName);
    void addAnswer(String answer);
    String getAnswers();




}
