package com.softserve.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.softserve.server.handler.*;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class HttpServer {

    private final static int PORT = 9090;

    public static void main(String... args) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            ServerSocket server = new ServerSocket(PORT);

            while (true) {
                Socket client = server.accept();
                executor.execute(new HttpHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}