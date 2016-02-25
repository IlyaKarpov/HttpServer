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

    private Socket client;
    private InputStream streamToClient;
    private OutputStream streamFromClient;

    public Client(String marker) {
        this.marker = marker;
        init();
    }

    public Client(String marker, int port) {
        this.marker = marker;
        this.port = port;
        init();
    }

    public String getMarker() {
        return marker;
    }

    private void init() {
        try {
            client = new Socket("localhost", port);
            streamToClient = client.getInputStream();
            streamFromClient = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentMessage() {
        try {
            streamFromClient.write(marker.getBytes());
            streamFromClient.flush();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String header = "GET / HTTP/1.1" + CRCN +
                "Accept: text/html, application/xhtml+xml, */*" + CRCN +
                "Accept-Language: uk-UA" + CRCN +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" + CRCN +
                "Accept-Encoding: gzip, deflate" + CRCN +
                "Host: localhost:9000" + CRCN +
                "Connection: Keep-Alive" + CRCN + CRCN;

        new Client(header).sentMessage();
    }

    private boolean checkServerResponse() {
        boolean isNeedProxyAuthorization = false;
        Scanner header = new Scanner(streamToClient);
        while (header.hasNextLine()) {
            System.out.println("here");
            if (header.nextLine().contains("407 Proxy Authentication Required")) isNeedProxyAuthorization = true;
        }
        return isNeedProxyAuthorization;
    }

}