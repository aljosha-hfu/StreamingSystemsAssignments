package streamingsystems.events;

public class TrafficJamEvent extends BaseEvent {
    protected TrafficJamEvent(int sensorId) {
        super(sensorId);
    }
}
