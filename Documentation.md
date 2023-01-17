<!-- Disable multiple same headings warning -->
<!-- markdownlint-disable MD024 -->

# Documentation of _Streaming Systems_ Tasks

✅ Tasks by Bastian Hodapp and Aljosha Vieth

📅 Wintersemester 2022/2023

🏫 HFU Furtwangen University

## General Task Remarks

### Design decisions

- Test-Driven Design (TDD) for automatic feedback on any changes
  - Tests implemented with JUnit
- GitHub Actions pipeline automatically verifies Maven build with automatic JUnit testing
- We're using IntelliJ IDEA as our IDE
  - Code Analysis is performed by IntelliJ IDEAs built-in code analysis tool
  - Formatting is performed by IntelliJ IDEAs built-in code formatter

### Config files

For some tasks, we're storing the application's configuration inside `src/main/resources/app.config`.
The `ConfigManager` class is used to access these values.
The config values and thereby also the `ConfigManager` change from task to task.

Even though we're using this class as a Singleton (which means its values should not be able to change), we're still caching the config values in the classes they're used in to ensure they stay the same during runtime.

This approach also allows us to test for non-null configuration values in JUnit tests.

#### Development

We're using the baseline file `app-example.config` file to configure the application environment variables for development.
It contains values that correspond to the default values of the services we're using (Kafka, RabbitMQ, etc.) that are defined in the `docker-compose.yml` files.

#### GitHub Actions

We're using the `app-pipeline.config` file to configure the application environment variables for usage with GitHub Actions pipelines.
That file also contains values that correspond to the default values of the services we're using that are defined in the `docker-compose.yml` files.
For now, the two files `app-example.config` and `app-pipeline.config` are (mostly) identical, but keeping them separate is a good idea in case we need to change the values specifically for pipelines or development in the future.

### `docker-compose`

We're using `docker-compose` to start all our services (Kafka, RabbitMQ, etc.) and to connect them to each other.
The `docker-compose.yml` file is located in the root directory of each of the branches the project.

### GitHub Actions

We're using GitHub Actions to automatically build and test the different tasks.
We're using branches to separate the different tasks.
This makes it easier to keep the different tasks separate, keep the code clean and define different pipelines for each task.

On the project [main page](https://github.com/AljoshaVieth/StreamingSystemsAssignments), you can see the status of the different pipelines.

#### Port mappings

For each service we're mapping the ports to the host machine to make it easier to access the services from the Java program.
For eaxmple, the Kafka service is mapped to port `9092` on the host machine, so we can connect to it with `localhost:9092` from Java.

#### Additional configs

We're also using the `docker-compose.yml` file to configure the services. For example, we're setting the `KAFKA_CFG_ADVERTISED_LISTENERS` environment variable for the Kafka service to make set this setting in the Kafka server.

## Task 01

- Considerations: Should we implement the commands directly inside the `CommandsImpl` class or as subclasses of an abstract `Command`class with an abstract `handle()` method which executes the command?
  - We decided on using the latter since it allows for more code segregation and separation of concerns.

### Alternative way to handle creation of Map of MovingItems in QueryModel

Instead of always checking each eventtype and then doing the logic in the query model, one could bind the logic to the event, like it´s done in the commands.
This could be achieved by adding a method to the Event superclass and implement it in every subclass with the corresponding logic.
The QueryModel does not need to know the logic then.

#### Example code

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

## Task 02

### Remove MovingItem after n (= 20) moves

To achieve this behavior, we implemented a method in the domain model to check if the item has already moved n-1 times before.
Then the apply() method of the MoveItemCommand class checks whether the number uses this method to determine if the item should be processed or deleted.

**DomainModel:**

```Java
private final int maximumMoves = 20;

public boolean itemHasReachedMaximumMoves(String id){
        return getNumberOfMovesForMovingItemName(id) >= maximumMoves-1;
        }
```

**MoveItemCommand:**

```Java
@Override
public void handle(){
    if(DomainModel.getInstance().itemHasReachedMaximumMoves(id)){
    DomainModel.getInstance().removeMovingItemNameFromModel(id);
    EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
    return;
}
// Else process event...
}
```

### Check if MovingItem gets moved to a position where another MovingItem is already present and if so, delete the MovingItem that has been there before

We wrote methods in the DomainModel to check if a MovingItem exists on a position and to get a MovingItem at a specific position.
**DomainModel:**

```Java
public boolean itemExistsOnPosition(int[] position) {
        // Using stream and filter instead of HashMap.containsValue() because the values are int[] and they cannot be compared this way
        long numberOfItemsAtPosition = movingItemsPositions.values().stream().filter(pos -> Arrays.equals(pos, position)).count();
        return numberOfItemsAtPosition > 0;
}

public String getItemNameForPosition(int[] positionToFind) {
        Optional<String> foundItemName = movingItemsPositions.entrySet().stream().filter(entry -> Arrays.equals(entry.getValue(), positionToFind))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet())
        .stream()
        .findFirst();
        return foundItemName.orElse(null);
}
```

We then check in the MoveItemCommand if there is another MovinItem at the position to move to and if so, delete it and move the new MovingItem there.

To calculate a new position when moving a MovingItem, we wrote a helper class with a helper method.

**Helpers:**

```Java
public class Helpers {
    public static int[] addArrays(int[] location1, int[] location2) {
        return IntStream.range(0, location1.length).
                mapToObj(i -> location1[i] + location2[i]).mapToInt(i -> i).toArray();
    }
}
```

**MoveItemCommand:**

```Java
int[] newMovingItemPosition = Helpers.addArrays(DomainModel.getInstance().getPositionForMovingItemName(id), vector);


if (DomainModel.getInstance().itemExistsOnPosition(newMovingItemPosition)) {
    String existingMovingItemAtNewPositionId = DomainModel.getInstance().getItemNameForPosition(newMovingItemPosition);
    DomainModel.getInstance().removeMovingItemNameFromModel(existingMovingItemAtNewPositionId);
    EventStore.getInstance().addEvent(new MovingItemDeletedEvent(existingMovingItemAtNewPositionId));
} else {
    EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
    DomainModel.getInstance().moveMovingItem(id, vector);
    DomainModel.getInstance().incrementNumberOfMovesForMovingItemNameByOne(id);
}
```

## Task 03

### Which Message Queueing system to use?

We considered using Apache ActiveMQ, but since there was no ready-made Docker image in sight, we decided on using RabbitMQ instead, since we already used it in Mr. Betermieux's lectures.
RabbitMQ has an official [Docker image](https://hub.docker.com/_/rabbitmq) and is easy to set up.

### RabbitMQ config

We are starting RabbitMQ in docker with a custom `docker-compose.yml` file.
The class `RabbitMQConnectionManager` handles the connection to RabbitMQ and the `EventStore` class publishes the events into a RabbitMQ channel with its `addEvent()` method.
The `QueryModel` class consumes those messages from the channel using the `basicGet()` method.

## Task 04

## Apache Kafka

We are starting Kafka in docker with a custom `docker-compose.yml` file.
This makes cross-platform development easier as each developer can start a Kafka setup at whim.

The `EventStore` class sends the events into Kafka with a producer, whereas the `QueryModel` class consumes those
messages with a KafkaConsumer.

## `DomainModel` & `QueryModel` tradeoff

Our `DomainModel` and `QueryModel` classes use the same codebase to deserialize Lists of Events into a List of
MovingItems with their current states.
As it stands, this solution doesn't scale very well, but guarantees consistency among the order of events.
Another approach to this would be to split up the events into different partitions or even into different topics.
This would improve scalability by a lot, but negates the consistency advantage of our implementation.

The `KafkaExtractor` class gets a list of `Event` data from Kafka and parses them into a List of `Event`s.
The `MovingItemListGenerator` class takes this list of `Event`s and generates a "current state" list of `MovingItem`s to
work with in the `DomainModel` and `QueryModel`.

### Adding a List of events to the apply method of each event

In prior tasks, the events apply methods did not need any arguments.
However, some events need information about the history of their moving item.
In the past, this information got simply accessed by accessing the global list of all events, stored in the `QueryModel`.
(The `QueryModel` is a singleton)
Now, that we are using Apache Kafka, there is a separation between the `QueryModel` and the data from Kafka.
To disconnect these two parts and make the events work without the Query model, each event now takes
a `HashMap<String, MovingItemImpl> movingItemImplHashMap`, hence they now have access to the history of events.

#### Inheritance

Actually, only the `MovingItemMovedEvent` and the `MovingItemValueChangedEvent` need the event history. However, all
events inherit from the base event interface, hence the other events also have a reference to the list in their `apply()` method.

## Task 05

### `GeoCell`s

To represent the grid of cells in the city, we used a class called `GeoCell`.
It takes Lat and Long parameters and then returns.

### PaymentTypes

We created an Enum `PaymentType` to store the different payment types.
In the documentation, it is stated, that the payment type is either Cash (CSH) or CreditCard (CRD).

However, there are also entries that list UNK as PaymentType, so we created a third option, Unknown (UNK).

Examples for the usage of UNK are line 74 and 516 of sample_data.csv:

line 74 of sample_data.csv:

```csv
F899990AB4A6582D6A5F21265725216E,474E00C0A8C9619AF6BE05D88A15F78A,2013-01-01 00:05:00,2013-01-01 00:08:00,180,0.40,-73.955826,40.779598,-73.949593,40.777012,UNK,4.00,0.50,0.50,1.00,0.00,6.00
```

To represent the wording of the challenge, the enum values also have a name in form of a String.

````java
public enum PaymentType {
    CRD("CREDIT_CARD"),
    CSH("CASH"),
    UNK("UNKNOWN");

    public final String name;

    PaymentType(String name) {
        this.name = name;
    }
}

````

## Task 06

### Steps

#### TestDataGenerator

Our `TestDataGenerator` class uses a Java `Random` class.
It can be used with a given random seed so that the results can be reproducible if needed.

The test data is generated using the parameters described in the task description.
The generated data points are then written to Kafka.

#### DataParser

The `DataParser` class reads the data points from Kafka using a `KafkaIO` adapter.
The `KafkaIO` adapter uses the `IntegerDeserializer` and `StringDeserializer` classes to deserialize the data points.
It is important to set the `.withStartReadTime()` parameter to a date in the past,
e.g. `LocalDate.parse("1990-01-01").toDateTimeAtCurrentTime().toInstant())` because that's the point in time from where
the data points are read.

Using the `KafkaIO` adapter, we can now read the data points from Kafka into a `PCollection` of `KV<Integer, String>`
objects.
The key (`Integer`) is the sensor ID of the data point and the value are generated sensor values (As a list of valued
formatted into a `String`) of the data point.

We then use `.apply(Window.into(FixedWindows.of(Duration.standardSeconds(WINDOW_SIZE_SECONDS))))`
and `.apply(Mean.perKey())` to calculate the mean value of the sensor values for each sensor ID in a window
of `WINDOW_SIZE_SECONDS` (30s in our case, as described in the task) seconds.

#### Starting both classes

The `TestDataGeneratorThread` class starts the `TestDataGenerator` class in a separate thread so that it can run in parallel with the `DataParser` class.
The `Main` class first starts this thread and then starts the `DataParser` class afterwards.

### Verification of solution correctness

The solution is verified by comparing the results of the `Mean.perKey()` operation with the expected results.
Since we use a _fixed random seed_, the results are reproducible.
Therefore, we can compare the results with the expected results.

Example output:

```shell
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 0; 30s avg speed: 79.95275762958181
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 3; 30s avg speed: 78.1525645793246
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 1; 30s avg speed: 78.53137264961234
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 4; 30s avg speed: 79.550183942172
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 2; 30s avg speed: 81.06412492735669
```

### TODO

- [ ] Add test cases for the `DataParser` class

## Task 07

This task didn't need a `docker-compose.yml` file, because we didn't need to run any services.
The test data generator (similar to the last task) and the data parser (using Esper) were run locally.

### Steps

#### Test data generator

We're using a test data generator similar to the one used in Task 06 as a basis for Task 07.
However, we added a feature where, after a fixed time, a traffic jam is simulated by lowering the speeds of all sensors of a random sensor ID.
To insert the data points into Esper, we use the `getEventService()` method of the `EPRuntime` class and then use the `getEventSender()` method to get a `EventSender` object.
This method yields a `EventSender` object, which we can use to send events to Esper.
As opposed to task 06, we're not generating a string of comma-separated values, but instead single key-value pairs that are inserted into Esper using the `sendEvent()` method of the `EventSender` class.

#### Data Parser

The `DataParser` class uses Esper to parse the data points.

#### Esper queries

The Esper queries are defined in the `EsperClient` class, as well.

To keep the lines of code needed to define the queries short, we used the following syntax:

```java
public static String getEsperStatementString() {
        int averagingWindowSeconds = 5;
        int trafficJamCheckingWindow = 30;
        float trafficJamThreshold = 0.6f;
        return String.format(Locale.ENGLISH,
        """
                             // Event: getSensorsEvents
                             @name('getSensorsEvents')
                             SELECT sensorId, speed
                             FROM SensorEvent
                             WHERE speed >= 0;
                                            
                             // Event: getAverageSpeedEvents
                             @name('getAverageSpeedEvents')
                             INSERT INTO AverageSpeedEvent
                             SELECT sensorId, avg(speed) AS averageSpeed
                             FROM SensorEvent#time_batch(%d sec)
                             WHERE speed >= 0
                             GROUP BY sensorId;
                                                          
                             // Event: getTrafficJamEvents (fire if for one sensor the average speed decreased by 10 percent in the last 15 seconds)
                             // IDEA: Use a timed window and check if the minimum speed in the window is 10 percent lower than the average speed
                             @name('getTrafficJamEvents')
                             INSERT INTO TrafficJamEvent
                             SELECT sensorId, avg(averageSpeed) AS averageSpeed, min(averageSpeed) AS minSpeed
                             FROM AverageSpeedEvent#time(%d sec)
                             GROUP BY sensorId
                             HAVING min(averageSpeed) <= avg(averageSpeed) * %f
                             """,
        averagingWindowSeconds,
        trafficJamCheckingWindow,
        trafficJamThreshold
        );
        }
```
We used Locale.ENGLISH because on our machines the formatter formatted the float values with a `,` instead of a `.` which caused a crash.

The `getEsperStatementString()` method returns a string that contains the Esper query.
We can use `//` to [add comments to the query](https://esper.espertech.com/release-6.0.1/esper-reference/html/epl_clauses.html#epl-syntax-comments).

### TODO

- [ ] Test cases
- [ ] Change TestDataGenerator to throw out events that have negative speeds
