package streamingsystems06;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.io.kafka.KafkaRecord;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Mean;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A class for starting the data processing pipeline.
 */
public class DataParser implements Serializable {
    /**
     * The windows size in seconds.
     */
    public static final int WINDOW_SIZE_SECONDS = 30;

    /**
     * The main method for this class.
     */
    public void parse() {
        Logger logger = LoggerFactory.getLogger(DataParser.class.getName());
        logger.info("Starting...");

        PipelineOptions options = PipelineOptionsFactory.create();
        //        options.setRunner(FlinkRunner.class);
        Pipeline pipeline = Pipeline.create(options);


        PCollection<KafkaRecord<Integer, String>> kafkaRecords = pipeline.apply(KafkaIO
                                                                                        .<Integer, String>read()
                                                                                        .withBootstrapServers(
                                                                                                ConfigManager.INSTANCE.getKafkaUrl())
                                                                                        .withTopic(ConfigManager.INSTANCE.getKafkaTopicName())
                                                                                        .withKeyDeserializer(
                                                                                                IntegerDeserializer.class)
                                                                                        .withValueDeserializer(
                                                                                                StringDeserializer.class)
                                                                                        .withStartReadTime(LocalDate
                                                                                                                   .parse("1990-01-01")
                                                                                                                   .toDateTimeAtCurrentTime()
                                                                                                                   .toInstant()));

        // Pardo into a new PCollection as arrays with keys
        PCollection<KV<Integer, Double>>
                parsedRecords =
                kafkaRecords.apply(ParDo.of(new DoFn<KafkaRecord<Integer, String>, KV<Integer, Double>>() {
                    @ProcessElement public void parseValues(
                            @Element KafkaRecord<Integer, String> inputRecord,
                            OutputReceiver<KV<Integer, Double>> outputRecord
                    ) {
                        String[] splitSensorValueStrings = inputRecord.getKV().getValue().split(",");

                        if (inputRecord.getKV().getValue().length() == 0) { // Ignore empty strings
                            return;
                        }
                        Arrays.stream(splitSensorValueStrings).forEach(sensorValueString -> {
                            double splitSensorValue = Double.parseDouble(sensorValueString);
                            if (splitSensorValue > 0) {
                                outputRecord.output(KV.of(inputRecord.getKV().getKey(), splitSensorValue));
                            }
                        });
                    }
                }));

        // Window the last 30 seconds
        PCollection<KV<Integer, Double>> windowedSpeedInLast30Seconds = parsedRecords.apply(Window.into(FixedWindows.of(
                Duration.standardSeconds(WINDOW_SIZE_SECONDS)))).apply(Mean.perKey());

        // Print the collection
        windowedSpeedInLast30Seconds.apply(ParDo.of(new DoFn<KV<Integer, Double>, Void>() {
            @ProcessElement public void printAveragedValues(@Element KV<Integer, Double> inputRecord) {
                logger.info("Sensor ID: " + inputRecord.getKey() + "; 30s avg speed: " + inputRecord.getValue());
            }
        }));

        // Start the execution
        pipeline.run().waitUntilFinish();

        logger.info("Terminating...");
    }
}
