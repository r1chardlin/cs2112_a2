package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Vigenere extends AbstractCipher
{
    private String alphabet;
    private String key;

    Vigenere(String key)
    {
        this.alphabet = super.getAlphabet();
        this.key = key;
    }

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

    public void save(OutputStream out) throws IOException {
        String outText = "VIGENERE\n" + this.key + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
        out.close();
    }
}
