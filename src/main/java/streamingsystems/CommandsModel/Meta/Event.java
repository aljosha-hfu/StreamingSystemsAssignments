package streamingsystems.CommandsModel.Meta;

import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import java.io.Serializable;

public abstract class Event implements Serializable {
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
