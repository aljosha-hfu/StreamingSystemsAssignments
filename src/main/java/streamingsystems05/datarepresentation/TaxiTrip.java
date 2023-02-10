package streamingsystems05.datarepresentation;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @param medallion           The medallion of the taxi.
 * @param hackLicense         The hack license of the taxi.
 * @param pickupDatetime      The pickup date and time.
 * @param dropoffDatetime     The dropoff date and time.
 * @param tripTimeInSecs      The trip time in seconds.
 * @param tripDistanceInMiles The trip distance in miles.
 * @param pickupLocation      The pickup location.
 * @param dropoffLocation     The dropoff location.
 * @param paymentType         The payment type.
 * @param fareAmount          The fare amount.
 * @param surcharge           The surcharge.
 * @param taxDollars          The tax dollars.
 * @param tipDollars          The tip dollars.
 * @param tollsAmount         The tolls amount.
 * @param totalAmount         The total amount.
 */
public record TaxiTrip(String medallion, String hackLicense, Date pickupDatetime, Date dropoffDatetime,
                       int tripTimeInSecs, double tripDistanceInMiles, GeoCellIndex pickupLocation,
                       GeoCellIndex dropoffLocation, PaymentType paymentType, float fareAmount, float surcharge,
                       float taxDollars, float tipDollars, float tollsAmount, float totalAmount)
        implements Serializable {

    /**
     * Creates a new TaxiTrip from a string.
     *
     * @param inputString The input string.
     * @return The TaxiTrip.
     */
    public static TaxiTrip taxiTripFromString(String inputString) {
        String[] inputStringArray = inputString.split(",");
        return taxiTripFromStringList(inputStringArray);
    }

    /**
     * Creates new TaxiTrips from a string array.
     *
     * @param strings The input string array.
     * @return The TaxiTrip.
     */
    public static TaxiTrip taxiTripFromStringList(String[] strings) {
        String medallion = strings[0];
        String hackLicense = strings[1];
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            Date pickupDatetime = dateFormat.parse(strings[2]);
            Date dropoffDatetime = dateFormat.parse(strings[3]);
            int tripTimeInSecs = Integer.parseInt(strings[4]);
            double tripDistanceInMiles = Double.parseDouble(strings[5]);

            // The order in the line string is long -> lat instead of lat -> long
            GeoCellIndex pickupLocation =
                    new GeoCellIndex(new LatLong(Double.parseDouble(strings[7]), Double.parseDouble(strings[6])));
            GeoCellIndex dropoffLocation =
                    new GeoCellIndex(new LatLong(Double.parseDouble(strings[9]), Double.parseDouble(strings[8])));

            PaymentType paymentType = PaymentType.valueOf(strings[10]);
            float fareAmount = Float.parseFloat(strings[11]);
            float surcharge = Float.parseFloat(strings[12]);
            float taxDollars = Float.parseFloat(strings[13]);
            float tipDollars = Float.parseFloat(strings[14]);
            float tollsAmount = Float.parseFloat(strings[15]);
            float totalAmount = Float.parseFloat(strings[16]);
            return new TaxiTrip(medallion, hackLicense, pickupDatetime, dropoffDatetime, tripTimeInSecs,
                                tripDistanceInMiles, pickupLocation, dropoffLocation, paymentType, fareAmount,
                                surcharge, taxDollars, tipDollars, tollsAmount, totalAmount);
        } catch (ParseException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Get the route from the TaxiTrip.
     * @return The route
     */
    public Route getRoute() {
        return new Route(pickupLocation, dropoffLocation);
    }

    @Override
    public String toString() {
        return "TaxiTrip{" + "medallion='" + medallion + '\'' + ", hackLicense='" + hackLicense + '\'' +
                ", pickupDatetime=" + pickupDatetime + ", dropoffDatetime=" + dropoffDatetime + ", pickupLocation=" +
                pickupLocation + ", dropoffLocation=" + dropoffLocation + '}';
    }
}
