package com.move4mobile.lichtstad.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReader {

    public static String readString(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(stream))) {
            StringBuilder readString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                readString.append(line).append('\n');
            }
            return readString.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
