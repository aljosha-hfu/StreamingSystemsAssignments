package streamingsystems;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.io.kafka.KafkaRecord;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
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

        PCollection<KafkaRecord<Integer, String>> kafkaRecords = pipeline.apply(KafkaIO.<Integer, String>read().withBootstrapServers(
                ConfigManager.INSTANCE.getKafkaUrl()).withTopic(ConfigManager.INSTANCE.getKafkaTopicName()).withKeyDeserializer(
                IntegerDeserializer.class).withValueDeserializer(StringDeserializer.class));

        // Print the collection
        kafkaRecords.apply(ParDo.of(new DoFn<KafkaRecord<Integer, String>, Void>() {
            @ProcessElement
            public void processElement(ProcessContext c) {
                KafkaRecord<Integer, String> kafkaRecord = c.element();
                logger.info("Key: " + kafkaRecord.getKV().getKey() + ", Value: " + kafkaRecord.getKV().getValue() + ", Timestamp: " + kafkaRecord.getTimestamp());
            }
        }));

        // Start the execution
        pipeline.run().waitUntilFinish();

        // print the PCollection

        logger.info("Terminating...");
    }
}