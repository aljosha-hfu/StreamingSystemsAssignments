package streamingsystems;

import streamingsystems.DataRepresentation.Route;
import streamingsystems.DataRepresentation.TaxiTrip;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Builds a string representation of the top 10 routes.
 */
public class Top10RoutesStringBuilder {
    /**
     * @param top10Routes              The list of top 10 routes.
     * @param triggeringTrip           The trip that triggered the update.
     * @param triggerTimeInNanoseconds The time in nanoseconds when the update was triggered.
     * @return The string representation of the top 10 routes.
     */
    public static String buildTop10RoutesString(ArrayList<Route> top10Routes,
                                                TaxiTrip triggeringTrip,
                                                long triggerTimeInNanoseconds) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        stringJoiner.add(triggeringTrip.pickupDatetime().toString());
        stringJoiner.add(triggeringTrip.dropoffDatetime().toString());
        for (int i = 0; i < top10Routes.size(); i++) {
            stringJoiner.add(top10Routes.get(i).pickup() + "_" + i);
            stringJoiner.add(top10Routes.get(i).dropoff() + "_" + i);
        }
        stringJoiner.add(String.valueOf(System.nanoTime() - triggerTimeInNanoseconds));
        return stringJoiner.toString();
    }
}
