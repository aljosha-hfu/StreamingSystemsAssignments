# Task 04
## Adding a List of events to the apply method of each event
In prior tasks, the events apply methods did not need any arguments.
However, some events need information about the history of their moving item.
In the past, this information got simply accessed by accessing the global list of all events, stored in the QueryModel.
(The QueryModel is a singleton)
Now, that we are using Apache Kafka, there is a separation between the QueryModel and the data from Kafka.
To disconnect these two parts and make the events work without the Query model, each event now takes a `HashMap<String, MovingItemImpl> movingItemImplHashMap`, hence they now have access to the history of events.
### Inheritance
Actually, only the `MovingItemMovedEvent` and the `MovingItemValueChangedEvent` need the event history. However, all events inherit from the base event interface, hence the other events also have the list in their apply method.




