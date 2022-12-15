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
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test the main functionality
 */
public class BeamQueryMain {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(BeamQueryMain.class);
        logger.info("Starting...");

        PipelineOptions options = PipelineOptionsFactory.create();
        //        options.setRunner(FlinkRunner.class);
        Pipeline pipeline = Pipeline.create(options);


        PCollection<KafkaRecord<Integer, String>> kafkaRecords = pipeline.apply(KafkaIO
                                                                                        .<Integer, String>read()
                                                                                        .withBootstrapServers(
                                                                                                ConfigManager.INSTANCE.getKafkaUrl())
                                                                                        .withTopic(
                                                                                                ConfigManager.INSTANCE.getKafkaTopicName())
                                                                                        .withKeyDeserializer(
                                                                                                IntegerDeserializer.class)
                                                                                        .withValueDeserializer(
                                                                                                StringDeserializer.class)
                                                                                        .withStartReadTime(LocalDate
                                                                                                                   .parse("1990-01-01")
                                                                                                                   .toDateTimeAtCurrentTime()
                                                                                                                   .toInstant()));

        // Print the collection
        kafkaRecords.apply(ParDo.of(new DoFn<KafkaRecord<Integer, String>, Void>() {
            @ProcessElement
            public void processElement(ProcessContext c) {
                KafkaRecord<Integer, String> kafkaRecord = c.element();
                logger.info("Key: " + kafkaRecord.getKV().getKey() + ", Value: " + kafkaRecord.getKV().getValue() +
                                    ", Timestamp: " + kafkaRecord.getTimestamp());
            }
        }));

        // Start the execution
        pipeline.run().waitUntilFinish();

        // print the PCollection

        logger.info("Terminating...");
    }
}