package cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents an RSA cipher
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.09.22
 */
public class RSA extends AbstractCipher
{
    private BigInteger e;
    private BigInteger n;
    private BigInteger d;

    /**
     * Creates an RSA cipher using randomly generated values for p and q
     */
    RSA()
    {
        Random randP = new Random();
        Random randQ = new Random();
        BigInteger p = new BigInteger(511, 20, randP);
        BigInteger q = new BigInteger(511, 20, randQ);
        while (p.equals(q))
        {
            q = new BigInteger(511, 20, randQ);
        }
        this.n = p.multiply(q);
        BigInteger one = new BigInteger("1");
        BigInteger totient = p.subtract(one).multiply(q.subtract(one));
        BigInteger r = totient.sqrt();
        while (!(totient.gcd(r).equals(one)))
        {
            r = r.add(one);
        }
        this.e = r;
        this.d = this.e.modInverse(totient);
    }

    /**
     * Creates an RSA cipher given e, n, and d
     * @param e An integer that is relatively prime to the totient
     * @param n The product of p and q
     * @param d The multiplicative inverse of e modulo the totient
     */
    RSA(BigInteger e, BigInteger n, BigInteger d)
    {
        this.e = e;
        this.n = n;
        this.d = d;
    }

    /**
     * Gets e
     * @return e
     */
    public BigInteger getE()
    {
        return this.e;
    }

    /**
     * Gets e
     * @return e
     */
    public BigInteger getN()
    {
        return this.n;
    }

    /**
     * Gets e
     * @return e
     */
    public BigInteger getD()
    {
        return this.d;
    }

    /**
     * Encrypts the plaintext bytes given in the InputStream in using e and n.
     * Writes the encryptedBytes to the OutputStream out if out != null.
     * Otherwise, prints the encryptedBytes
     * @param in The InputStream the plaintext is on
     * @param out The OutputStream to send the ciphertext to
     * @throws IOException
     */
    public void encrypt(InputStream in, OutputStream out) throws IOException
    {
        Data chunkReader = new Data(126, in.available());
//        System.out.println(in.available());
//        System.out.println(chunkReader.chunkSize());
        while (chunkReader.hasNext())
        {
            byte[] tempChunk = new byte[chunkReader.chunkSize()];
//            System.out.println(chunkReader.getByteIndex());
//            in.read(tempChunk, chunkReader.getByteIndex(), chunkReader.chunkSize());
            for (int i = 0; i < chunkReader.chunkSize(); i++)
            {
                int nextByte = in.read();
                if (nextByte == -1)
                {
                    nextByte = (byte)(0);
                }
                tempChunk[i] = (byte)(nextByte);
            }
            byte[] chunk = new byte[chunkReader.chunkSize() + 1];
            if (chunkReader.getByteIndex() + chunkReader.chunkSize() >= chunkReader.getDataLen())
            {
                chunk[0] = (byte)(chunkReader.getDataLen() - chunkReader.getByteIndex());
            }
            else
            {
                chunk[0] = (byte)(chunkReader.chunkSize());
            }
            for (int i = 0; i < tempChunk.length; i++)
            {
                chunk[i + 1] = tempChunk[i];
            }
            BigInteger chunkInt = new BigInteger(chunk);
            // TODO: Use RSA to encrypt chunkInt
            BigInteger encryptedInt = chunkInt.modPow(this.e, this.n);
            byte[] encryptedBytes = encryptedInt.toByteArray();
            if (encryptedBytes.length < 128)
            {
                byte[] temp = new byte[128];
                int tempIndex = 0;
                while (tempIndex < 128 - encryptedBytes.length)
                {
                    temp[tempIndex] = 0;
                    tempIndex++;
                }
                int ogIndex = 0;
                while(tempIndex < 128)
                {
                    temp[tempIndex] = encryptedBytes[ogIndex];
                    tempIndex++;
                    ogIndex++;
                }
                encryptedBytes = temp;
            }
            if (out != null)
            {
                out.write(encryptedBytes);
                chunkReader.increaseByteIndex();
            }
            else
            {
                System.out.print(Arrays.toString(encryptedBytes));
            }
        }
        if (out != null)
        {
            out.close();
        }
    }

    /**
     * Decrypts the cipherText bytes given in the InputStream in using d and n.
     * Writes the decrypted bytes to the OutputStream out if out != null.
     * Otherwise, prints the decrypted bytes
     * @param in The InputStream the ciphertext is on
     * @param out The OutputStream to send the plaintext to
     * @throws IOException
     */
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {
        Data chunkReader = new Data(128, in.available());
        while(chunkReader.hasNext())
        {
            byte[] encryptedChunk = new byte[chunkReader.chunkSize()];
//            in.read(encryptedChunk, chunkReader.getByteIndex(), chunkReader.chunkSize());
            for (int i = 0; i < chunkReader.chunkSize(); i++)
            {
                int nextByte = in.read();
                encryptedChunk[i] = (byte)(nextByte);
            }
            BigInteger encryptedInt = new BigInteger(encryptedChunk);
            BigInteger chunkInt = encryptedInt.modPow(this.d, this.n);
            byte[] chunk = chunkInt.toByteArray();
            int plaintextLen = chunk[0];
            byte[] reducedChunk = new byte[plaintextLen];
            for (int i = 0; i < plaintextLen; i++)
            {
                reducedChunk[i] = chunk[i + 1];
            }
            if (out != null)
            {
                out.write(reducedChunk);
            }
            else
            {
                System.out.print(new String(reducedChunk));
            }
            chunkReader.increaseByteIndex();
        }
        if (out != null)
        {
            out.close();
        }
    }

    public void save(OutputStream out) throws IOException
    {
        String outText = "RSA\n" + this.e + "\n" + this.n + "\n" + this.d + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
    }
}
