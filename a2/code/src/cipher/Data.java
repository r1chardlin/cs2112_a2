package cipher;

import java.io.EOFException;
import java.io.IOException;

/**
 * Contains relevant information on the input file and for RSA encryption/decryption
 */
public class Data implements ChunkReader
{
    private int chunkSize;
    private int byteIndex;
    private int dataLen;

    /**
     * Creates a Data object
     * @param chunkSize The size of the chunk of bytes
     * @param dataLen The number of bytes in the input file
     */
    Data(int chunkSize, int dataLen)
    {
        this.chunkSize = chunkSize;
        this.byteIndex = 0;
        this.dataLen = dataLen;
    }

    /**
     * Gets the byteIndex
     * @return The byteIndex
     */
    public int getByteIndex()
    {
        return this.byteIndex;
    }

    /**
     * Gets dataLen
     * @return The number of bytes in the input file
     */
    public int getDataLen()
    {
        return this.dataLen;
    }

    /**
     * increases the byteIndex by the chunkSize
     */
    public void increaseByteIndex()
    {
        this.byteIndex += this.chunkSize();
    }

    /**
     * Returns the maximum number of bytes in a chunk.
     */
    public int chunkSize()
    {
        return this.chunkSize;
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
}
