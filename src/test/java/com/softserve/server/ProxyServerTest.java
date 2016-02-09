package com.softserve.server;

import com.softserve.server.handler.ProxyHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ikar on 08.02.2016.
 *
 */
public class ProxyServerTest {

//    private static final int PROXY_SERVER_PORT = 9000;
//    private static final int HTTP_SERVER_PORT = 8080;
//    private ServerSocket proxyServerSocket;
//
//    @Before
//    public void setUp() {
//        try {
//            proxyServerSocket = new ServerSocket(PROXY_SERVER_PORT);
//            new Socket("localhost", PROXY_SERVER_PORT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void tearDown() {
//        try {
//            proxyServerSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testConnection() {
//        try {
//            proxyServerSocket.accept();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testSendHeaderToHttpServer() {
//        try {
//            Socket clientSocket = proxyServerSocket.accept();
//            Socket serverSocket = new Socket("localhost", HTTP_SERVER_PORT);
//            ProxyHandler proxyHandler = new ProxyHandler(clientSocket, serverSocket);
//            new Thread(proxyHandler).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}