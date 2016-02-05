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

//        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            ServerSocket proxy = new ServerSocket(PROXY_PORT);

            while (true) {
                Socket client = proxy.accept();
                Socket server = new Socket("localhost", SERVER_PORT);
                new Thread(new ProxyHandler(client, server)).start();
//                executor.execute(new ProxyHandler(client, server));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//
//            System.out.println("Starting proxy for localhost" + ":" + SERVER_PORT
//                    + " on port " + PROXY_PORT);
//
//            runServer("localhost", SERVER_PORT, PROXY_PORT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void runServer(String host, int remoteport, int localport)
//            throws IOException {
//
//        ServerSocket ss = new ServerSocket(localport);
//
//        final byte[] request = new byte[1024];
//        byte[] reply = new byte[4096];
//
//        while (true) {
//            Socket client = null, server = null;
//            try {
//
//                client = ss.accept();
//
//                final InputStream streamFromClient = client.getInputStream();
//                final OutputStream streamToClient = client.getOutputStream();
//
//                try {
//                    server = new Socket(host, remoteport);
//                } catch (IOException e) {
//                    PrintWriter out = new PrintWriter(streamToClient);
//                    out.print("Proxy server cannot connect to " + host + ":"
//                            + remoteport + ":\n" + e + "\n");
//                    out.flush();
//                    client.close();
//                    continue;
//                }
//
//                final InputStream streamFromServer = server.getInputStream();
//                final OutputStream streamToServer = server.getOutputStream();
//
//                Thread t = new Thread() {
//                    public void run() {
//                        int bytesRead;
//                        try {
//                            while ((bytesRead = streamFromClient.read(request)) != -1) {
//                                streamToServer.write(request, 0, bytesRead);
//                                streamToServer.flush();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            streamToServer.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                t.start();
//
//                int bytesRead;
//                try {
//                    while ((bytesRead = streamFromServer.read(reply)) != -1) {
//                        streamToClient.write(reply, 0, bytesRead);
//                        streamToClient.flush();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                streamToClient.close();
//            } catch (IOException e) {
//                System.err.println(e);
//            } finally {
//                try {
//                    if (server != null)
//                        server.close();
//                    if (client != null)
//                        client.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
