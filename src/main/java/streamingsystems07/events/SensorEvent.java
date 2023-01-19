package streamingsystems07.events;

public class SensorEvent extends BaseEvent {
    private double speed;


    public SensorEvent(int sensorId, double speed) {
        super(sensorId);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
