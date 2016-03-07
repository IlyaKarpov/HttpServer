package com.softserve.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.softserve.server.handler.*;
import org.apache.log4j.Logger;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class HttpServer {

    private final static int PORT = 9000;
    private static final Logger logger = Logger.getLogger(HttpServer.class);

    public static void main(String... args) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            logger.warn("If using port is being executed this method can throw an exception");

            if (logger.isInfoEnabled()) {
                logger.info("Starting HttpServer...");
            }

            ServerSocket server = new ServerSocket(PORT);

            while (true) {
                Socket client = server.accept();
                executor.execute(new HttpHandler(client));
            }
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Check this port: " + PORT);
            }
            logger.error("Perhaps using port is being executed");
            e.printStackTrace();
        }

    }

}