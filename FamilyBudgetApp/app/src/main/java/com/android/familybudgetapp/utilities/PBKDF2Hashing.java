package com.android.familybudgetapp.utilities;
//based on https://github.com/wallaceespindola/password-hashing-security-java/blob/main/src%2Fmain%2Fjava%2Fcom%2Fwtech%2Fcore%2Fhashing%2FPBKDF2Hashing.java
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBKDF2Hashing
{
    public static String hashPassword(String password) throws Exception
    {
        // Securely generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hashPasswordWithSalt(password, salt);
    }

    private static String hashPasswordWithSalt(String password, byte[] salt) throws Exception
    {
        // Set realistic values for parameters
        int iterationCount = 100000; // How many iterations to use
        int keyLength = 256; // Derived key length

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash) + "," + Base64.getEncoder().encodeToString(salt);
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) throws Exception
    {
        String[] storedPasswordParts = storedHash.split(",");
        byte[] savedSalt = Base64.getDecoder().decode(storedPasswordParts[1]);
        String newHash = hashPasswordWithSalt(inputPassword, savedSalt);
        return newHash.equals(storedHash);
    }
}