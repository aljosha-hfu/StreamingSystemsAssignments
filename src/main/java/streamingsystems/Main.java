package streamingsystems;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting...");

        // Create a Java Collection, in this case a list of strings.
        final List<String> cities = Arrays.asList("Furtwangen", "Tuttlingen", "VS-Schwenningen");
        // Create the pipeline
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);
        // Create the PCollection
        PCollection<String> pc = pipeline.apply(Create.of(cities)).setCoder(StringUtf8Coder.of());
        // Start the execution
        pipeline.run().waitUntilFinish();

        logger.info("Terminating...");
    }
}