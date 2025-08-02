package ru.ifmo.CLI;

import ru.ifmo.CLI.input.ComLine;
import ru.ifmo.CLI.parameter_validators.FloatValidator;
import ru.ifmo.CLI.parameter_validators.IntValidator;
import ru.ifmo.CLI.parameter_validators.StringValidator;
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

    public CLIclient(String source, ComLine comLine, Consumer consumer) throws IOException {
        this.source = source;
        this.comLine = comLine;
        this.consumer = consumer;
        reader = new JsonReader(source);
        var commands = reader.getCommands();
        coms = new HashMap<>();
        for (CommandVerifier command : commands) {
            coms.put(command.getName(), command);
        }
        ParameterVerifier.addValidator("string",new StringValidator());
        ParameterVerifier.addValidator("int",new IntValidator());
        ParameterVerifier.addValidator("float",new FloatValidator());
    }

    public void start(){
        Thread thread = new Thread(()-> {
            try {
                while (true) {
                    String input = comLine.read(">>>");
                    CommandVerifier verifier = coms.get(input.trim());
                    if(verifier==null) comLine.println("такой команды не существует");
                    else {
                        consumer.consume(Command.builder(verifier).interactiveBuild(comLine).toJson());
                    }
                }
            } catch (IOException e) {
                System.err.println("comline exception. Thread restarted");
                start();
            }
        });
        thread.start();
    }
}
