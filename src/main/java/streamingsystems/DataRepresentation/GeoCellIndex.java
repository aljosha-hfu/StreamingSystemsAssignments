package streamingsystems.DataRepresentation;

import java.io.Serializable;

public class GeoCellIndex implements Serializable {
    // CONSTANT


    public static final GeoCellIndex firstCellIndex = new GeoCellIndex(1, 1);
    public static final GeoCellIndex lastCellIndex = new GeoCellIndex(300, 300);

    // CONSTANTS
    public static final double latitude500MetersEastDelta = 0.005986;
    public static final double longitude500MetersSouthDelta = 0.004491556;

    public static final LatLong firstCellCenterCoords = new LatLong(41.474937, -74.913585);
    public static final LatLong firstCellTopLeftCoords =
            new LatLong(firstCellCenterCoords.lat() + latitude500MetersEastDelta / 2,
                    firstCellCenterCoords.lng() - longitude500MetersSouthDelta / 2);

    public static final int cellWidthMeters = 500;
    public static final int cellHeightMeters = 500;

    // STATIC
    // FIELDS
    public final int xIndex;
    public final int yIndex;

    public GeoCellIndex(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public GeoCellIndex(LatLong latLongInput) {
        this.xIndex = getCellIndexNumberByLatitudeValue(latLongInput.lat());
        this.yIndex = getCellIndexNumberByLongitudeValue(latLongInput.lng());
    }

    public static int getCellIndexNumberByLatitudeValue(double latitudeValue) {

        if (latitudeValue > firstCellTopLeftCoords.lat() ||
                latitudeValue < firstCellTopLeftCoords.lat() - 301 * latitude500MetersEastDelta) {
            throw new IllegalArgumentException("Latitude value is out of range");
        }

        var unroundedLatCellIndex = Math.abs(latitudeValue - firstCellTopLeftCoords.lat()) / latitude500MetersEastDelta;
        return (int) Math.floor(unroundedLatCellIndex);
    }

    public static int getCellIndexNumberByLongitudeValue(double longitudeValue) {

        if (longitudeValue < firstCellTopLeftCoords.lng() ||
                longitudeValue > firstCellTopLeftCoords.lng() + 301 * longitude500MetersSouthDelta) {
            throw new IllegalArgumentException("Longitude value is out of range");
        }

        var unroundedLngCellIndex = (longitudeValue - firstCellTopLeftCoords.lng()) / longitude500MetersSouthDelta;
        return (int) Math.floor(unroundedLngCellIndex);
    }

    public String toString() {
        return "[" + xIndex + "." + yIndex + "]";
    }
}
