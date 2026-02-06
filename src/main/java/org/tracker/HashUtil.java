package org.tracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    // Method to hash the password using SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Apply SHA-256 to the password
        byte[] hashBytes = digest.digest(password.getBytes());

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        // Return the hashed password as a string
        return hexString.toString();
    }
}
