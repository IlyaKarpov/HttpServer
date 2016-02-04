package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.handler.*;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class HttpServer {

    private final static int PORT = 8080;

    public static void main(String... args) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            ServerSocket server = new ServerSocket(PORT);

            while (true) {
                try {
                    Socket client = server.accept();
                    executor.execute(new Handler(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}