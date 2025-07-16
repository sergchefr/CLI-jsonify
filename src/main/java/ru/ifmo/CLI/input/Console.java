package ru.ifmo.CLI.input;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements ComLine {
    private Scanner console;

    @Override
    public void print(String arg) {
        System.out.print(arg);
    }

    @Override
    public String read() throws IOException {
        try {
            return console.nextLine();
        } catch (NoSuchElementException e) {
            throw new IOException();
        }
    }
}
