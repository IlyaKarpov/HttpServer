package com.softserve.client;

import com.softserve.util.StreamUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by ikar on 08.02.2016.
 *
 */
@RunWith(Parameterized.class)
public class ClientTest {

    private static final int PORT = 8080;

    private static ServerSocket serverSocket;

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[] {
                "0", "1", "2", "3", "4", "5"
        });
    }

    private String marker;

    public ClientTest(String marker) {
        this.marker = marker;
    }

    @BeforeClass
    public static void setUp() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMessage() {

        try {

            Client client = new Client(String.valueOf(marker));
            client.sentMessage();

            Socket testSocket = serverSocket.accept();
            String stringWithoutSpaces = StreamUtil.getStringFromInputStream(testSocket.getInputStream()).substring(2);
            assertEquals(String.valueOf(client.getMarker()), stringWithoutSpaces);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}