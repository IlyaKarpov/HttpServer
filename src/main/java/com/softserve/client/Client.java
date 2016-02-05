package com.softserve.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class Client {

    private int marker;

    public Client(int  marker) {
        this.marker = marker;
    }

    public void sentMessage() {
        try {
            Socket client = new Socket("localhost", 8080);

            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF(String.valueOf(marker));
            dataOutputStream.flush();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new Client(i).sentMessage();
        }
    }

}