package streamingsystems.events;

public abstract class BaseEvent {
    public int getSensorId() {
        return sensorId;
    }

    int sensorId;

    public BaseEvent(int sensorId) {
        this.sensorId = sensorId;
    }
}
