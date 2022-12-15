package streamingsystems;

public class Main {

    public static void main(String[] args) {
        new TestDataGeneratorThread().start();
        new DataParser().parse();
    }

}

