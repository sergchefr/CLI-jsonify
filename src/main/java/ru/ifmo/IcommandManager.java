package ru.ifmo;

public interface IcommandManager {

    void execute(String command);
    VerifierCommand getVerifierCommand(String comName);
    void addAnswer(String answer);
    String getAnswers();

    void addCommand(Icommand command);

    String help(String command);
    String help();




}
