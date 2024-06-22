package com.tushar.redislite;

import com.tushar.redislite.server.RedisServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RedisServer server = new RedisServer(6381);
        server.accept();
    }
}
