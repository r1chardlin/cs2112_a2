package cipher;

import cipher.Cipher;
import cipher.CipherFactory;
import java.awt.event.InputEvent;
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Command line interface to allow users to interact with your ciphers.
 *
 * <p>We have provided some infrastructure to parse most of the arguments. It is your responsibility
 * to implement the appropriate actions according to the assignment specifications. You may choose
 * to "fill in the blanks" or rewrite this class.
 *
 * <p>Regardless of which option you choose, remember to minimize repetitive code. You are welcome
 * to add additional methods or alter the provided code to achieve this.
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.9.22
 */
public class Main
{

    public static void main(String[] args) throws IOException
    {
        Main inputParser = new Main();
        int pos = 0;

        pos = inputParser.parseCipherType(args, pos);
        if (!invalidInpFlag)
        {
            pos = inputParser.parseCipherFunction(args, pos);
            while (pos < args.length && !invalidInpFlag)
            {
                pos = inputParser.parseOutputOptions(args, pos);
            }
        }
    }
    public static CipherFactory factory = new CipherFactory();
    public static Cipher cipher;
    public static String cipherFunction;
    public static String input;
    public static boolean invalidInpFlag = false;

    /**
     * Set up the cipher type based on the options found in args starting at position pos, and
     * return the index into args just past any cipher type options.
     */
    private int parseCipherType(String[] args, int pos) throws IllegalArgumentException, IOException {
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        String cmdFlag = args[pos++];
        switch (cmdFlag) {
            case "--caesar":
                int shift = Integer.parseInt(args[pos++]);
                cipher = factory.getCaesarCipher(shift);
                break;
            case "--random":
                cipher = factory.getRandomSubstitutionCipher();
                break;
            case "--monoLoad":
                String fNameMono = args[pos++];
                BufferedReader fhandMono = new BufferedReader(new FileReader(fNameMono));
                fhandMono.readLine();
                String encrAlph = fhandMono.readLine();
                encrAlph = encrAlph.substring(0, encrAlph.length() - 1);
                cipher = factory.getMonoCipher(encrAlph);
                break;
            case "--vigenere":
                String key = args[pos++];
                cipher = factory.getVigenereCipher(key);
                break;
            case "--vigenereLoad":
                String fNameVig = args[pos++];
                BufferedReader fhandVig = new BufferedReader(new FileReader(fNameVig));
                fhandVig.readLine();
                String loadedKey = fhandVig.readLine();
                loadedKey = loadedKey.substring(0, loadedKey.length() - 1);
                cipher = factory.getVigenereCipher(loadedKey);
                break;
            case "--rsa":
                cipher = factory.getRSACipher();
                break;
            case "--rsaLoad":
                String fNameRSA = args[pos++];
                BufferedReader fhandRSA = new BufferedReader(new FileReader(fNameRSA));
                fhandRSA.readLine();
                String eStr = fhandRSA.readLine();
                String nStr = fhandRSA.readLine();
                String dStr = fhandRSA.readLine();
                BigInteger e = new BigInteger(eStr);
                BigInteger n = new BigInteger(nStr);
                BigInteger d = new BigInteger(dStr);
                cipher = factory.getRSACipher(e, n, d);
                break;
            default:
                System.out.println("please enter a valid command");
                invalidInpFlag = true;
        }
        return pos;
    }

    /**
     * Parse the operations to be performed by the program from the command-line arguments in args
     * starting at position pos. Return the index into args just past the parsed arguments.
     */
    private int parseCipherFunction(String[] args, int pos) throws IllegalArgumentException {
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        switch (args[pos++])
        {
            case "--em":
                cipherFunction = "em";
                input = args[pos++];
                break;
            case "--ef":
                cipherFunction = "ef";
                input = args[pos++];
                break;
            case "--dm":
                cipherFunction = "dm";
                input = args[pos++];
                break;
            case "--df":
                cipherFunction = "df";
                input = args[pos++];
                break;
            default:
                System.out.println("please enter a valid command");
                invalidInpFlag = true;
        }
        return pos;
    }

    /**
     * Parse options for output, starting within {@code args} at index {@code argPos}. Return the
     * index in args just past such options.
     */
    private int parseOutputOptions(String[] args, int pos) throws IllegalArgumentException, IOException {
        // check if arguments are exhausted
        if (pos == args.length) return pos;

        String cmdFlag;
        while (pos < args.length) {
            switch (cmdFlag = args[pos++]) {
                case "--print":
                    // ciphers only
                    if (cipherFunction.equals("em"))
                    {
                        InputStream in = new ByteArrayInputStream(input.getBytes());
                        cipher.encrypt(in, null);
                    }
                    if (cipherFunction.equals("ef"))
                    {
                        InputStream in = new FileInputStream(input);
                        cipher.encrypt(in, null);
                    }
                    if (cipherFunction.equals("dm"))
                    {
                        InputStream in = new ByteArrayInputStream(input.getBytes());
                        cipher.decrypt(in, null);
                    }
                    if (cipherFunction.equals("df"))
                    {
                        InputStream in = new FileInputStream(input);
                        cipher.decrypt(in, null);
                    }
                    break;
                case "--out":
                    String outputFile = args[pos++];
                    OutputStream out = new FileOutputStream(outputFile);
                    if (cipherFunction.equals("em"))
                    {
                        InputStream in = new ByteArrayInputStream(input.getBytes());
                        cipher.encrypt(in, out);
                    }
                    if (cipherFunction.equals("ef"))
                    {
                        InputStream in = new FileInputStream(input);
                        cipher.encrypt(in, out);
                    }
                    if (cipherFunction.equals("dm"))
                    {
                        InputStream in = new ByteArrayInputStream(input.getBytes());
                        cipher.decrypt(in, out);
                    }
                    if (cipherFunction.equals("df"))
                    {
                        InputStream in = new FileInputStream(input);
                        cipher.decrypt(in, out);
                    }
                    break;
                case "--save":
                    String saveFile = args[pos++];
                    OutputStream saveStream = new FileOutputStream(saveFile);
                    cipher.save(saveStream);
                    break;
                default:
                    System.out.println("please enter a valid command");
                    invalidInpFlag = true;
            }
        }
        return pos;
    }
}
