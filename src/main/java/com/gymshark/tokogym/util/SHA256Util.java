package com.gymshark.tokogym.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static String encrypt(String input) {
        try {
            // Membuat instance SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Mengubah input ke byte lalu di-hash
            byte[] hashBytes = md.digest(input.getBytes());

            // Mengonversi hasil hash ke format heksadesimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Format dua digit hex
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found!");
        }
    }
}

