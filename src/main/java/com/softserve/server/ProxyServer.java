package com.softserve.server;

import com.softserve.server.handler.ProxyHandler;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ikar on 04.02.2016.
 *
 */

public class ProxyServer {

    private final static int PROXY_PORT = 9000;
    private final static int SERVER_PORT = 8080;

    public static void main(String... a) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            ServerSocket proxy = new ServerSocket(PROXY_PORT);

            while (true) {
                Socket client = proxy.accept();
                Socket server = new Socket("localhost", SERVER_PORT);
                System.out.println(client + " " + server);
                executor.execute(new ProxyHandler(client, server));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}