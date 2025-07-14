package ru.ifmo.CLI;

import ru.ifmo.console_old.VerifierCommand;

public interface Icommand {
    String execute(String command);
    String getName();
    VerifierCommand getVerifierCommand();
}
