/**
 * A class used to return the relevant information
 * for an encoded binary string
 */
public class BinaryWrapper {
    private byte[] binaryData;
    private int falseDigits;

    /**
     * Constructor for the BinaryWrapper
     * @param binaryData The byte array which contains the encoded binary values
     * @param falseDigits The number of '0' characters which had to be appended to get the
     *                    binary string to be divisible by 0
     */
    public BinaryWrapper( byte[] binaryData, int falseDigits ){
        this.binaryData = binaryData;
        this.falseDigits = falseDigits;
    }

    /**
     * A getter for binaryData
     * @return The byte array containing the encoded binary values
     */
    public byte[] getBinaryData() {
        return binaryData;
    }

    /**
     * A getter for falseDigits
     * @return An integer containing the number of '0' characters added to the end of the binary string
     */
    public int getFalseDigits() {
        return falseDigits;
    }
}
