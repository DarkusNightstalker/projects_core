/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author CIUNAS
 */
public class AES {
    
    public static String encrypt(String text, String keyString) throws Exception {
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(text.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return new String(encryptedValue.getBytes());
    }

    public static String decrypt(String encryptedData, String keyString) throws Exception {
        if(encryptedData == null) return null;
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(new String(encryptedData));
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
       
        return decryptedValue;
    }

    private static Key generateKey(String keyValue) throws Exception {
        Key key = new SecretKeySpec(keyValue.getBytes(), "AES");
        return key;
    }

}
