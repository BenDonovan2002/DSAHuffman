import java.lang.reflect.Array;
import java.util.*;

/**
 * A class which maps a character to a certain number
 */
public class CharMap {
    /**
     * The HashMap containing the values
     */
    private HashMap< Character, Integer > hMap = new HashMap<>();

    /**
     * Sets the value of a character in the HashMap
     * @param character The character to set the value of
     * @param freq The value to set
     */
    public void setValue( char character, int freq ){
        this.hMap.put( character, freq );
    }

    /**
     * Gets the value that corresponds to the character in the HashMap
     * @param character The character to get the value for
     * @return The value that corresponds to the given character
     */
    public Integer getValue( char character ){
        return this.hMap.get( character );
    }

    /**
     * A selection sort algorithm that orders an ArrayList of String arrays
     * by the second value in each array
     * @param unsortedList The list to sort
     * @return The sorted list
     */
    private ArrayList<String[]> selectionSort( ArrayList<String[]> unsortedList ){
        for ( int i = 0; i < unsortedList.size()-1; i++ ){
            int currentLowestIndex = i;

            for ( int ii = i+1; ii < unsortedList.size(); ii++ ){
                if ( Integer.parseInt( unsortedList.get(ii)[1] ) < Integer.parseInt( unsortedList.get(currentLowestIndex)[1] ) ){
                    currentLowestIndex = ii;
                }
            }

            String[] tempArr = unsortedList.get( currentLowestIndex );
            unsortedList.set( currentLowestIndex, unsortedList.get( i ) );
            unsortedList.set(i, tempArr);

        }

        return unsortedList;
    }

    /**
     * Gets all the values in the HashMap and returns them in order
     * @return An ArrayList containing the sorted key-value pairs of the HashMap
     */
    public ArrayList<String[]> getIterableMap(){
        ArrayList<String[]> itMap = new ArrayList<>();
        Iterator it = this.hMap.entrySet().iterator();

        while ( it.hasNext() ){
            Map.Entry pair = (Map.Entry)it.next();

            itMap.add( new String[]{ pair.getKey().toString(), pair.getValue().toString() } );

            it.remove();
        }

        ArrayList<String[]> sortedMap = this.selectionSort( itMap );



        return sortedMap;
    }
}
