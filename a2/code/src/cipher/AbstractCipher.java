package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Contains common attributes of ciphers
 */
public abstract class AbstractCipher implements Cipher
{
    private String plaintext;
    private String ciphertext;
    private final String alphabet;

    /**
     * Creates an AbstractCipher
     */
    AbstractCipher()
    {
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
        this.plaintext = null;
        this.ciphertext = null;
    }

    /**
     * Gets the plaintext
     * @return The plaintext
     */
    public String getPlaintext()
    {
        return this.plaintext;
    }

    /**
     * Gets the ciphertext
     * @return The ciphertext
     */
    public String getCiphertext()
    {
        return this.ciphertext;
    }

    /**
     * Gets the alphabet
     * @return The English alphabet
     */
    public String getAlphabet()
    {
        return this.alphabet;
    }

    /**
     * Edits the plaintext given a new String
     * @param newText The new plaintext;
     */
    public void editPlaintext(String newText)
    {
        this.plaintext = newText;
    }

    /**
     * Edits the ciphertext given a new String
     * @param newText The new ciphertext
     */
    public void editCiphertext(String newText)
    {
        this.ciphertext = newText;
    }

    /**
     * Converts all letters to lowercase and non-alphabetic and non-whitespace characters are removed
     * @param str The String to be converted
     * @return The converted String
     */
    public String convert(String str)
    {
        return str.toLowerCase().replaceAll("[^'\\s'a-z]","");
    }

    /**
     * Encrypts the plaintext given in the InputStream in
     * Outputs the ciphertext to the OutputStream out if out != null
     * Otherwise prints the ciphertext
     * @param in The InputStream the plaintext is on
     * @param out The OutputStream to send the ciphertext to
     * @throws IOException
     */
    public void encrypt(InputStream in, OutputStream out) throws IOException
    {
        byte[] plaintextArr = new byte[in.available()];
        in.read(plaintextArr);
        String plaintext = new String(plaintextArr);
        plaintext = this.convert(plaintext);
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

    /**
     * Decrypts the ciphertext given in the InputStream in
     * Outputs the plaintext to the OutputStream out if out != null
     * Otherwise prints the plaintext
     * @param in The InputStream the ciphertext is on
     * @param out The OutputStream to send the plaintext to
     * @throws IOException
     */
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {
        byte[] ciphertextArr = new byte[in.available()];
        in.read(ciphertextArr);
        String ciphertext = new String(ciphertextArr);
        ciphertext = this.convert(ciphertext);
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

    /**
     * To be implemented in subclasses
     * @param plaintext The plaintext to be encrypted
     * @return null
     */
    public String encrypt(String plaintext)
    {
        return null;
    }

    /**
     * To be implemented in subclasses
     * @param ciphertext The ciphertext to decrypt
     * @return null
     */
    public String decrypt(String ciphertext)
    {
        return null;
    }

    /**
     * To be implemented in subclasses
     * @param out The OutputStream to write the cipher key to
     * @throws IOException
     */
    public abstract void save(OutputStream out) throws IOException;

}
