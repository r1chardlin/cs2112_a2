package cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonoSubstitution extends AbstractCipher
{
    private String encryptedAlphabet;

    // Caesar
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

    // Random Substitution
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

    // Uses given encryptedAlphabet
    MonoSubstitution(String encryptedAlphabet)
    {
        this.encryptedAlphabet = encryptedAlphabet;
    }

    public String getEncryptedAlphabet()
    {
        return this.encryptedAlphabet;
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
                int index = super.getAlphabet().indexOf(plaintext.charAt(i));
                ciphertext += this.encryptedAlphabet.charAt(index);
            }
        }
        return ciphertext;
    }

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

    public void save(OutputStream out) throws IOException {
        String outText = "MONO\n" + this.encryptedAlphabet + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
        out.close();
    }
}
