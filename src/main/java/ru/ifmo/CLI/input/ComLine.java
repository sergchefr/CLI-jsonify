package ru.ifmo.CLI.input;

import java.io.IOException;

public interface ComLine {
    void print(String arg)throws IOException;
    String  read() throws IOException;
}
