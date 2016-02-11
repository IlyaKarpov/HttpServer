package com.softserve.server;

import com.softserve.client.Client;
import com.softserve.server.handler.ProxyHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by ikar on 08.02.2016.
 *
 */
public class ProxyServerTest {

    private static final int PROXY_SERVER_PORT = 9000;
    private static final int HTTP_SERVER_PORT = 8080;

    private ServerSocket proxyServerSocket;
    private ServerSocket httpServerSocket;

    private final static String request = "GET / HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.103 Safari/537.36\n" +
            "Accept-Encoding: gzip, deflate, sdch\n" +
            "Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";

    @Before
    public void setUp() {
        try {
            proxyServerSocket = new ServerSocket(PROXY_SERVER_PORT);
            httpServerSocket = new ServerSocket(HTTP_SERVER_PORT);
            new Client(request, PROXY_SERVER_PORT).sentMessage();
            new Socket("localhost", HTTP_SERVER_PORT);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            proxyServerSocket.close();
            httpServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {
        try {
            proxyServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSendRequest() {
        Socket clientSocket = null;
        try {
            clientSocket = proxyServerSocket.accept();
        } catch (IOException e) {
//            e.printStackTrace();
        }

        Socket serverSocket = null;
        try {
            serverSocket = httpServerSocket.accept();
        } catch (IOException e) {
//            e.printStackTrace();
        }

        ProxyHandler proxyHandler = new ProxyHandler(clientSocket, serverSocket);
        proxyHandler.run();

        String actual = new String(proxyHandler.getRequest()).trim().substring(1);

        assertEquals(request, actual);
    }

    @Test
    public void testGettingResponse() {
        Socket clientSocket = null;
        try {
            clientSocket = proxyServerSocket.accept();
        } catch (IOException e) {
//            e.printStackTrace();
        }

        Socket serverSocket = null;
        try {
            serverSocket = httpServerSocket.accept();
        } catch (IOException e) {
//            e.printStackTrace();
        }

        ProxyHandler proxyHandler = new ProxyHandler(clientSocket, serverSocket);
        proxyHandler.run();

        String actual = new String(proxyHandler.getResponse()).trim();
        assertEquals("", actual);
    }

}