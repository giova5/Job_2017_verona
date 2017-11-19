package com.emis.job2017.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jo5 on 17/10/17.
 */

public class Utils {

    private static String SHARED_SECRET = "SECIRIUSCIAMOSIAMODEGLIDEISISISI";

    public enum EventType {
        GET_EVENT_INFO,
        GET_EXHIBITORS_CATEGORIES,
        GET_EXHIBITORS_PADIGLIONI,
        GET_EXHIBITORS_INFO,
        USER_AUTHENTICATE,
        GET_ACCESS_TOKEN,
        GET_PROGRAM,
        NEWS,
        GET_USER_PROFILE_INFO;
    }

    public enum ExhibitorsTypeOfID{

    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(SHARED_SECRET.getBytes(), "AES/CBC/PKCS5Padding");
    }

    @Nullable
    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        Cipher cipher = null;
        byte[] newEncryptedBytes;

        try {
            /* Encrypt the message. */
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //Creating IV
            SecureRandom r = new SecureRandom();
            byte[] ivBytes = new byte[16];
            r.nextBytes(ivBytes);

            cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            byte[] encryptedBytes = cipher.doFinal(message.getBytes("UTF-8"));

            newEncryptedBytes = new byte[encryptedBytes.length + ivBytes.length];
//
            System.arraycopy(ivBytes, 0, newEncryptedBytes, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, newEncryptedBytes, ivBytes.length, encryptedBytes.length);
        }

        catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        return newEncryptedBytes;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
    {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist*1000; // output distance, in metri
    }

}
