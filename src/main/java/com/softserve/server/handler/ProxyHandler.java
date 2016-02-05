package com.softserve.server.handler;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 05.02.2016.
 *
 */
public class ProxyHandler implements Runnable {

    private Socket client;
    private InputStream streamFromClient;
    private OutputStream streamToClient;

    private Socket server;
    private InputStream streamFromServer;
    private OutputStream streamToServer;

    final byte[] request = new byte[1024];
    byte[] response = new byte[4096];

    public ProxyHandler(Socket client, Socket server) {
        this.client = client;
        this.server = server;
        init();
    }

    private void init() {
        try {
            this.streamFromClient = client.getInputStream();
            this.streamToClient = client.getOutputStream();
            this.streamFromServer = server.getInputStream();
            this.streamToServer = server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            readHeader();
            writeResponse();
//            server.close();
//            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHeader() throws IOException {
        int bytesRead;
        while ((bytesRead = streamFromClient.read(request)) != -1) {
            streamToServer.write(request, 0, bytesRead);
            streamToServer.flush();
        }
//        streamToServer.close();
    }

    private void writeResponse() throws IOException {
        int bytesRead;
        while ((bytesRead = streamFromServer.read(response)) != -1) {
            streamToClient.write(response, 0, bytesRead);
            streamToClient.flush();
        }
//        streamToClient.close();
    }

}