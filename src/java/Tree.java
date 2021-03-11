/**
 * A tree instance
 */
public class Tree {

    /**
     * The node at the root of the tree
     */
    private Node rootNode;

    /**
     * The current node that the user has traversed to
     */
    private Node currentNode;

    /**
     * The constructor for the Tree instance
     */
    public Tree(){
        this.rootNode = new Node("Root", "");
        this.currentNode = this.rootNode;
    }

    /**
     * The constructor for the Tree instance
     * @param rootNode A node instance to set at the root node
     */
    public Tree( Node rootNode ){
        this.rootNode = rootNode;
        this.currentNode = this.rootNode;
    }

    /**
     * Gets the current traversed node
     * @return The current traversed Node
     */
    public Node getCurrentNode(){
        return this.currentNode;
    }

    /**
     * Gets the root node
     * @return A Node which is stored as the root of the Tree instance
     */
    public Node getRootNode(){
        return this.rootNode;
    }

    /**
     * Resets the current traversed node to the root node
     */
    public void resetTraversal(){
        this.currentNode = this.rootNode;
    }

    /**
     * Traverses the tree
     * @param path The integer value of the path to traverse down
     * @return The Node instance that the tree has traversed to
     * @throws Exception
     */
    public Node traverse( int path ) throws Exception {
        if ( path < this.currentNode.getChildren().size() ){
            Node newNode = this.currentNode.getChildren().get( path );
            this.currentNode = this.currentNode.getChildren().get( path );
            return newNode;
        } else {
            throw new Exception("Cannot traverse path; Path ID out of range!");
        }
    }

}
