package com.tushar.redislite.command;


import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.deserializer.DataDeserializer;
import com.tushar.redislite.serializer.DataSerializer;

public class CommandHandler {

    private final DataDeserializer deserializer;
    private final DataSerializer serializer;
    private final Memory memory;

    public CommandHandler(DataDeserializer deserializer, DataSerializer serializer, Memory memory) {
        this.deserializer = deserializer;
        this.serializer = serializer;
        this.memory = memory;
    }

    public Object handle(String inp) {
        char command = inp.charAt(0);
        if (command != '*') throw new IllegalArgumentException("Input is not an array");
        Object []deserialized = deserializer.deserializeObject(inp);
        return switch(deserialized[0].toString().toUpperCase()) {
            case "ECHO" -> serializer.serialize(
                    String.valueOf(new EchoCommand().builder(deserialized).handle(memory)), true);
            case "PING" -> serializer.serialize(
                    String.valueOf(new PingCommand().builder(deserialized).handle(memory)), false);
            case "SET" -> serializer.serialize(
                    String.valueOf(new SetCommand().builder(deserialized).handle(memory)), true);
            case "GET" -> serializer.serialize(
                    String.valueOf(new GetCommand().builder(deserialized).handle(memory)), true);
            case "LPUSH" -> serializer.serialize(
                    String.valueOf(new LpushCommand().builder(deserialized).handle(memory)), true);
            default -> throw new IllegalStateException("Unexpected value: " + deserialized[0].toString().toUpperCase());
        };
    }
}
