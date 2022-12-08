package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxiTripTest {

    @Test
    void taxiTripFromValidString1() {
        assertNotNull(TaxiTrip.taxiTripFromString(
                "F899990AB4A6582D6A5F21265725216E,474E00C0A8C9619AF6BE05D88A15F78A,2013-01-01 00:05:00,2013-01-01 00:08:00,180,0.40,-73.955826,40.779598,-73.949593,40.777012,UNK,4.00,0.50,0.50,1.00,0.00,6.00"));
    }

    @Test
    void taxiTripFromValidString2() {
        assertNotNull(TaxiTrip.taxiTripFromString(
                "5F78CC6D4ECD0541B765FECE17075B6F,B7567F5BFD558C665D23B18451FE1FD1,2013-01-01 00:00:00,2013-01-01 00:04:00,240,1.21,-73.973000,40.793140,-73.981453,40.778465,CRD,6.00,0.50,0.50,1.30,0.00,8.30"));
    }

    @Test
    void taxiTripFromValidString3() {
        assertNotNull(TaxiTrip.taxiTripFromString(
                "3B4129883A1D05BE89F2C929DE136281,7077F9FD5AD649AEACA4746B2537E3FA,2013-01-01 00:01:00,2013-01-01 00:03:00,120,0.61,-73.987373,40.724861,-73.983772,40.730995,CRD,4.00,0.50,0.50,0.00,0.00,5.00"));
    }

    @Test
    void taxiTripFromInvalidString() {
        assertNull(TaxiTrip.taxiTripFromString(
                "03A8081017EE702DA6494085566665DA9837711811287BB91E7536A6970B0305,2013-01-01 00:06:00,2013-01-01 00:09:00,180,0.35,-73.990852,40.746197,-73.994972,40.748714,CSH,4.00,0.50,0.50,0.00,0.00,5.00"));
    }

    @Test
    void taxiTripFromStringWithZeroLatLngValues() {
        assertNull(TaxiTrip.taxiTripFromString(
                "22D70BF00EEB0ADC83BA8177BB861991,3FF2709163DE7036FCAA4E5A3324E4BF,2013-01-01 00:02:00,2013-01-01 00:02:00,0,0.00,0.000000,0.000000,0.000000,0.000000,CSH,27.00,0.00,0.50,0.00,0.00,27.50"));
    }

    @Test
    void taxiTripFromEmptyString() {
        assertThrows(IndexOutOfBoundsException.class, () -> TaxiTrip.taxiTripFromString(""));
    }
}