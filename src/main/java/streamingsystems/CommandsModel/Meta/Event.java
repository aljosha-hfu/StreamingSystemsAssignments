package streamingsystems.CommandsModel.Meta;

import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

public abstract class Event {
    private final QueryModel queryModel = QueryModel.getInstance();
    protected final String id;

    public Event(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract MovingItemImpl apply();
}
