package streamingsystems;

import java.util.stream.IntStream;

/**
 * Helper methods.
 */
public class Helpers {
    /**
     * Adds the two location vectors together.
     *
     * @param locationVector1 The first location vector.
     * @param locationVector2 The second location vector.
     * @return The two vectors added together.
     */
    public static int[] addArrays(int[] locationVector1,
                                  int[] locationVector2) {
        return IntStream.range(0, locationVector1.length)
                .mapToObj(i -> locationVector1[i] + locationVector2[i])
                .mapToInt(i -> i).toArray();
    }
}
