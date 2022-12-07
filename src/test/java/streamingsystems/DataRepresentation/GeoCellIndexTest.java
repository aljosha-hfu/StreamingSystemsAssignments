package streamingsystems.DataRepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoCellIndexTest {

    @Test
    void testCellIndexNumberByLatitudeValueIndexOne() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLatitudeValue(GeoCellIndex.firstCellCenterCoords.lat));
    }

    @Test
    void getCellIndexNumberByLongitudeValue() {
        assertEquals(1, GeoCellIndex.getCellIndexNumberByLongitudeValue(GeoCellIndex.firstCellCenterCoords.lng));
    }
}