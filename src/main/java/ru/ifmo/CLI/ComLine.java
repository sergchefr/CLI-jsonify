package ru.ifmo.CLI;

import java.io.IOException;

public interface ComLine {
    void print(String arg)throws IOException;
    void read() throws IOException;
}
