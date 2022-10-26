package streamingsystems.CommandsModel.Meta;

public abstract class Event {
    private final String id;

    public Event(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
