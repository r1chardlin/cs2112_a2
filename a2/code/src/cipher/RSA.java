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

    private int byteIndex;
    private int dataLen;

    RSA()
    {
        this.byteIndex = 0;
        this.dataLen = null;
        // TODO generate random keys
    }

    RSA(BigInteger e, BigInteger n, BigInteger d)
    {
        this.e = e;
        this.n = n;
        this.d = d;
        this.byteIndex = 0;
        this.dataLen = null;
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
        return data.length - index;
    }


    public void encrypt(InputStream in, OutputStream out) throws IOException
    {
        byte[] plaintextArr = new byte[in.available()];
        this.dataLen = plaintextArr.length
        in.read(plaintextArr);
        while (this.hasNext())
        {
            byte[] chunk = Arrays.copyOfRange(data, byteIndex, byteIndex + this.chunkSize());
            BigInteger chunkInt = new BigInteger(chunk);
            // TODO: Use RSA to encrypt chunkInt
            byteIndex += this.chunkSize();
        }
    }
    public void decrypt(InputStream in, OutputStream out) throws IOException
    {

    }

    public void save(OutputStream out)
    {
        String outText = "RSA\n" + this.e + "\n" + this.n + "\n" + this.d + "\n";
        byte[] outBytes = outText.getBytes();
        out.write(outBytes);
    }
}
