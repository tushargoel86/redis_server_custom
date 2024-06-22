package com.tushar.redislite.server;

import com.tushar.redislite.cache.Memory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisServer {

    private final int port;
    private final Memory memory;
    private final ExecutorService service = Executors.newFixedThreadPool(10);
    private final List<Socket> clients;
    private boolean shouldAcceptConnections = true;

    public RedisServer(int port) {
        this.port = port;
        this.memory = new Memory();
        this.clients = new ArrayList<>();
    }

    public void accept() throws IOException {
        registerShutdownHook();
		try(ServerSocket socket = new ServerSocket(port)) {
			Socket client;
            while (shouldAcceptConnections) {
                client = socket.accept();
                SocketHandler handler = new SocketHandler(client, memory);
                this.clients.add(client);
                service.execute(handler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                for(Socket socket : clients) {
                    socket.close();
                }
                this.service.shutdown();
                this.stop();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }));
    }


    public void stop() {
		shouldAcceptConnections = false;
    }
}
