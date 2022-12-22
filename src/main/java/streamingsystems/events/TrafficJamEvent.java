package streamingsystems.events;

public class TrafficJamEvent extends BaseEvent {
    public double getAverageSpeed() {
        return averageSpeed;
    }

    private final double averageSpeed;

    public double getPercentageDecrease() {
        return percentageDecrease;
    }

    private final double percentageDecrease;

    protected TrafficJamEvent(int sensorId, double averageSpeed, double percentageDecrease) {
        super(sensorId);
        this.averageSpeed = averageSpeed;
        this.percentageDecrease = percentageDecrease;

    }
}
