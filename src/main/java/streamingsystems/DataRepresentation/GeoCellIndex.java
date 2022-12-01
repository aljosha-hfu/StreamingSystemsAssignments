package streamingsystems.DataRepresentation;

public class GeoCellIndex {
    // CONSTANT
    public static final GeoCellIndex firstCellIndex = new GeoCellIndex(1,1);
    public static final GeoCellIndex lastCellIndex = new GeoCellIndex(300,300);

    public final int xIndex;
    public final int yIndex;

    public GeoCellIndex(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public String getStringRepresentation() {
        return xIndex + "." + yIndex;
    }
}
