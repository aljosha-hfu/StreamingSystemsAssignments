package streamingsystems06;

/**
 * A class for starting Kafka test data generation and parsing thereof.
 */
public class Main {

    /**
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new TestDataGeneratorThread().start();
        new DataParser().parse();
    }

}

