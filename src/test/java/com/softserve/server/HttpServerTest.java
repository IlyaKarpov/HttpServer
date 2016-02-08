package com.softserve.server;

import com.softserve.client.Client;
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
public class HttpServerTest {

    private static final int PORT = 8080;
    private ServerSocket serverSocket;

    @Before
    public void setUp() {
        try {
            serverSocket = new ServerSocket(PORT);
            Client client = new Client(0);
            client.sentMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientConnect() {

        try {
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientSendHeader() {

        try {
            Socket testSocket = serverSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}