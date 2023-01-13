package streamingsystems.DataRepresentation;

/**
 * @param pickup The pickup location.
 * @param dropoff The dropoff location.
 */
public record Route(GeoCellIndex pickup, GeoCellIndex dropoff) {
}