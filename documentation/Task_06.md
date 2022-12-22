# Task 06

## Steps

### Test data generator

Our test data generator uses a Java `Random` class with a given random seed so that the results are reproducible.
The test data is generated using the given parameters described in the task description.
The generated data points are then written to Kafka.

### Kafka consumer / Data Parser

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

## Verification of solution correctness

The solution is verified by comparing the results of the `Mean.perKey()` operation with the expected results.
Since we use a fixed random seed, the results are reproducible.
Therefore, we can compare the results with the expected results.