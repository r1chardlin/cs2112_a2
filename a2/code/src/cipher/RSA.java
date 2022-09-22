package cipher;
import java.io.EOFException;
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
public class RSA extends AbstractCipher implements ChunkReader
{
    private BigInteger e;
    private BigInteger n;
    private BigInteger d;

    private int byteIndex;
    private int dataLen;

    /**
     * Creates an RSA cipher using randomly generated values for p and q
     */
    RSA()
    {
        this.byteIndex = 0;
        this.dataLen = 0;
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
        this.byteIndex = 0;
        this.dataLen = 0;
    }

    /**
     * Returns the maximum number of bytes in a chunk.
     */
    public int chunkSize()
    {
        return 126;
    }

    /**
     * Returns true if and only if there is at least one more byte of data to be
     * read in the current stream.
     */
    public boolean hasNext()
    {
        if (byteIndex < dataLen)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the next chunk of up to {@code chunkSize()} bytes from the current
     * input stream. The returned bytes are placed in the array {@code data},
     * starting from index 0. The number of bytes returned is always
     * {@code chunkSize()}, unless the end of the input stream has been reached and
     * there are fewer than {@code chunkSize()} bytes available, in which case all
     * remaining bytes are returned. The values in {@code data} after the region in
     * which bytes were written are unspecified.
     *
     * @param data An array of length at least {@code chunkSize()}.
     * @return The number of bytes returned, which is always between 1 and the chunk
     *         size.
     * @throws EOFException if there are no more bytes available.
     * @throws IOException  if any IO exception occurs.
     */
    public int nextChunk(byte[] data) throws EOFException, IOException
    {
        if (byteIndex + this.chunkSize() - 1 < data.length)
        {
            return this.chunkSize();
        }
        return data.length - byteIndex;
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
        byte[] plaintextArr = new byte[in.available()];
        this.dataLen = plaintextArr.length;
        in.read(plaintextArr);
        while (this.hasNext())
        {
            byte[] tempChunk = Arrays.copyOfRange(plaintextArr, this.byteIndex, this.byteIndex + this.chunkSize());
            byte[] chunk = new byte[this.chunkSize() + 1];
            if (byteIndex + this.chunkSize() >= this.dataLen)
            {
                chunk[0] = (byte)(this.dataLen - this.byteIndex);
            }
            for (int i = 0; i < tempChunk.length; i++)
            {
                chunk[i + 1] = tempChunk[i];
            }
            BigInteger chunkInt = new BigInteger(chunk);
            // TODO: Use RSA to encrypt chunkInt
            BigInteger encryptedInt = chunkInt.modPow(this.e, this.n);
            byte[] encryptedBytes = encryptedInt.toByteArray();
//            byte[] encryptedBytes = chunkInt.toByteArray();
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
                byteIndex += this.chunkSize();
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
        byte[] ciphertextArr = new byte[in.available()];
        in.read(ciphertextArr);
        int byteIndex2 = 0;
        while(byteIndex2 <= ciphertextArr.length - 128)
        {
            byte[] encryptedBytes = Arrays.copyOfRange(ciphertextArr, byteIndex2, byteIndex2 + 128);
            BigInteger encryptedInt = new BigInteger(encryptedBytes);
            BigInteger chunkInt = encryptedInt.modPow(this.d, this.n);
            byte[] chunk = chunkInt.toByteArray();
            int plaintextLen = chunk[0];
            byte[] reducedChunk = new byte[plaintextLen];
            for (int i = 0; i < plaintextLen; i++)
            {
                reducedChunk[i] = chunk[i + 1];
            }
//            byte[] chunk = encryptedInt.toByteArray();
            if (out != null)
            {
                out.write(reducedChunk);
            }
            else
            {
                System.out.print(new String(reducedChunk));
            }
            byteIndex2 += 128;
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
