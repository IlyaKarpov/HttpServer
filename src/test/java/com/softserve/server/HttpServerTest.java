//package com.softserve.server;
//
//import com.softserve.client.Client;
//import com.softserve.server.handler.HttpHandler;
//import com.softserve.util.StreamUtil;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import static org.junit.Assert.*;
//
///**
// * Created by ikar on 08.02.2016.
// *
// */
//public class HttpServerTest {
//
//    private static final String CRCN = "\r\n";
//    private static final int PORT = 8080;
//    private ServerSocket serverSocket;
//    private Client client;
//
//    @Before
//    public void setUp() {
//        try {
//            serverSocket = new ServerSocket(PORT);
//            client = new Client("0");
//            client.sentMessage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @After
//    public void tearDown() {
//        try {
//            serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testClientConnect() {
//
//        try {
//            serverSocket.accept();
//        } catch (IOException e) {
//            e.printStackTrace();
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void testClientSendHeader() {
//
//        try {
//            Socket testSocket = serverSocket.accept();
//            String stringWithoutSpaces = StreamUtil.getStringFromInputStream(testSocket.getInputStream()).substring(2);
//            assertEquals(String.valueOf(client.getMarker()), stringWithoutSpaces);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testServerResponse() {
//
//        try {
//            String message = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//            String response = "HTTP/1.1 200 OK" + CRCN +
//                    "Content-Type: text/html" + CRCN +
//                    "Content-Length: " + message.length() + CRCN +
//                    "Connection: close" + CRCN + CRCN;
//            String expectedResponse = response + message;
//
//            Socket testSocket = serverSocket.accept();
//            HttpHandler httpHandler = new HttpHandler(testSocket);
////            String actualResponse = httpHandler.writeResponse();
//            String actualResponse = "";
//
//            assertEquals(expectedResponse, actualResponse);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}