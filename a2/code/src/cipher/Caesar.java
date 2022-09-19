package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Caesar extends AbstractCipher
{
    private String alphabet;
    private int shift;

    Caesar(int shift)
    {
        this.alphabet = super.getAlphabet();
        this.shift = shift;
    }

    public void encrypt(InputStream in, OutputStream out) throws IOException
    {

    }
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {

    }

    public String encrypt(String plaintext)
    {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++)
        {
            if (Character.isWhitespace(plaintext.charAt(i)))
            {
                ciphertext += " ";
            }
            else
            {
                int index = alphabet.indexOf(plaintext.charAt(i)) + this.shift;
                if (index >= alphabet.length())
                {
                    index -= alphabet.length();
                }
                else if (index < 0)
                {
                    index = alphabet.length() + index;
                }
                ciphertext += alphabet.charAt(index);
            }
        }
        return ciphertext;
    }

    public String decrypt(String ciphertext)
    {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++)
        {
            if (Character.isWhitespace(plaintext.charAt(i)))
            {
                plaintext += " ";
            }
            else
            {
                int index = alphabet.indexOf(plaintext.charAt(i)) - this.shift;
                if (index >= alphabet.length())
                {
                    index -= alphabet.length();
                }
                else if (index < 0)
                {
                    index = alphabet.length() + index;
                }
                plaintext += alphabet.charAt(index);
            }
        }
        return ciphertext;
    }
}
