package com.tushar.redislite.server;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.command.CommandHandler;
import com.tushar.redislite.deserializer.DataDeserializer;
import com.tushar.redislite.serializer.DataSerializer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketHandler implements Runnable {

    private final Socket socket;
	private final Memory memory;

    public SocketHandler(Socket socket, Memory memory) {
        this.socket = socket;
        this.memory = memory;
    }

    @Override
    public void run() {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()))) {

            char []buffer = new char[1024];
            int nosOfBytesRead;

            while ((nosOfBytesRead =  reader.read(buffer)) > 0) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < nosOfBytesRead; i ++) {
                    builder.append(buffer[i]);
                }
                Object object = new CommandHandler(new DataDeserializer(), new DataSerializer(), memory)
                        .handle(builder.toString());

                System.out.println("output: " + object);
                writer.write(object.toString());
                writer.flush();
            }
            System.out.println("************");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
