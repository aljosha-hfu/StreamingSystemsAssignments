package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxiTripTest {

    @Test
    void taxiTripFromValidString() {
        assertNotNull(TaxiTrip.taxiTripFromString(
                "F899990AB4A6582D6A5F21265725216E,474E00C0A8C9619AF6BE05D88A15F78A,2013-01-01 00:05:00,2013-01-01 00:08:00,180,0.40,-73.955826,40.779598,-73.949593,40.777012,UNK,4.00,0.50,0.50,1.00,0.00,6.00"));
    }

    @Test
    void taxiTripFromInvalidString() {
        assertNull(TaxiTrip.taxiTripFromString(
                "03A8081017EE702DA6494085566665DA9837711811287BB91E7536A6970B0305,2013-01-01 00:06:00,2013-01-01 00:09:00,180,0.35,-73.990852,40.746197,-73.994972,40.748714,CSH,4.00,0.50,0.50,0.00,0.00,5.00"));
    }

    @Test
    void taxiTripFromEmptyString() {
        assertThrows(IndexOutOfBoundsException.class, () -> TaxiTrip.taxiTripFromString(""));
    }
}