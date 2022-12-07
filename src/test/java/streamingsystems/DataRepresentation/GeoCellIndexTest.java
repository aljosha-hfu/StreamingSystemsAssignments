package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoCellIndexTest {

    // LATITUDE
    @Test
    void testCellIndexNumberByLatitudeValueIndexOne() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLatitudeValue(GeoCellIndex.firstCellCenterCoords.lat + 0.3 * GeoCellIndex.latitude500MetersEastDelta));
    }

    @Test
    void testCellIndexNumberByLatitudeValueIndexFive() {
        assertEquals(5, GeoCellIndex.getCellIndexNumberByLatitudeValue(GeoCellIndex.firstCellCenterCoords.lat + 4.8 * GeoCellIndex.latitude500MetersEastDelta));
    }

    // LONGITUDE
    @Test
    void getCellIndexNumberByLongitudeValue() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLongitudeValue(GeoCellIndex.firstCellCenterCoords.lng + 0.3 * GeoCellIndex.longitude500MetersSouthDelta));
    }

    @Test
    void getCellIndexNumberByLongitudeValueIndexFive() {
        assertEquals(5, GeoCellIndex.getCellIndexNumberByLongitudeValue(GeoCellIndex.firstCellCenterCoords.lng + 4.8 * GeoCellIndex.longitude500MetersSouthDelta));
    }
}