package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeoCellIndexTest {

    // LATITUDE
    @Test
    void cellIndexNumberByLatitudeValueNegativeOutsideGrid() {
        assertThrows(IllegalArgumentException.class, () -> GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() + 2));
    }

    @Test
    void cellIndexNumberByLatitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() - 0.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void cellIndexNumberByLatitudeValueIndex5() {
        assertEquals(5, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() - 4.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void cellIndexNumberByLatitudeValueIndex255() {
        assertEquals(255, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() - 254.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void cellIndexNumberByLatitudeValueIndex420ShouldHaveException() {
        assertThrows(IllegalArgumentException.class, () -> GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() - 419.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void cellIndexNumberByLatitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat() - 299.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    // LONGITUDE
    @Test
    void cellIndexNumberByLongitudeValueNegativeOutsideGrid() {
        assertThrows(IllegalArgumentException.class, () -> GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() - 2));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 0.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex8() {
        assertEquals(8, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 7.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex197() {
        assertEquals(197, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 196.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 299.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex298() {
        assertEquals(298, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 297.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void cellIndexNumberByLongitudeValueIndex420ShouldHaveException() {
        assertThrows(IllegalArgumentException.class, () -> GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng() + 419.3 * GeoCellIndex.latitude500MetersEastDelta));
    }
}