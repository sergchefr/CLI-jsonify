package ru.ifmo.CLI;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements ComLine{
    private Scanner console;

    @Override
    public void print(String arg) {
        System.out.print(arg);
    }

    @Override
    public void read() throws IOException {
        String command = "";
        try {
            command = console.nextLine();
        } catch (NoSuchElementException e) {
            throw new IOException();
        }
    }
}
