import java.util.ArrayList;

/**
 * A node in a tree
 */
public class Node {

    /**
     * The label of the node instance
     */
    private String label;

    /**
     * The value that this node instance is storing
     */
    private String value;

    /**
     * An ArrayList containing all the node instance's child node instances
     */
    private ArrayList<Node> children;

    /**
     * The constructor for the node
     * @param label The label of the node
     * @param value The value that this node is storing
     */
    public Node( String label, String value ){
        this.label = label;
        this.value = value;
        this.children = new ArrayList<>();
    }

    /**
     * Traverse this node to access it's child nodes
     * @param path An integer representing the route to traverse
     * @return The node at that branch off the node instance
     * @throws Exception
     */
    public Node traverse( int path ) throws Exception {

        if ( path < this.children.size() ){
            return this.children.get( path );
        } else {
            throw new Exception("Cannot traverse path; Path ID out of range!");
        }

    }

    /**
     * Adds a child to this node instance
     * @param child The child node to add
     */
    public void addChild( Node child ){
        this.children.add( child );
    }

    /**
     * Gets all the children of this node
     * @return An ArrayList of child nodes
     */
    public ArrayList<Node> getChildren(){
        return this.children;
    }

    /**
     * Loops through all the child nodes and gets all their sub-nodes
     * @return An ArrayList containing the path to every sub-node of this node
     * and it's path from here
     */
    public ArrayList<String[]> getChildrenRecursive(){
        ArrayList<String[]> childrenInfo = new ArrayList<>();

        for ( int i = 0; i < this.children.size(); i++ ){

            childrenInfo.add( new String[]{ this.children.get( i ).getLabel(), this.children.get( i ).getValue(), String.valueOf(i) } );
            if ( this.children.get(i).getChildren().size() != 0 ){
                ArrayList<String[]> c = this.children.get(i).getChildrenRecursive();
                for ( int j = 0; j < c.size(); j++ ){
                    c.get(j)[2] = String.valueOf(i) + c.get(j)[2];
                    childrenInfo.add( c.get( j ) );
                }
            }


        }

        return childrenInfo;
    }

    /**
     * Sets the label for this node
     * @param label The new label
     */
    public void setLabel( String label ){
        this.label = label;
    }

    /**
     * Gets the label for this node
     * @return A string containing the label
     */
    public String getLabel(){
        return this.label;
    }

    /**
     * Sets the value of this node instance
     * @param value The value to set
     */
    public void setValue( String value ){
        this.value = value;
    }

    /**
     * Gets the value for this node instance
     * @return A string containing the node instance's value
     */
    public String getValue(){
        return this.value;
    }
}
