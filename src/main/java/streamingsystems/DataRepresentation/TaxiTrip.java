package streamingsystems.DataRepresentation;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public record TaxiTrip(String medallion, String hackLicense, Date pickupDatetime, Date dropoffDatetime,
                       int tripTimeInSecs, double tripDistanceInMiles, GeoCellIndex pickupLocation,
                       GeoCellIndex dropoffLocation, PaymentType paymentType, float fareAmount, float surcharge,
                       float taxDollars, float tipDollars, float tollsAmount, float totalAmount)
        implements Serializable {

    public static TaxiTrip taxiTripFromString(String inputString) {
        String[] inputStringArray = inputString.split(",");
        return taxiTripFromStringList(inputStringArray);
    }

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
            ;
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
                    tripDistanceInMiles, pickupLocation, dropoffLocation, paymentType, fareAmount, surcharge,
                    taxDollars, tipDollars, tollsAmount, totalAmount);
        } catch (ParseException | IllegalArgumentException e) {
            return null;
        }
    }

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
