package streamingsystems02;

/**
 * The implementation of a moving item that was given as part of the assignment.
 */
public interface MovingItem {
    /**
     * @return The name of the moving item.
     */
    String getName();

    /**
     * @return The location of the moving item.
     */
    int[] getLocation();

    /**
     * @return The number of moves the moving item has made.
     */
    int getNumberOfMoves();

    /**
     * @return The value of the moving item.
     */
    int getValue();


}
