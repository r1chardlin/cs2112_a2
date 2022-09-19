package cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomSubstitution extends AbstractCipher
{
    private String alphabet;
    private String encryptedAlphabet;

    RandomSubstitution()
    {
        this.alphabet = super.getAlphabet();
        List<String> temp = Arrays.asList(alphabet.split("\\s*,\\s*"));
        this.encryptedAlphabet = "";
        int tempLen = temp.size();
        for (int i = 0; i < tempLen; i++)
        {
            int index = (int)(Math.random() * temp.size());
            encryptedAlphabet += temp.get(i);
            temp.remove(index);
        }
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
                int index = alphabet.indexOf(plaintext.charAt(i));
                ciphertext += encryptedAlphabet.charAt(index);
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
                int index = encryptedAlphabet.indexOf(ciphertext.charAt(i));
                plaintext += alphabet.charAt(index);
            }
        }
        return plaintext;
    }
}
