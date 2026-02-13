package org.tracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    private static final String ALGORITHM = "SHA-256";

    // Генерация случайной соли
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16]; // 16 байт соли
        random.nextBytes(saltBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : saltBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    // Хэширование пароля с солью
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        String salt = generateSalt();
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

        // Комбинируем пароль и соль
        String passwordWithSalt = password + salt;
        byte[] hashBytes = digest.digest(passwordWithSalt.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString() + ":" + salt; // Возвращаем хэш и соль, разделенные двоеточием
    }

    // Проверка пароля с хэшированным значением
    public static boolean checkPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        String[] parts = storedHash.split(":");  // Разделяем хэш и соль
        String hash = parts[0];
        String salt = parts[1];

        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        String passwordWithSalt = password + salt; // Комбинируем введенный пароль и соль
        byte[] hashBytes = digest.digest(passwordWithSalt.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hash.equals(hexString.toString()); // Сравниваем хэш с сохраненным
    }
}

