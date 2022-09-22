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
 */
public class Main {

    public static void main(String[] args) throws IOException
    {
        // TESTING

        // Caesar

//        // Random Substitution
//        MonoSubstitution randSub = new MonoSubstitution();
//        System.out.println(randSub.getAlphabet());
//        System.out.println(randSub.getEncryptedAlphabet());
//        String[] test = randSub.getEncryptedAlphabet().split("");
//        Arrays.sort(test);
//        System.out.println(Arrays.toString(test));
//        String randResult = randSub.encrypt("abcdefghijklmnopqrstuvwxyz");
//        System.out.println(randResult);
//        System.out.println(randSub.decrypt(randResult));

        // Vigenere
//        Vigenere vigenere = new Vigenere("abc");
//        String input = "tomorrow and tomorrow and tomorrow in the petty pace from day to day";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        OutputStream out = new FileOutputStream("test.txt");
//        vigenere.encrypt(in, out);
//        InputStream in2 = new FileInputStream("test.txt");
//        OutputStream out2 = new FileOutputStream("test2.txt");
//        vigenere.decrypt(in2, out2);
//        String vigResult = vigenere.encrypt("tomorrow and tomorrow and tomorrow in the petty pace from day to day");
//        System.out.println(vigResult);
//        System.out.println(vigenere.decrypt(vigResult));

        // RSA
//        RSA rsa = new RSA();
//        String inStr = "Hello world!";
//        InputStream inRSA = new ByteArrayInputStream(inStr.getBytes());
//        OutputStream outRSA = new FileOutputStream("rsaTest.txt");
//        rsa.encrypt(inRSA, outRSA);
//        InputStream inRSA2 = new FileInputStream("rsaTest.txt");
//        OutputStream outRSA2 = new FileOutputStream("rsaTest2.txt");
//        rsa.decrypt(inRSA2, outRSA2);

        // TODO implement
        // NOTE: To convert String to Input Stream -
        // InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        Main inputParser = new Main();
//        System.out.println("test");
        int pos = 0;
        pos = inputParser.parseCipherType(args, pos);
        pos = inputParser.parseCipherFunction(args, pos);
        while (pos < args.length)
        {
            pos = inputParser.parseOutputOptions(args, pos);
        }
    }

//    public static String cipherType;
//    public static String[] extraArgs;
    public static CipherFactory factory = new CipherFactory();
    public static Cipher cipher;
    public static String cipherFunction;
    public static String input;

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
                // TODO create a Caesar cipher object with the given shift parameter
                int shift = Integer.parseInt(args[pos++]);
                cipher = factory.getCaesarCipher(shift);
                break;
            case "--random":
                // TODO create a random substitution cipher object
                cipher = factory.getRandomSubstitutionCipher();
                break;
            case "--monoLoad":
                // TODO load a monoaphabetic substitution cipher from a file
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
                // TODO create a new Vigenere Cipher with the given key
                break;
            case "--vigenereLoad":
                // TODO create a Vigenere cipher with key loaded from the given file
                String fNameVig = args[pos++];
                BufferedReader fhandVig = new BufferedReader(new FileReader(fNameVig));
                fhandVig.readLine();
                String loadedKey = fhandVig.readLine();
                loadedKey = loadedKey.substring(0, loadedKey.length() - 1);
                cipher = factory.getVigenereCipher(loadedKey);
                break;
            case "--rsa":
                // TODO create new RSA cipher
                cipher = factory.getRSACipher();
                break;
            case "--rsaLoad":
                // TODO load an RSA key from the given file
                String fNameRSA = args[pos++];
                BufferedReader fhandRSA = new BufferedReader(new FileReader(fNameRSA));
                fhandRSA.readLine();
                String eStr = fhandRSA.readLine();
                String nStr = fhandRSA.readLine();
                String dStr = fhandRSA.readLine();
//                eStr = eStr.substring(0, eStr.length() - 1);
//                nStr = nStr.substring(0, nStr.length() - 1);
//                dStr = dStr.substring(0, dStr.length() - 1);
                BigInteger e = new BigInteger(eStr);
                BigInteger n = new BigInteger(nStr);
                BigInteger d = new BigInteger(dStr);
                cipher = factory.getRSACipher(e, n, d);
                break;
            default:
                // TODO
                System.out.println("please enter a valid command");
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
                // TODO encrypt the given string
                cipherFunction = "em";
                input = args[pos++];
                break;
            case "--ef":
                // TODO encrypt the contents of the given file
                cipherFunction = "ef";
                input = args[pos++];
                break;
            case "--dm":
                // TODO decrypt the given string -- substitution ciphers only
                cipherFunction = "dm";
                input = args[pos++];
                break;
            case "--df":
                // TODO decrypt the contents of the given file
                cipherFunction = "df";
                input = args[pos++];
                break;
            default:
                // TODO
                System.out.println("please enter a valid command");
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
                    // TODO print result of applying the cipher to the console -- substitution
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
                    // TODO output result of applying the cipher to a file
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
                    // TODO save the cipher key to a file
                    String saveFile = args[pos++];
                    OutputStream saveStream = new FileOutputStream(saveFile);
                    cipher.save(saveStream);
                    break;
                default:
                    // TODO
                    System.out.println("please enter a valid command");
            }
        }
        return pos;
    }
}
