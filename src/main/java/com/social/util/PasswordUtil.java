package com.social.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // bytes
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128; // bits

    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }
}
