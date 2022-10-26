package streamingsystems.QueryHandlingModel;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItem;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class QueryModel {
    private static final QueryModel singletonInstance = new QueryModel();

    private QueryModel() {
        System.out.println("QueryModel Instance created.");
    }

    public static QueryModel getInstance() {
        return singletonInstance;
    }


    private HashMap<String, MovingItem> movingItemHashMap = new HashMap<>();

    private HashMap<String, MovingItem> createEventStoreFroEvents(LinkedBlockingQueue<Event> eventQueue) {
        HashMap<String, MovingItem> map = new HashMap<>();
        eventQueue.forEach(event -> {
            switch (event) {

            }
        });

    }
}
