package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeoCellIndexTest {

    // LATITUDE
    @Test
    void testCellIndexNumberByLatitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() + 0.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex5() {
        assertEquals(5, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() + 4.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex255() {
        assertEquals(255, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() + 254.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex420ShouldHaveException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeoCellIndex.getCellIndexNumberByLatitudeValue(
                    GeoCellIndex.firstCellCenterCoords.lat() + 419.3 * GeoCellIndex.latitude500MetersEastDelta);
        });
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() + 299.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    // LONGITUDE
    @Test
    void getCellIndexNumberByLongitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 0.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex8() {
        assertEquals(8, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 7.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex197() {
        assertEquals(197, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 196.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 299.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex298() {
        assertEquals(298, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 297.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex420ShouldHaveException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeoCellIndex.getCellIndexNumberByLongitudeValue(
                    GeoCellIndex.firstCellCenterCoords.lng() + 419.3 * GeoCellIndex.longitude500MetersSouthDelta);
        });
    }
}