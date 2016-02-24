package com.softserve.client;

import com.softserve.util.StreamUtil;

import java.io.*;
import java.net.Socket;

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
            Socket client = new Socket("localhost", port);

            PrintStream printStream = new PrintStream(client.getOutputStream());
            printStream.print(marker);
            printStream.flush();
            client.shutdownOutput();

//            if (StreamUtil.getStringFromInputStream(client.getInputStream()).contains("407")) System.out.println(true);

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
                "Connection: Keep-Alive";

        new Client(header).sentMessage();
    }

}