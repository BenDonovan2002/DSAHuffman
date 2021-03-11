import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The main entry point for the app
 */
public class App {

    /**
     * Asks the user for the path to the file to compress and
     * the path to where it should be outputted. It then calls
     * the EncodeFile function.
     * @throws Exception
     */
    public static void CompressionUI() throws Exception {
        Scanner ioScanner = new Scanner( System.in );
        System.out.println( "Please enter the name and full path of the file you would like to compress:" );
        String plainTextPath = ioScanner.nextLine();
        System.out.println( "Please enter the path of the directory you would like the encoded file to be put:" );
        String outputPath = ioScanner.nextLine();

        FunctionStore.EncodeFile(plainTextPath, outputPath);


    }

    /**
     * Gets the user to input the path to the file to compress,
     * the path to the tree to use to encode the file,
     * and the path to the output directory and then
     * calls the EncodeFile function
     * @throws Exception
     */
    public static void CompressionExistingUI() throws Exception {
        Scanner ioScanner = new Scanner( System.in );
        System.out.println( "Please enter the name and full path of the file you would like to compress:" );
        String plainTextPath = ioScanner.nextLine();
        System.out.println( "Please enter the path of the tree file you would like to use:" );
        String treePath = ioScanner.nextLine();
        System.out.println( "Please enter the path of the directory you would like the encoded file to be put:" );
        String outputPath = ioScanner.nextLine();

        FunctionStore.EncodeFile(plainTextPath, outputPath, treePath);


    }

    /**
     * Asks the user for the path to the directory where the encoded file is
     * and the name for that file and then calls the DecodeFile function
     * @throws Exception
     */
    public static void DecodingUI() throws Exception {
        Scanner ioScanner = new Scanner( System.in );
        System.out.println( "Please enter the path where the encoded file is located:" );
        String encodedPath = ioScanner.nextLine();
        System.out.println( "Please enter the name of the file, including the extension, that you would like to decode:" );
        String encodedName = ioScanner.nextLine();

        FunctionStore.DecodeFile(encodedPath, encodedName);
    }

    public static void main( String[] args ) throws Exception {
        boolean running = true;
        while ( running ){
            Scanner ioScanner = new Scanner( System.in );
            System.out.println( "Would you like to:\n1. Compress A File\n2. Decode A Compressed File\n3. Compress A File Using An Existing Tree\n4. Quit" );
            String userInput = ioScanner.nextLine();
            switch ( userInput ){
                case "1":
                    CompressionUI();
                    break;
                case "2":
                    DecodingUI();
                    break;
                case "3":
                    CompressionExistingUI();
                    break;
                case "4":
                    running = false;
            }
        }

    }
}
