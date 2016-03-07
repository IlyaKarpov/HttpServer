package com.softserve.server;

import com.softserve.server.handler.ProxyHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ikar on 04.02.2016.
 *
 */

public class ProxyServer {

    private final static int PROXY_PORT = 9090;
    private final static int SERVER_PORT = 9000;
    private final static Logger logger = Logger.getLogger(ProxyHandler.class);

    public static void main(String... a) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            logger.warn("If using port is being executed this method can throw an exception");

            if (logger.isInfoEnabled()) {
                logger.info("Starting ProxyServer...");
            }

            ServerSocket proxy = new ServerSocket(PROXY_PORT);

            while (true) {
                Socket client = proxy.accept();
                Socket server = new Socket("localhost", SERVER_PORT);
                executor.execute(new ProxyHandler(client, server));
            }
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Check these ports: " + PROXY_PORT + " " + SERVER_PORT);
            }
            logger.error("Perhaps using port is being executed");
            e.printStackTrace();
        }

    }
}