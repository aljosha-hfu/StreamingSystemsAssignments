# Task 06

## Steps

### Test data generator

Our test data generator uses a Java `Random` class with a given random seed so that the results are reproducible.
The test data is generated using the given parameters described in the task description.
The generated data points are then written to Kafka.

## Verification of solution correctness

The solution is verified by comparing the results of the `Mean.perKey()` operation with the expected results.
Since we use a fixed random seed, the results are reproducible.
Therefore, we can compare the results with the expected results.

Example output:

```shell
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 0; 30s avg speed: 79.95275762958181
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 3; 30s avg speed: 78.1525645793246
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 1; 30s avg speed: 78.53137264961234
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 4; 30s avg speed: 79.550183942172
[direct-runner-worker] INFO streamingsystems.DataParser - Sensor ID: 2; 30s avg speed: 81.06412492735669
```

## TODO

- [ ] Test cases