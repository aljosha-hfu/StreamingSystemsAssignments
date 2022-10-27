package streamingsystems;

import java.util.stream.IntStream;

public class Helpers {
    public static int[] addArrays(int[] location1, int[] location2) {
        return IntStream.range(0, location1.length).
                mapToObj(i -> location1[i] + location2[i]).mapToInt(i -> i).toArray();
    }
}
