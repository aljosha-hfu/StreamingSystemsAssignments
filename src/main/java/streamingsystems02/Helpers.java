package streamingsystems02;

import java.util.stream.IntStream;

/**
 * Helpers methods for the streaming systems project.
 */
public class Helpers {
    /**
     * Adds the two given vector arrays together.
     * @param locationVector1 The first location vector.
     * @param locationVector2 The second location vector.
     * @return The result of the addition of the two location vectors.
     */
    public static int[] addArrays(int[] locationVector1, int[] locationVector2) {
        return IntStream.range(0, locationVector1.length)
                .mapToObj(i -> locationVector1[i] + locationVector2[i]).mapToInt(i -> i)
                .toArray();
    }
}
