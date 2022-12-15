package streamingsystems;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.io.kafka.KafkaRecord;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
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
        // pardo into a new PCollection as arrays with keys
        PCollection<KV<Integer, String[]>> parsedRecords = kafkaRecords.apply(
                ParDo.of(new DoFn<KafkaRecord<Integer, String>, KV<Integer, String[]>>() {
                    @ProcessElement
                    public void processElement(@Element KafkaRecord<Integer, String> inputRecord,
                                               OutputReceiver<KV<Integer, String[]>> outputRecord) {
                        String[] splitSensorValues = inputRecord.getKV().getValue().split(",");

                        //Remove negative values
                        for (int i = 0; i < splitSensorValues.length; i++) {
                            if (splitSensorValues[i].startsWith("-")) {
                                splitSensorValues[i] = splitSensorValues[i].substring(1);
                            }
                        }

                        outputRecord.output(KV.of(inputRecord.getKV().getKey(), splitSensorValues));
                    }
                }));

        // Group messages by key
        PCollection<KV<Integer, Iterable<String[]>>> groupedSpeedRecords = parsedRecords.apply(
                org.apache.beam.sdk.transforms.GroupByKey.create());

        // Print the collection
        groupedSpeedRecords.apply(ParDo.of(new DoFn<KV<Integer, Iterable<String[]>>, Void>() {
            @ProcessElement
            public void processElement(@Element KV<Integer, Iterable<String[]>> record) {
                System.out.println(record.getKey());
                for (String[] sensorValues : record.getValue()) {
                    System.out.println("  " + sensorValues[0] + " " + sensorValues[1] + " " + sensorValues[2]);
                }
            }
        }));

        // Start the execution
        pipeline.run().waitUntilFinish();

        logger.info("Terminating...");
    }
}