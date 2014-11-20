package com.swift;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestReader {
    static String read(InputStream inputStream) {

        List<Character> bytel = new ArrayList<Character>();
        StringBuilder sb = new StringBuilder();
        byte[] b;
        String message = null;

        try {
            while (inputStream.available() > 0) {
//                bytel.add((char) inputStream.read());
                sb.append((char) inputStream.read());
            }
//            b = new byte[bytel.size()];
//            message = new String(bytel.to, "UTF-8");
            message = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
