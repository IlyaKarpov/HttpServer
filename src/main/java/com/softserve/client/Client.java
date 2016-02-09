package com.softserve.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class Client {

    private String marker;
    private int port = 8080;

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

            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF(String.valueOf(marker));
            dataOutputStream.flush();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Client(String.valueOf(i)).sentMessage();
        }
    }

}