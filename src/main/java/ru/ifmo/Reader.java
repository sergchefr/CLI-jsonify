package ru.ifmo;

import java.util.ArrayList;

public interface Reader {
    VerifierCommand getVerifierCommand(String name);
    ArrayList<VerifierCommand> getAll();
    void reload();
}
