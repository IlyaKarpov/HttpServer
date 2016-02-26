package com.softserve.util;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 08.02.2016.
 *
 */
public class StreamUtil {
    public static String getStringFromInputStream(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String currentLine;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((currentLine = reader.readLine()) != null) {
                builder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public static String readMessageFromServer(Socket socket) throws IOException {
        byte[] buff = new byte[64 * 1024];
        int read = socket.getInputStream().read(buff);
        return new String(buff, 0, read);
    }

}