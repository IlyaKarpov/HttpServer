package com.softserve.server.handler;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class Handler implements Runnable {

    private static final String CRCN = "\r\n";

    private Socket client;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Handler(Socket client) {
        this.client = client;
        init();
    }

    private void init() {
        try {
            this.inputStream = client.getInputStream();
            this.outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            writeResponse();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeResponse() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String message = dateFormat.format(Calendar.getInstance().getTime()) + " "
                + new DataInputStream(client.getInputStream()).readUTF();

        String response = "HTTP/1.1 200 OK" + CRCN +
                "Content-Type: text/html" + CRCN +
                "Content-Length: " + message.length() + CRCN +
                "Connection: close" + CRCN + CRCN;

        String result = response + message;

        outputStream.write(result.getBytes());
        outputStream.flush();
    }

}