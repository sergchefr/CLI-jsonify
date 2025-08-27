package ru.ifmo.CLI;

import ru.ifmo.CLI.input.ComLine;
import ru.ifmo.CLI.parameter_validators.FloatValidator;
import ru.ifmo.CLI.parameter_validators.IntValidator;
import ru.ifmo.CLI.parameter_validators.StringValidator;
import ru.ifmo.CLI.parameter_validators.ValidationException;
import ru.ifmo.CLI.util.Command;
import ru.ifmo.CLI.util.CommandVerifier;
import ru.ifmo.CLI.util.JsonReader;
import ru.ifmo.CLI.util.ParameterVerifier;

import java.io.IOException;
import java.util.HashMap;

public class CLIclient {
    private String source;
    private JsonReader reader;
    private ComLine comLine;
    private HashMap<String, CommandVerifier> coms;
    private Consumer consumer;


//v1
    public CLIclient(String configSource, ComLine comLine, Consumer consumer) throws IOException {
        this.comLine = comLine;

        this.consumer = consumer;

        this.source = source;
        reader = new JsonReader(source);
        var commands = reader.getCommands();
        coms = new HashMap<>();
        for (CommandVerifier command : commands) {
            coms.put(command.getName(), command);
        }

    }
//v2
    public CLIclient(String configSource, Consumer consumer) throws IOException {
        this.consumer = consumer;

        this.source = configSource;
        reader = new JsonReader(source);
        var commands = reader.getCommands();
        coms = new HashMap<>();
        for (CommandVerifier command : commands) {
            coms.put(command.getName(), command);
        }
    }

    public CLIclient(ComLine comLine, Consumer consumer){
        this.comLine = comLine;
        this.consumer = consumer;
        coms = new HashMap<>();
    }

    public CLIclient(Consumer consumer) {
        this.consumer = consumer;
        coms = new HashMap<>();
    }

    public void start(){
        if(comLine==null) throw new RuntimeException("no comline");
        Thread thread = new Thread(()-> {
            try {
                while (true) {
                    String input = comLine.read(">>>");
                    CommandVerifier verifier = coms.get(input.trim());
                    if(verifier==null) comLine.println("такой команды не существует");
                    else {
                        consumer.consume(Command.interactiveBuilder(verifier, comLine).toJson());
                    }
                }
            } catch (IOException e) {
                System.err.println("comline exception. Thread restarted");
                start();
            }
        });
        thread.start();
    }

    public Command.CommandBuilder getCommandBuilder(String comname){
        System.out.println(coms);
        var v1 = coms.get(comname);
        if(v1==null) throw new NullPointerException();
        return Command.builder(v1);
    }

    public void addCommandVerifier(CommandVerifier verifier){
        coms.put(verifier.getName(),verifier);
    }
}
