package com.softserve.server.handler;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class HttpHandler implements Runnable {

    private static final String CRCN = "\r\n";

    private Socket client;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpHandler(Socket client) {
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

    public void run() {
        try {

            readHeader();
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

    private String readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String currentLine;

        while (true) {
            currentLine = reader.readLine();
//            System.out.println(currentLine);
            if (currentLine == null || currentLine.isEmpty()) {
                break;
            }
            builder.append(currentLine).append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    private synchronized void writeResponse() throws IOException {
        String message = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//                + new DataInputStream(client.getInputStream()).readUTF();

//        System.out.println(new DataInputStream(client.getInputStream()));
//        System.out.println(new DataInputStream(client.getInputStream()).readUTF());

        String response = "HTTP/1.1 200 OK" + CRCN +
                "Content-Type: text/html" + CRCN +
                "Content-Length: " + message.length() + CRCN +
                "Connection: close" + CRCN + CRCN;

        String result = response + message;

        outputStream.write(result.getBytes());
        outputStream.flush();
    }

}