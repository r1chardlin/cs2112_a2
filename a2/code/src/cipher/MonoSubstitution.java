package cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonoSubstitution extends AbstractCipher
{
    private String alphabet;
    private String encryptedAlphabet;

    // Caesar
    MonoSubstitution(int shift)
    {
        this.alphabet = super.getAlphabet();
        this.encryptedAlphabet = "";
        for (int i = 5; i < this.alphabet.length(); i++)
        {
            encryptedAlphabet += this.alphabet.charAt(i)
        }
        for (int i = 0; i < shift; i++)
        {
            encryptedAlphabet += this.alphabet.charAt(i)
        }
    }

    // Random Substitution
    MonoSubstitution()
    {
        this.alphabet = super.getAlphabet();
        this.encryptedAlphabet = "";
        List<String> temp = Arrays.asList(alphabet.split("\\s*,\\s*"));
        int tempLen = temp.size();
        for (int i = 0; i < tempLen; i++)
        {
            int index = (int)(Math.random() * temp.size());
            encryptedAlphabet += temp.get(i);
            temp.remove(index);
        }
    }

    // Uses given encryptedAlphabet
    MonoSubstitution(String encryptedAlphabet)
    {
        this.alphabet = super.getAlphabet();
        this.encryptedAlphabet = encryptedAlphabet;
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

    public void save(OutputStream out)
    {
        String outText = "MONO\n" + this.encryptedAlphabet + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
    }
}
