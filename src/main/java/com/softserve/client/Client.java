package com.softserve.client;

import com.softserve.server.states.ProxyServerCodes;
import com.softserve.util.StreamUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class Client {

    private static final String CRCN = "\r\n";

    private String marker;
    private int port = 9090;

    public Client(String marker) {
        this.marker = marker;
    }

    public Client(String marker, int port) {
        this.marker = marker;
        this.port = port;
    }

    public String getMarker() {
        return marker;
    }

    public void sentMessage() {
        try {
            Socket server = new Socket("localhost", port);
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();

            try {
                outputStream.write(marker.getBytes());
                outputStream.flush();
//                server.shutdownOutput();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(new Scanner(server.getInputStream()).nextLine());

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String header = "GET / HTTP/1.1 " +
                "Accept: text/html, application/xhtml+xml, */* " +
                "Accept-Language: uk-UA " +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko " +
                "Accept-Encoding: gzip, deflate " +
                "Host: localhost:9000 " +
                "Connection: Keep-Alive";

        new Client(header).sentMessage();
    }

}