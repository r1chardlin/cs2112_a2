package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher
{
    private String plaintext;
    private String ciphertext;

    public void encrypt(InputStream in, OutputStream out) throws IOException
    {

    }
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {

    }
    public String encrypt(String plaintext)
    {

    }
    public String decrypt(String ciphertext)
    {

    }

    public void save(OutputStream out) throws IOException
    {

    }

}
