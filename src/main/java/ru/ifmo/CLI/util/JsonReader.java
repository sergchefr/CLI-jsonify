package ru.ifmo.CLI.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonReader {
    private String json;
    private ArrayList<CommandVerifier> commands;

    public JsonReader(String path) throws IOException {
        Path filepath = Paths.get(path);
        json = Files.readString(filepath);

        JsonMapper mapper = new JsonMapper();
        JsonNode rootnode = mapper.readTree(json);
        if(!rootnode.has("commands")) throw new JsonMappingException("ошибка преобразования json в объект");

        JsonNode comnode = rootnode.get("commands");
        if(comnode.isEmpty()) throw new JsonMappingException("json пуст");

        var iter = comnode.elements();
        ObjectMapper objectMapper = new ObjectMapper();
        commands = new ArrayList<>();
        while(iter.hasNext()){
            commands.add(objectMapper.treeToValue(iter.next(), CommandVerifier.class));
        }
    }

    public String getJson(){
        return json;
    }

    public ArrayList<CommandVerifier> getCommands() {
        return commands;
    }
}
