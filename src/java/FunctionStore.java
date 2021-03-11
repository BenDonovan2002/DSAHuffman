import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The class which stores most of the essential functions
 */
public class FunctionStore {
    /**
     * A selection sort algorithm to sort an ArrayList of nodes by their value
     * @param unsortedList The list to sort
     * @return A sorted ArrayList of nodes
     */
    public static ArrayList<Node> selectionSort(ArrayList<Node> unsortedList ){
        for ( int i = 0; i < unsortedList.size()-1; i++ ){
            int currentLowestIndex = i;

            for ( int ii = i+1; ii < unsortedList.size(); ii++ ){
                if ( Integer.parseInt( unsortedList.get(ii).getValue() ) < Integer.parseInt( unsortedList.get(currentLowestIndex).getValue() ) ){
                    currentLowestIndex = ii;
                }
            }

            Node tempArr = unsortedList.get( currentLowestIndex );
            unsortedList.set( currentLowestIndex, unsortedList.get( i ) );
            unsortedList.set(i, tempArr);

        }

        return unsortedList;
    }

    /**
     * Generates a Huffman Tree from a frequency table
     * @param cMap The CharMap containing each character and it's respective frequency
     * @return A Tree which contains a hierarchy of nodes arranged using the Huffman Coding algorithm
     */
    public static Tree generateHuffmanTree( CharMap cMap ){

        ArrayList<String[]> iterMap = cMap.getIterableMap();
        ArrayList<Node> nodes = new ArrayList<>();

        // Create All The Nodes
        for ( int i = 0; i < iterMap.size(); i++ ){
            nodes.add( new Node( iterMap.get(i)[0].toString(), iterMap.get(i)[1].toString() ) );
        }

        while ( nodes.size() != 1 ){

            ArrayList<Node> newNodes = new ArrayList<>();

            int newFrequency = Integer.parseInt( nodes.get(0).getValue() )
                    + Integer.parseInt( nodes.get(1).getValue() );

            if ( nodes.size() == 2 ){
                Node newNode = new Node( "Freq", String.valueOf( newFrequency ) );
                newNode.addChild( nodes.get( 0 ) );
                newNode.addChild( nodes.get( 1 ) );
                newNodes.add( newNode );
            } else {

                // Combine two lowest nodes
                Node newNode = new Node( "Freq", String.valueOf( newFrequency ) );
                newNode.addChild( nodes.get( 0 ) );
                newNode.addChild( nodes.get( 1 ) );
                newNodes.add( newNode );

                for ( int i = 2 ; i < nodes.size(); i++ ){
                    newNodes.add( nodes.get(i) );
                }

                newNodes = selectionSort( newNodes );
            }

            nodes = newNodes;

        }

        return new Tree( nodes.get( 0 ) );

    }

    //Plagiarism
    /**
     * Converts a binary sequence in String form into a byte array
     * @param s A String containing the binary sequence to encode
     * @return A byte array containing the encoded binary sequence
     */
    public static BinaryWrapper GetBinary( String s ){
        StringBuilder sB = new StringBuilder(s);
        int falseDigits = 0;

        while ( sB.length() % 8 != 0 ){
            sB.append("0");
            falseDigits += 1;
        }

        String binString = sB.toString();

        byte[] data = new byte[ binString.length() / 8 ];

        for ( int i = 0; i < binString.length(); i++ ){
            char c = binString.charAt(i);
            if ( c == '1' ){
                data[i >> 3] |= 0x80 >> (i & 0x7);
            }
        }

        return new BinaryWrapper( data, falseDigits );
    }

    /**
     * Converts a byte array into a binary sequence
     * @param bytes The byte array to convert
     * @return The binary sequence String
     */
    public static String GetString( byte[] bytes ){
        StringBuilder sB = new StringBuilder( bytes.length * Byte.SIZE );
        for ( int i = 0; i < Byte.SIZE * bytes.length; i++ ){
            sB.append( (bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1' );
        }

        return sB.toString();
    }

    /**
     * Converts a txt file into a Huffman encoded bin file using a previously generated tree
     * @param inputPath The path to the file to encode
     * @param outputPath The directory where the files should be output
     * @param treePath The path to the tree to use for encoding
     * @throws Exception
     */
    public static void EncodeFile( String inputPath, String outputPath, String treePath ) throws Exception {
        CharMap cMap = new CharMap();
        FileReader f = new FileReader();

        ArrayList<String> lines = f.readFile(inputPath);

        int highestFreq = 0;

        for ( int i = 0; i < lines.size(); i++ ){
            for ( int ii = 0; ii < lines.get(i).length(); ii++ ){
                if ( cMap.getValue( lines.get(i).charAt( ii ) ) == null ){
                    cMap.setValue( lines.get(i).charAt( ii ), 1 );
                } else {
                    if ( cMap.getValue( lines.get(i).charAt( ii ) ) + 1 > highestFreq ){
                        highestFreq = cMap.getValue( lines.get(i).charAt( ii ) ) + 1;
                    }
                    cMap.setValue( lines.get(i).charAt( ii ), cMap.getValue( lines.get(i).charAt( ii ) ) + 1 );
                }
            }
            if ( cMap.getValue( '\n' ) == null ){
                cMap.setValue( '\n', 1 );
            } else {
                cMap.setValue( '\n', cMap.getValue( '\n' ) + 1 );
            }
        }

        try {
            File myObj = new File(treePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split( ":" );

                if ( data[0].equals("Freq") != true && data[0].equals("FD") != true ){
                    if ( cMap.getValue( (char) Integer.parseInt( data[0] ) ) != null ){
                        cMap.setValue( (char) Integer.parseInt( data[0] ), cMap.getValue( (char) Integer.parseInt( data[0] ) ) + highestFreq );
                    } else {
                        cMap.setValue( (char) Integer.parseInt( data[0] ), highestFreq );
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Tree huffmanTree = generateHuffmanTree( cMap );

        ArrayList<String[]> ch = huffmanTree.getRootNode().getChildrenRecursive();

        // Generate encoded file
        HashMap< String, String > charCodes = new HashMap<>();

        for ( int i = 0; i < ch.size(); i++ ){
            charCodes.put( ch.get(i)[0], ch.get(i)[2] );
        }

        StringBuilder stringBuilder = new StringBuilder();

        for ( int i = 0; i < lines.size(); i++ ){
            for ( int ii = 0; ii < lines.get(i).length(); ii++ ){
                stringBuilder.append( charCodes.get( String.valueOf( lines.get(i).charAt( ii ) ) ) );
            }
            stringBuilder.append( charCodes.get( "\n" ) );
        }

        BinaryWrapper bW = GetBinary( stringBuilder.toString() );
        byte[] encodedStringBinary = bW.getBinaryData();

        if ( outputPath.charAt( outputPath.length()-1 ) != '/' &&
                outputPath.charAt( outputPath.length()-1 ) != '\\' ){
            outputPath += "/";
        }

        try {

            OutputStream outputStream = new FileOutputStream( outputPath + "encoded.bin" );
            outputStream.write( encodedStringBinary );
        } catch ( IOException e ){
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(outputPath + "tree.txt");
            myWriter.write( "FD:" + String.valueOf( bW.getFalseDigits() ) + "\n" );
            for ( int i = 0; i < ch.size(); i++ ){
                if ( ch.get(i)[0] == "Freq" ){
                    myWriter.write( ch.get(i)[0] + ":" + ch.get(i)[2] + "\n" );
                } else {
                    myWriter.write( (int)ch.get(i)[0].charAt(0) + ":" + ch.get(i)[2] + "\n" );
                }

            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Generates a tree for a text file and then converts that file into a Huffman encoded bin file
     * @param inputPath The path of the file to encode
     * @param outputPath The directory to output the files to
     * @throws Exception
     */
    public static void EncodeFile( String inputPath, String outputPath ) throws Exception {
        CharMap cMap = new CharMap();
        FileReader f = new FileReader();

        ArrayList<String> lines = f.readFile(inputPath);

        for ( int i = 0; i < lines.size(); i++ ){
            for ( int ii = 0; ii < lines.get(i).length(); ii++ ){
                if ( cMap.getValue( lines.get(i).charAt( ii ) ) == null ){
                    cMap.setValue( lines.get(i).charAt( ii ), 1 );
                } else {
                    cMap.setValue( lines.get(i).charAt( ii ), cMap.getValue( lines.get(i).charAt( ii ) ) + 1 );
                }
            }
            if ( cMap.getValue( '\n' ) == null ){
                cMap.setValue( '\n', 1 );
            } else {
                cMap.setValue( '\n', cMap.getValue( '\n' ) + 1 );
            }
        }

        Tree huffmanTree = generateHuffmanTree( cMap );

        ArrayList<String[]> ch = huffmanTree.getRootNode().getChildrenRecursive();

        // Generate encoded file
        HashMap< String, String > charCodes = new HashMap<>();

        for ( int i = 0; i < ch.size(); i++ ){
            charCodes.put( ch.get(i)[0], ch.get(i)[2] );
        }

        StringBuilder stringBuilder = new StringBuilder();

        for ( int i = 0; i < lines.size(); i++ ){
            for ( int ii = 0; ii < lines.get(i).length(); ii++ ){
                stringBuilder.append( charCodes.get( String.valueOf( lines.get(i).charAt( ii ) ) ) );
            }
            stringBuilder.append( charCodes.get( "\n" ) );
        }

        BinaryWrapper bW = GetBinary( stringBuilder.toString() );

        byte[] encodedStringBinary = bW.getBinaryData();

        if ( outputPath.charAt( outputPath.length()-1 ) != '/' &&
                outputPath.charAt( outputPath.length()-1 ) != '\\' ){
            outputPath += "/";
        }

        try {

            OutputStream outputStream = new FileOutputStream( outputPath + "encoded.bin" );
            outputStream.write( encodedStringBinary );
        } catch ( IOException e ){
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(outputPath + "tree.txt");
            myWriter.write( "FD:" + String.valueOf( bW.getFalseDigits() ) + "\n" );
            for ( int i = 0; i < ch.size(); i++ ){
                if ( ch.get(i)[0] == "Freq" ){
                    myWriter.write( ch.get(i)[0] + ":" + ch.get(i)[2] + "\n" );
                } else {
                    myWriter.write( (int)ch.get(i)[0].charAt(0) + ":" + ch.get(i)[2] + "\n" );
                }

            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Takes a Huffman Encoded bin file and converts it into a plaintext txt file
     * @param dirPath The directory the the encoded file and it's tree.txt is in
     * @param fileName The name of the file to decode
     * @throws IOException
     */
    public static void DecodeFile( String dirPath, String fileName ) throws IOException {
        if ( dirPath.charAt( dirPath.length()-1 ) != '/'
        && dirPath.charAt( dirPath.length()-1 ) != '\\' ){
            dirPath += "/";
        }

        HashMap<String, String> treeValues = new HashMap<>();
        int falseDigits = -1;
        try {
            File myObj = new File(dirPath + "tree.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split( ":" );

                if ( data[0].equals( "FD" ) ){
                    falseDigits = Integer.parseInt(data[1]);
                } else if ( data[0].equals("Freq") ){
                    treeValues.put( data[1], data[0] );
                } else {
                    treeValues.put( data[1], String.valueOf ( (char) Integer.parseInt( data[0] ) ) );
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String encodedString = "";

        try {
            byte[] AllBytes = Files.readAllBytes( Paths.get( dirPath + fileName ) );
            encodedString = GetString(  AllBytes );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String currentString = "";

        StringBuilder uSB = new StringBuilder();

        if ( falseDigits != -1 ){
            encodedString = encodedString.substring(0, encodedString.length()-falseDigits);
        }

        for ( int i = 0; i < encodedString.length(); i++ ){
            if ( treeValues.get( currentString + encodedString.charAt( i ) ) == null ){
                //Hit the end of the tree
                uSB.append( treeValues.get( currentString ) );
                currentString = "";
                currentString += encodedString.charAt( i );
            } else {
                currentString += encodedString.charAt(i);
            }
        }

        if ( treeValues.get( currentString ) != null ){
            uSB.append( treeValues.get( currentString ) );
        }

        try {
            FileWriter myWriter = new FileWriter(dirPath + "decoded.txt");
            myWriter.write( uSB.toString() );
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
