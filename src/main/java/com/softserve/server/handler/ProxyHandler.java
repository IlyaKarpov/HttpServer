package com.softserve.server.handler;

import com.softserve.server.ProxyServer;
import com.softserve.server.states.ProxyServerCodes;
import com.softserve.util.StreamUtil;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by ikar on 05.02.2016.
 *
 */
public class ProxyHandler implements Runnable {

    private static final String CRCN = "\r\n";

    private Socket client;
    private InputStream streamFromClient;
    private OutputStream streamToClient;

    private Socket server;
    private InputStream streamFromServer;
    private OutputStream streamToServer;

    private final byte[] request = new byte[1024];
    private byte[] response = new byte[4096];

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
            if (checkClientRequest()) {
                write407Response();
            }
//            else {
//                readHeader();
//                writeResponse();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                server.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHeader() throws IOException {
        Thread thread = new Thread() {
            public void run() {
                int bytesRead;
                try {
                    while ((bytesRead = streamFromClient.read(request)) != -1) {
                        streamToServer.write(request, 0, bytesRead);
                        streamToServer.flush();
                    }
                    streamToServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void writeResponse() throws IOException {
        int bytesRead;
        while ((bytesRead = streamFromServer.read(response)) != -1) {
            streamToClient.write(response, 0, bytesRead);
            streamToClient.flush();
        }
        streamToClient.close();
    }

    public byte[] getRequest() {
        return request;
    }

    public byte[] getResponse() {
        return response;
    }

    private void write407Response() throws IOException {
        streamToClient.write(ProxyServerCodes.CODE407.getMessage().getBytes());
        streamToClient.flush();
    }

    private boolean checkClientRequest() {
        boolean isNeedProxyAuthorization = true;
        Scanner header = new Scanner(streamFromClient);
        while (header.hasNextLine()) {
            if (header.nextLine().contains("Proxy-Authorization")) isNeedProxyAuthorization = false;
        }
        return isNeedProxyAuthorization;
    }

}