package streamingsystems.events;

public class AverageSpeedEvent extends BaseEvent {

    public double getAverageSpeed() {
        return averageSpeed;
    }

    private final double averageSpeed;

    public AverageSpeedEvent(int sensorId, double averageSpeed) {
        super(sensorId);
        this.averageSpeed = averageSpeed;
    }
}
