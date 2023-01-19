package streamingsystems04;

import java.util.stream.IntStream;

/**
 * Helper classes for the application.
 */
public class Helpers {
    /**
     * Adds the two vectors.
     *
     * @param locationVector1 The first location vector.
     * @param locationVector2 The second location vector.
     * @return The vectors added together.
     */
    public static int[] addArrays(int[] locationVector1,
                                  int[] locationVector2) {
        return IntStream.range(0, locationVector1.length)
                .mapToObj(i -> locationVector1[i] + locationVector2[i])
                .mapToInt(i -> i).toArray();
    }
}
