# General Task Remarks

## Design decisions

- Test-Driven Design (TDD) for automatic feedback on any changes
    - Tests implemented with JUnit
- GitHub Actions pipeline automatically verifies Maven build with automatic JUnit testing

## Apache Kafka

We are starting Kafka in docker with a custom `docker-compose.yml` file.
This makes cross-platform development easier as each developer can start a Kafka setup at whim.

The `EventStore` class sends the events into Kafka with a producer, whereas the `QueryModel` class consumes those
messages with a KafkaConsumer.

### Domain Model refactoring

TODO

## Config file

We're storing our application configuration inside `src/main/resources/app.config` again, using the `ConfigManager`
class as a way to access its values.
Even though we're using this class as a Singleton (which means its values should not be able to change), we're still
caching the config Strings in the Producer and Consumer classes to ensure they stay the same during runtime.
This ensures isolation of the Producer and Consumer classes from config changes.