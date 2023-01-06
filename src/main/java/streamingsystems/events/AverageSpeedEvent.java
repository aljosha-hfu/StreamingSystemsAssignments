package streamingsystems.events;

public class AverageSpeedEvent extends BaseEvent {

    public double getAverageSpeed() {
        return averageSpeed;
    }

    private final double averageSpeed;

    protected AverageSpeedEvent(int sensorId, double averageSpeed) {
        super(sensorId);
        this.averageSpeed = averageSpeed;
    }

    public String getEsperInsertIntoStatement() {
        return """
               insert into AverageSpeedEvent
               select id, avg(speed) as averageSpeed from SensorEvent.win:time(30 sec)
               group by id
               """;
    }
}
