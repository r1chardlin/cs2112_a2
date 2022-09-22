package cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a MonoSubstitution cipher
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.09.22
 */
public class MonoSubstitution extends AbstractCipher
{
    private String encryptedAlphabet;

    /**
     * Creates a MonoSubstitution cipher where the encryptedAlphabet is a shifted version of the alphabet.
     * In other words, creates a Caesar cipher.
     * @param shift The amount of units to shift the alphabet to create the encryptedAlphabet
     */
    MonoSubstitution(int shift)
    {
        this.encryptedAlphabet = "";
        for (int i = shift; i < super.getAlphabet().length(); i++)
        {
            encryptedAlphabet += super.getAlphabet().charAt(i);
        }
        for (int i = 0; i < shift; i++)
        {
            encryptedAlphabet += super.getAlphabet().charAt(i);
        }
    }

    /**
     * Constructs a Random Substitution MonoSubstitution cipher with a randomly generated encryptedAlphabet
     */
    MonoSubstitution()
    {
        this.encryptedAlphabet = "";
        String[] alphabetArr = super.getAlphabet().split("");
//        System.out.println(Arrays.toString(alphabetArr));
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < alphabetArr.length; i++)
        {
            temp.add(alphabetArr[i]);
        }
        int tempLen = temp.size();
//        System.out.println(tempLen);
        for (int i = 0; i < tempLen; i++)
        {
            int index = (int)(Math.random() * temp.size());
//            System.out.println(index);
            encryptedAlphabet += temp.get(index);
            temp.remove(index);
        }
    }

    /**
     * Creates a MonoSubstitution Cipher with a given encryptedAlphabet
     * @param encryptedAlphabet The given encrypted alphabet
     */
    MonoSubstitution(String encryptedAlphabet)
    {
        this.encryptedAlphabet = encryptedAlphabet;
    }

    /**
     * Returns the encryptedAlphabet
     * @return The encrypted alphabet
     */
    public String getEncryptedAlphabet()
    {
        return this.encryptedAlphabet;
    }

    /**
     * Encrypts the given plaintext
     * @param plaintext The plaintext to be encrypted
     * @return The encrypted plaintext
     */
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
                int index = super.getAlphabet().indexOf(plaintext.charAt(i));
                ciphertext += this.encryptedAlphabet.charAt(index);
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
        for (int i = 0; i < ciphertext.length(); i++)
        {
            if (Character.isWhitespace(ciphertext.charAt(i)))
            {
                plaintext += " ";
            }
            else
            {
                int index = encryptedAlphabet.indexOf(ciphertext.charAt(i));
                plaintext += super.getAlphabet().charAt(index);
            }
        }
        return plaintext;
    }

    /**
     * Saves the MonoSubstitution cipher to a specified OutputStream
     * @param out The OutputStream to write the cipher key to
     * @throws IOException
     */
    public void save(OutputStream out) throws IOException {
        String outText = "MONO\n" + this.encryptedAlphabet + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
        out.close();
    }
}
