package com.softserve.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
                builder.append(currentLine);
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
        System.out.println(builder);
        return builder.toString();
    }

}