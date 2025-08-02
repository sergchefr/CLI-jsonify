package ru.ifmo.CLI.input;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EasyConsole implements ComLine {
    private Scanner console;

    public EasyConsole() {
        console = new Scanner(System.in);
    }

    @Override
    public void print(String arg) {
        System.out.print(arg);
    }

    @Override
    public void println(String arg) {
        System.out.println(arg);
    }

    @Override
    public String read() throws IOException {
        try {
            return console.nextLine();
        } catch (NoSuchElementException e) {
            throw new IOException();
        }
    }

    @Override
    public String read(String message) throws IOException {
        System.out.print(message);
        try {
            return console.nextLine();
        } catch (NoSuchElementException e) {
            throw new IOException();
        }
    }
}
