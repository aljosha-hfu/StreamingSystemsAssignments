package streamingsystems.events;

public class TrafficJamEvent extends BaseEvent {
    public double getAverageSpeed() {
        return averageSpeed;
    }

    private final double averageSpeed;
    private final double minSpeed;


    public TrafficJamEvent(int sensorId, double averageSpeed, double minSpeed) {
        super(sensorId);
        this.averageSpeed = averageSpeed;

        this.minSpeed = minSpeed;
    }

    public double getMinSpeed() {
        return minSpeed;
    }
}
