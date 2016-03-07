package com.softserve.server.handler;

import org.apache.log4j.Logger;

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
    private static final Logger logger = Logger.getLogger(HttpHandler.class);

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

            if (logger.isInfoEnabled()) {
                logger.info("Client has accepted to the HttpServer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            logger.warn("If some socket is turned off this method can throw an IOException");

            String header = readHeader();
            System.out.println(header);
            writeResponse(header);

        } catch (IOException e) {
            logger.error("Connection refused");
            e.printStackTrace();
        } finally {
            try {
                logger.warn("If connection has been refused this method can throw an IOException");
                client.close();
            } catch (IOException e) {
                logger.error("The Sockets of client and server haven't been closed");
                e.printStackTrace();
            }
        }
    }

    public String readHeader() throws IOException {
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

        if (logger.isDebugEnabled()) {
            logger.debug("HttpServer has read a client's request: \n" + builder.toString());
        }
        if (logger.isInfoEnabled()) {
            logger.info("HttpServer has read a client's request");
        }

        return builder.toString();
    }

    public synchronized String writeResponse(String header) throws IOException {

        String result;

        String message = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        String response = "HTTP/1.1 200 OK" + CRCN +
                "Content-Type: text/html" + CRCN +
                "Content-Length: " + message.length() + CRCN +
                "Connection: close" + CRCN + CRCN;

        result = response + message;

        outputStream.write(result.getBytes());
        outputStream.flush();

        if (logger.isDebugEnabled()) {
            logger.debug("HttpServer has sent a client's response: \n" + result);
        }
        if (logger.isInfoEnabled()) {
            logger.info("HttpServer has sent a client's respons");
        }

        return result;
    }

}