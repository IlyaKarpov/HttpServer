package com.softserve.ssl;

import com.softserve.ssl.handler.Handler;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 9999;

    public static void main(String... args) {
        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {
            System.setProperty("javax.net.ssl.keyStore", "mySrvKeystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");

            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket =
                    (SSLServerSocket) sslserversocketfactory.createServerSocket(PORT);

            while (true) {
                SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
                executor.execute(new Handler(sslsocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}