package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represent a Vigenere cipher
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.09.22
 */
public class Vigenere extends AbstractCipher
{
    private String alphabet;
    private String key;

    /**
     * Creates a Vigenere cipher given a key
     * @param key The key
     */
    Vigenere(String key)
    {
        this.alphabet = super.getAlphabet();
        this.key = key;
    }

    /**
     * Encrypts the given plaintext
     * @param plaintext The plaintext to be encrypted
     * @return The encrypted plaintext
     */
    public String encrypt(String plaintext)
    {
        String ciphertext = "";
        int keyIndex = 0;
        for (int i = 0; i < plaintext.length(); i++)
        {
            if (Character.isWhitespace(plaintext.charAt(i)))
            {
                ciphertext += " ";
            }
            else
            {
                int charValue = alphabet.indexOf(plaintext.charAt(i));
                int keyValue = alphabet.indexOf(key.charAt(keyIndex)) + 1;
                int value = charValue + keyValue;
                if (value >= alphabet.length())
                {
                    value -= alphabet.length();
                }
                ciphertext += alphabet.charAt(value);
                keyIndex++;
                if (keyIndex >= key.length())
                {
                    keyIndex = 0;
                }
            }
        }
        return ciphertext;
    }

    /**
     * Decrypts the given ciphertext
     * @param ciphertext The ciphertext to decrypt
     * @return The decrypted ciphertext
     */
    public String decrypt(String ciphertext)
    {
        String plaintext = "";
        int keyIndex = 0;
        for (int i = 0; i < ciphertext.length(); i++)
        {
            if (Character.isWhitespace(ciphertext.charAt(i)))
            {
                plaintext += " ";
            }
            else
            {
                int charValue = alphabet.indexOf(ciphertext.charAt(i));
                int keyValue = alphabet.indexOf(key.charAt(keyIndex)) + 1;
                int value = charValue - keyValue;
                if (value < 0)
                {
                    value = alphabet.length() + value;
                }
                plaintext += alphabet.charAt(value);
                keyIndex++;
                if (keyIndex >= key.length())
                {
                    keyIndex = 0;
                }
            }
        }
        return plaintext;
    }

    /**
     * Saves the Vigenere cipher to a specified OutputStream
     * @param out The OutputStream to write the cipher key to
     * @throws IOException
     */
    public void save(OutputStream out) throws IOException {
        String outText = "VIGENERE\n" + this.key + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
        out.close();
    }
}
