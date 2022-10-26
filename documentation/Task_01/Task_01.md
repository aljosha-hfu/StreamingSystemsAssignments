# Task 01

- Considerations: Should we implement the commands directly inside the `CommandsImpl` class or as subclasses of an
  abstract `Command`class with an abstract `handle()` method which executes the command?
    - We decided on using the latter since it allows for more code segregation and separation of concerns.

## Questions

- Are `id` and `name` the same thing?

### Alternative way to handle creation of Map of MovingItems in QueryModel
Instead of always checking each eventtype and then doing the logic in the query model, one could bind the logic to the event, like it´s done in the commands.
This could be achieved by adding a method to the Event superclass and implement it in every subclass with the corresponding logic.
The QueryModel does not need to know the logic then.

#### Example code:
```Java
public abstract class Event {
  ...
    abstract public MovingItem apply(MovingItem movingItem);
}
```
```Java
public class MovingItemValueChangedEvent extends Event {
  private String id;
  private int newValue;

  public MovingItemValueChangedEvent(String id, int newValue) {
    ...
  }


  @Override
  public MovingItem apply(MovingItem movingItem) {
    return movingItem.setValue(newValue);
  }
}
```
```Java

public class QueryModel {
  ...
  private HashMap<String, MovingItem> createEventStoreFroEvents(LinkedBlockingQueue<Event> eventQueue) {
    ...
    eventQueue.forEach(event -> {
      ...
      event.apply()
    });

  }
}
```

## TODO

- Add JUnit für unit tests
