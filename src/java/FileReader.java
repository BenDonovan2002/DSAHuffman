import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A layer of abstraction on top of the Scanner class to assist in reading files
 */
public class FileReader {

    /**
     * Reads a file
     * @param fileName The file to read
     * @return An ArrayList of Strings where each string is a line of the file
     * @throws Exception
     */
    public ArrayList<String> readFile( String fileName ) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File f = new File( fileName );
            Scanner s = new Scanner( f );
            while( s.hasNextLine() ){
                String data = s.nextLine();
                lines.add( data );
            }

        } catch ( FileNotFoundException e ){
            throw new Exception("File Not Found!");
        }
        return lines;
    }

}
