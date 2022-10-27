package streamingsystems.QueryHandlingModel;

import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItem;
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.implemented.MovingItemImpl;
import streamingsystems.implemented.events.MovingItemCreatedEvent;
import streamingsystems.implemented.events.MovingItemDeletedEvent;
import streamingsystems.implemented.events.MovingItemMovedEvent;
import streamingsystems.implemented.events.MovingItemValueChangedEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class QueryModel {
    private static QueryModel INSTANCE;

    public static QueryModel getInstance(EventStore eventStore) {
        if(INSTANCE == null){
            INSTANCE = new QueryModel(eventStore);
        }
        return INSTANCE;
    }


    private final EventStore eventStore;
    private HashMap<String, MovingItemDTO> movingItemHashMap = new HashMap<>();

    private QueryModel(EventStore eventStore) {
        this.eventStore = eventStore;
        updateEventStore();
    }


    public void updateEventStore(){
       movingItemHashMap =  convertToMovingItemDTOMap(createEventStoreFromEvents(eventStore.getEventQueue()));
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(HashMap<String, MovingItemImpl> movingItemImplHashMap){
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach((k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }

    private HashMap<String, MovingItemImpl> createEventStoreFromEvents(LinkedBlockingQueue<Event> eventQueue) {
        HashMap<String, MovingItemImpl> map = new HashMap<>();
        eventQueue.forEach(event -> {
            String itemId = event.getId();
            if (event instanceof MovingItemValueChangedEvent movingItemValueChangedEvent) {
                MovingItemImpl movingItem = map.get(itemId);
                movingItem.setValue(movingItemValueChangedEvent.getNewValue());
                map.put(itemId, movingItem);
            } else if (event instanceof MovingItemCreatedEvent movingItemCreatedEvent) {
                map.put(itemId, new MovingItemImpl(movingItemCreatedEvent.getMovingItem()));
            } else if (event instanceof MovingItemMovedEvent movingItemMovedEvent) {
                MovingItemImpl movingItem = map.get(itemId);
                movingItem.addMoveToMoveCounter();
                movingItem.move(movingItemMovedEvent.getVector());
                map.put(itemId, movingItem);
            } else if (event instanceof MovingItemDeletedEvent) {
                map.remove(itemId);
            }

        });
        return map;

    }


    public MovingItem getMovingItemFromName(String name) {
        if(!movingItemHashMap.containsKey(name)){
            throw new NoSuchElementException("There is no Item with this specific name!");
        }
        return movingItemHashMap.get(name);
    }

    public Collection<MovingItemDTO> getAllMovingItems() {
        return this.movingItemHashMap.values();
    }
}
