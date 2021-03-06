package org.voting.gateway.security;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class AESEncrypter {

    private Cipher cipher;
    private SecretKeySpec secretKey;

    public AESEncrypter() {

        String secret = "RODOKZAaZW0dt4G6";

        byte[] key = new byte[16];
        key = secret.getBytes(StandardCharsets.UTF_8);

        secretKey = new SecretKeySpec(key, "AES");

        try
        {
            cipher = Cipher.getInstance("AES");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	throw new RuntimeException("____ AESEncrypter nie dostano instancji ciphera ____");
        }

    }
    public void initEncryptMode() throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    public void initDecryptMode() throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
    }

    public byte[] encrypt(String input) throws BadPaddingException, IllegalBlockSizeException {
        byte[] data = input.getBytes(StandardCharsets.UTF_8);

       return cipher.doFinal(data);
    }

    public String decrypt(byte[] input) throws BadPaddingException, IllegalBlockSizeException {

        byte[] data = cipher.doFinal(input);
        return new String(data,StandardCharsets.UTF_8);
    }

}
