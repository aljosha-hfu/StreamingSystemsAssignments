package streamingsystems;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Instant;
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

        PipelineOptions options = PipelineOptionsFactory.create();
//        options.setRunner(FlinkRunner.class);
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> pc = pipeline.apply(Create.of(cities)).setCoder(StringUtf8Coder.of());

        PCollection<String> pcWithTimestamp = // Note: this examples
                pc.apply(ParDo.of(new DoFn<String, String>() { // uses an anonymous
                    @DoFn.ProcessElement // class
                    public void processElement(@Element String input, OutputReceiver<String> output) {
                        // Get current time with type org.joda.time.Instant
                        Instant i = Instant.now();
                        // The input element gets a new timestamp with the current time
                        // and is passed to the output PCollection.
                        output.outputWithTimestamp(input, i);
                    }
                }));

        pcWithTimestamp.apply(ParDo.of(new DoFn<String, Void>() {
            @DoFn.ProcessElement
            public void processElement(@Element String input, @Timestamp Instant timestamp) {
                System.out.println("Element: " + input + " Timestamp: " + timestamp);
            }
        }));

        // Start the execution
        pipeline.run().waitUntilFinish();

        // print the PCollection

        logger.info("Terminating...");
    }
}