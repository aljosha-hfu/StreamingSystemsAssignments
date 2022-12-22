package streamingsystems.events;

public abstract class BaseEvent {

    final int sensorId;

    protected BaseEvent(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getSensorId() {
        return sensorId;
    }
}
