package streamingsystems.DataRepresentation;

public class GeoCell {
    // CONSTANTS
    public final LatLong firstCellCenterCoords = new LatLong(41.474937, -74.913585);

    public final int cellWidthMeters = 500;
    public final int cellHeightMeters = 500;

    // FIELDS
    public final GeoCellIndex cellIndex;

    public GeoCell(GeoCellIndex cellIndex) {
        this.cellIndex = cellIndex;
    }
}
