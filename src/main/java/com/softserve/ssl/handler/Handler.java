package com.softserve.ssl.handler;

import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * Created by ikar on 10.03.2016.
 *
 */
public class Handler implements Runnable {

    private SSLSocket sslSocket;

    public Handler(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    public void run() {
        try {
            InputStream inputstream = sslSocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String string;
            while ((string = bufferedreader.readLine()) != null) {
                System.out.println(string);
                System.out.flush();
            }
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
}