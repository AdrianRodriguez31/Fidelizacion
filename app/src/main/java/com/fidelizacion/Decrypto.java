package com.fidelizacion;

/**
 * Created by Admin on 21/05/2015.
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypto {
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
    public Decrypto(String key) throws Exception {
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
     * Hexa to Bytes conversion
     *
     * @param str
     * @return
     */
    public byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    /* Decrypt the given excrypted string
     *
     * @param encrStr
     * @throws Exception
     */
    public String decrypt(String encrData) throws Exception {
        this.cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
        byte[] outText = this.cipher.doFinal(hexToBytes(encrData));

        String decrData = new String(outText).trim();
        return decrData;
    }
    /*public static void main(String[] args) throws Exception {
        Decrypto c = new Decrypto("D4:6E:AC:3F:F0:BE");
        String decrStr = c.decrypt("1f50a943601d8e29206c716f82e58b8d");
        System.out.println("Decrypted Str :" + decrStr);
    }*/
}
