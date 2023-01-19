package streamingsystems05.DataRepresentation;

import java.io.Serializable;

/**
 * Represents a cell in the taxi data grid.
 */
@SuppressWarnings("unused")
public class GeoCellIndex implements Serializable {
    // CONSTANT


    /**
     * The first cell index in the grid (top left).
     */
    public static final GeoCellIndex firstCellIndex = new GeoCellIndex(1, 1);
    /**
     * The last cell index in the grid (bottom right).
     */
    public static final GeoCellIndex lastCellIndex = new GeoCellIndex(300, 300);

    /**
     * The latitude value needed to move 500 meters east.
     */
    public static final double latitude500MetersEastDelta = 0.005986;
    /**
     * The longitude value needed to move 500 meters south.
     */
    public static final double longitude500MetersSouthDelta = 0.004491556;

    /**
     * The center coordinated of the first cell in the grid.
     */
    public static final LatLong firstCellCenterCoords = new LatLong(41.474937, -74.913585);
    /**
     * The LatLong coordinates of the first cell in the grid (top left).
     */
    public static final LatLong firstCellTopLeftCoords =
            new LatLong(firstCellCenterCoords.lat() + longitude500MetersSouthDelta / 2,
                        firstCellCenterCoords.lng() - latitude500MetersEastDelta / 2);

    /**
     * How wide a cell is in meters.
     */
    public static final int cellWidthMeters = 500;
    /**
     * How tall a cell is in meters.
     */
    public static final int cellHeightMeters = 500;

    /**
     * The x-axis index of the cell.
     */
    public final int xIndex;
    /**
     * The y-axis index of the cell.
     */
    public final int yIndex;

    /**
     * @param xIndex The x-axis index of the new cell.
     * @param yIndex The y-axis index of the new cell.
     */
    public GeoCellIndex(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    /**
     * @param latLongInput The LatLong to convert to a GeoCellIndex.
     */
    public GeoCellIndex(LatLong latLongInput) {
        this.xIndex = getCellIndexNumberByLongitudeValue(latLongInput.lng());
        this.yIndex = getCellIndexNumberByLatitudeValue(latLongInput.lat());
    }

    /**
     * @param latitudeValue The latitude value to convert to a cell index y value.
     * @return The cell index y value.
     * @throws IllegalArgumentException If the latitude value is out of range.
     */

    public static int getCellIndexNumberByLatitudeValue(double latitudeValue) throws IllegalArgumentException {

        if (latitudeValue > firstCellTopLeftCoords.lat() ||
                latitudeValue < firstCellTopLeftCoords.lat() - 301 * longitude500MetersSouthDelta) {
            throw new IllegalArgumentException("Latitude value is out of range");
        }

        var unroundedLatCellIndex =
                Math.abs(latitudeValue - firstCellTopLeftCoords.lat()) / longitude500MetersSouthDelta;
        return (int)Math.floor(unroundedLatCellIndex) + 1;
    }

    /**
     * @param longitudeValue The longitude value to convert to a cell index x value.
     * @return The cell index x value.
     * @throws IllegalArgumentException If the longitude value is out of range.
     */
    public static int getCellIndexNumberByLongitudeValue(double longitudeValue) throws IllegalArgumentException {

        if (longitudeValue < firstCellTopLeftCoords.lng() ||
                longitudeValue > firstCellTopLeftCoords.lng() + 301 * latitude500MetersEastDelta) {
            throw new IllegalArgumentException("Longitude value is out of range");
        }

        var unroundedLngCellIndex = (longitudeValue - firstCellTopLeftCoords.lng()) / latitude500MetersEastDelta;
        return (int)Math.floor(unroundedLngCellIndex) + 1;
    }

    public String toString() {
        return xIndex + "." + yIndex;
    }

}
