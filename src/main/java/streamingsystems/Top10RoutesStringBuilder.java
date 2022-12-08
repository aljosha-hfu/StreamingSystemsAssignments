package streamingsystems;

import streamingsystems.DataRepresentation.Route;
import streamingsystems.DataRepresentation.TaxiTrip;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Top10RoutesStringBuilder {
    public static String buildTop10RoutesString(ArrayList<Route> top10Routes, TaxiTrip triggeringTrip,
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
