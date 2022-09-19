package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher
{
    private String plaintext;
    private String ciphertext;
    private String alphabet;

    AbstractCipher()
    {
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
    }

    public String getAlphabet()
    {
        return this.alphabet;
    }
    public abstract void encrypt(InputStream in, OutputStream out) throws IOException;
    public abstract void decrypt(InputStream in, OutputStream out) throws IOException;
    public abstract String encrypt(String plaintext);

    public abstract String decrypt(String ciphertext);

    public void save(OutputStream out) throws IOException
    {

    }

}
