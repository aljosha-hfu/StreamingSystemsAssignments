package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoCellIndexTest {

    // LATITUDE
    @Test
    void testCellIndexNumberByLatitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat - 0.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex5() {
        assertEquals(5, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat - 5.4 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex255() {
        assertEquals(255, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat - 255.8 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLatitudeValue(
                GeoCellIndex.firstCellCenterCoords.lat - 300.8 * GeoCellIndex.latitude500MetersEastDelta));
    }

    // LONGITUDE
    @Test
    void getCellIndexNumberByLongitudeValueIndex1() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng + 0.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex8() {
        assertEquals(8, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng + 8.2 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex197() {
        assertEquals(197, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng + 197.2 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndex300() {
        assertEquals(300, GeoCellIndex.getCellIndexNumberByLongitudeValue(
                GeoCellIndex.firstCellCenterCoords.lng + 300.2 * GeoCellIndex.longitude500MetersSouthDelta));
    }
}