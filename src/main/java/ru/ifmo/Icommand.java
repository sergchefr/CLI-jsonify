package ru.ifmo;

import ru.ifmo.VerifierCommand;

public interface Icommand {
    String execute(String command);
    String getName();
    VerifierCommand getVerifierCommand();
}
