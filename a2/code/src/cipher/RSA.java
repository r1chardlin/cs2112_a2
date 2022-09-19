package cipher;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;

public class RSA extends AbstractCipher implements ChunkReader
{
    private BigInteger e;
    private BigInteger n;
    private BigInteger d;

    RSA()
    {
        // TODO generate random keys
    }

    RSA(BigInteger e, BigInteger n, BigInteger d)
    {
        this.e = e;
        this.n = n;
        this.d = d;
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
        return Arrays.copyOfRange(data, 0, 126);
    }

    public void encrypt(InputStream in, OutputStream out) throws IOException
    {

    }
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {

    }

    public String encrypt(String plaintext)
    {
        byte[] plainBytes = plaintext.getBytes();

    }

    public String decrypt(String ciphertext)
    {

    }


}
