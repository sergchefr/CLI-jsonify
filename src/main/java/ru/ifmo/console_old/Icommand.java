package ru.ifmo.console_old;

public interface Icommand {
    String execute(String command);
    String getName();
    VerifierCommand getVerifierCommand();
}
