package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher
{
    private String plaintext;
    private String ciphertext;
    private final String alphabet;

    AbstractCipher()
    {
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
        this.plaintext = null;
        this.ciphertext = null;
    }

    public String getPlaintext()
    {
        return this.plaintext;
    }

    public String getCiphertext()
    {
        return this.ciphertext;
    }
    public String getAlphabet()
    {
        return this.alphabet;
    }

    public void editPlaintext(String newText)
    {
        this.plaintext = newText;
    }

    public void editCiphertext(String newText)
    {
        this.ciphertext = newText;
    }
    public void encrypt(InputStream in, OutputStream out) throws IOException
    {
        byte[] plaintextArr = new byte[in.available()];
        in.read(plaintextArr);
        String plaintext = new String(plaintextArr);
        editPlaintext(plaintext);
        String ciphertext = this.encrypt(plaintext);
        editCiphertext(ciphertext);
        if (out != null)
        {
            byte[] outBytes = ciphertext.getBytes();
            out.write(outBytes);
            out.close();
        }
        else
        {
            System.out.println(ciphertext);
        }
    }

    public void decrypt(InputStream in, OutputStream out) throws IOException
    {
        byte[] ciphertextArr = new byte[in.available()];
        in.read(ciphertextArr);
        String ciphertext = new String(ciphertextArr);
        editCiphertext(ciphertext);
        String plaintext = this.decrypt(ciphertext);
        editPlaintext(plaintext);
        if (out != null)
        {
            byte[] outBytes = plaintext.getBytes();
            out.write(outBytes);
            out.close();
        }
        else
        {
            System.out.println(plaintext);
        }
    }
    public String encrypt(String plaintext)
    {
        return null;
    }

    public String decrypt(String ciphertext)
    {
        return null;
    }

    public abstract void save(OutputStream out) throws IOException;

}
