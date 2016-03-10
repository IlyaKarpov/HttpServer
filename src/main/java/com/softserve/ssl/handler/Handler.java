package com.softserve.ssl.handler;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ikar on 10.03.2016.
 *
 */
public class Handler implements Runnable {

    private static final String CRCN = "\r\n";
    private SSLSocket sslSocket;

    public Handler(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

//    public void run() {
//        try {
//            InputStream inputstream = sslSocket.getInputStream();
//            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
//            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
//
//            String string;
//            while ((string = bufferedreader.readLine()) != null) {
//                System.out.println(string);
//                System.out.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                sslSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void run() {
        try {
            readHeader();
            writeResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sslSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        String currentLine;

        while (true) {
            currentLine = reader.readLine();
            if (currentLine == null || currentLine.isEmpty()) {
                break;
            }
        }
    }

    private String writeResponse() throws IOException {
        String result;
        String message = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String response = "HTTP/1.1 200 OK" + CRCN +
                "Content-Type: text/html" + CRCN +
                "Content-Length: " + message.length() + CRCN +
                "Connection: close" + CRCN + CRCN;
        result = response + message;

        OutputStream outputStream = sslSocket.getOutputStream();
        outputStream.write(result.getBytes());
        outputStream.flush();

        return result;
    }
}