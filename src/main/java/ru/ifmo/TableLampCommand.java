package ru.ifmo;

public class TableLampCommand implements Icommand{

    @Override
    public String execute(String command) {
        System.err.println("lamp on");
        return "выполнено";
    }

    @Override
    public String getName() {
        return "table_lamp";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return null;
    }
}
