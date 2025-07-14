package ru.ifmo.console_old;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Класс, читающий XML и возвращающий объекты из них
 */
public class XMLreader implements Reader{
    private final String filename;
    private HashMap<String, VerifierCommand> verifierCommandHashMap;

    public XMLreader(String filename) {
        this.filename = filename;
        verifierCommandHashMap = new HashMap<>();
        try {
            parceConfig(filename);
        } catch (IOException e) {
            throw new RuntimeException("ошибка чтения файла");
        }
    }

    private String read(String filename) throws IOException {
        Path filepath;
        try {
            //filename = filename.replace("\\", "/");
            filepath = Paths.get(filename);
        }catch (InvalidPathException e){
            throw new IOException(e);
        }
        String str ="";
        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(filepath.toFile())))) {
            String nl;
            String version = bfr.readLine();
            while (true) {
                nl = bfr.readLine();
                if (nl != null) str = str+nl.strip();
                else break;
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        str=str.replace("<","#<").replace(">",">#").replace("##","#").substring(1);
        return str;
    }

    public void parceConfig(String filename)throws IOException{
        String[] coms;
        VerifierCommandBuilder commandBuilder = new VerifierCommandBuilder();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        try {
            String str1 = read(filename);
            coms = str1.split("#");
        }catch (IOException e){
            throw new IOException(e);
        }


        Stack<String> cond = new Stack<>();
        HashMap<String, String> constr = new HashMap<>();
        ArrayList<VerifierCommand> commands= new ArrayList<>();
        for (String s : coms) {
            if(s.contains("/")) {
                String a = cond.pop();
                if (a.equals("<command>")){
                    try {
                        commands.add(commandBuilder.build());
                        commandBuilder=new VerifierCommandBuilder();
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("inavalid name");
                    }
                }else if(a.equals("<parameter>")){
                    commandBuilder.addParamameter(parameterBuilder.getParameter());
                    parameterBuilder = new ParameterBuilder();
                }
            }
            else if(s.contains("<")) cond.add(s);
            //else constr.put(cond.toString(),s);
            else{
                switch (cond.toString()){
                    case "[<data>, <commands>, <command>, <parameters>, <parameter>, <name>]":
                        parameterBuilder.setName(s);
                        break;
                    case "[<data>, <commands>, <command>, <parameters>, <parameter>, <limitation>]":
                        parameterBuilder.setLimitations(s);
                        break;
                    case "[<data>, <commands>, <command>, <parameters>, <parameter>, <description>]":
                        parameterBuilder.setDescription(s);
                        break;
                    case "[<data>, <commands>, <command>, <name>]":
                        commandBuilder.setName(s);
                        break;
                    case "[<data>, <commands>, <command>, <description>]":
                        commandBuilder.setDescription(s);
                        break;
                }
            }
        }
        for (VerifierCommand command : commands) {
            verifierCommandHashMap.put(command.getName(), command);
        }
    }

    @Override
    public VerifierCommand getVerifierCommand(String name) {
        return verifierCommandHashMap.get(name);
    }

    @Override
    public ArrayList<VerifierCommand> getAll() {
        return new ArrayList<>(verifierCommandHashMap.values());
    }

    @Override
    public void reload() {
        verifierCommandHashMap =new HashMap<>();
        try {
            parceConfig(filename);
        } catch (IOException e) {
            System.err.println("ошибка чтения файла");
        }
    }
}
