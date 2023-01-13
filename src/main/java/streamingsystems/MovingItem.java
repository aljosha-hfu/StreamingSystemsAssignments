package streamingsystems;

/**
 * The implementation of a moving item that was given as part of the assignment.
 */
public interface MovingItem {
    /**
     * @return The name of the moving item.
     */
    String getName();

    int[] getLocation();

    int getNumberOfMoves();

    int getValue();


}
