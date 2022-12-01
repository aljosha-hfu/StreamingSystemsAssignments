package streamingsystems.DataRepresentation;

import streamingsystems.Helpers.LatLong;
import streamingsystems.Helpers.PaymentType;

import java.io.Serializable;
import java.util.Date;

public record TaxiTrip(
        String medallion,
        String hackLicense,
        Date pickupDatetime,
        Date dropoffDatetime,
        int tripTimeInSecs,
        int tripDistanceInMiles,
        LatLong pickupLocation,
        LatLong dropoffLocation,
        PaymentType paymentType,
        float fareAmount,
        float surcharge,
        float taxDollars,
        float tipDollars,
        float tollsAmount,
        float totalAmount
) implements Serializable {
    @Override
    public String toString() {
        return "TaxiTrip{" +
                "medallion='" + medallion + '\'' +
                ", hackLicense='" + hackLicense + '\'' +
                ", pickupDatetime=" + pickupDatetime +
                ", dropoffDatetime=" + dropoffDatetime +
                ", pickupLocation=" + pickupLocation +
                ", dropoffLocation=" + dropoffLocation +
                '}';
    }
}
