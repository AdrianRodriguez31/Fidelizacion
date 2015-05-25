package com.fidelizacion;

/**
 * Created by Admin on 21/05/2015.
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    //iv length should be 16 bytes
    private String iv = "fedcba9876543210";
    private String key = null;
    private Cipher cipher = null;
    private SecretKeySpec keySpec = null;
    private IvParameterSpec ivSpec = null;

    /*
     * Constructor
     *
     * @throws Exception
     */
    public Crypto(String key) throws Exception {
        this.key = key;

// Make sure the key length should be 16
        int len = this.key.length();
        if(len < 16) {
            int addSpaces = 16 - len;
            for (int i = 0; i < addSpaces; i++) {
                this.key = this.key + " ";
            }
        } else {
            this.key = this.key.substring(0, 16);
        }
        this.keySpec = new SecretKeySpec(this.key.getBytes(), "AES");
        this.ivSpec = new IvParameterSpec(iv.getBytes());
        this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
    }

    /*
     * Bytes to Hexa conversion
     *
     * @param data
     * @return
     */
    public String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        } else {
            int len = data.length;
            String str = "";
            for (int i = 0; i < len; i++) {
                if ((data[i] & 0xFF) < 16)
                    str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
                else
                    str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
            }
            return str;
        }
    }

    /* Encrpt the goven string
     *
     * @param plainData
     * @throws Exception
     */
    public String encrypt(String plainData) throws Exception {

// Make sure the plainData length should be multiple with 16
        int len = plainData.length();
        int q = len / 16;
        int addSpaces = ((q + 1) * 16) - len;
        for (int i = 0; i < addSpaces; i++) {
            plainData = plainData + " ";
        }

        this.cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
        byte[] encrypted = cipher.doFinal(plainData.getBytes());

        return bytesToHex(encrypted);
    }

}
