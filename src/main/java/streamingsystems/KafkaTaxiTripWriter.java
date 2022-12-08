package streamingsystems;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.DataRepresentation.TaxiTrip;

import java.util.Properties;

public class KafkaTaxiTripWriter {
    private static final KafkaTaxiTripWriter singletonInstance = new KafkaTaxiTripWriter();

    private final Logger logger;

    private final KafkaProducer<String, byte[]> kafkaProducer;

    private final String KAFKA_TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();
    private final Properties kafkaConsumerProperties;


    private KafkaTaxiTripWriter() {
        logger = LoggerFactory.getLogger(KafkaTaxiTripWriter.class);
        kafkaProducer = new KafkaProducer<>(generateProperties());
        kafkaConsumerProperties = generateProperties();
    }

    public static KafkaTaxiTripWriter getSingletonInstance() {
        return singletonInstance;
    }

    private Properties generateProperties() {
        Properties kafkaProducerProps = new Properties();

        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                ConfigManager.INSTANCE.getKafkaUrl());
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG,
                ConfigManager.INSTANCE.getKafkaClientId());
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ByteArraySerializer.class.getName());

        return kafkaProducerProps;
    }

    public Boolean writeTaxiTripToKafka(TaxiTrip taxiTrip) {
        byte[] taxiTripByteData = SerializationUtils.serialize(taxiTrip);
        logger.info("Posting serialized message for event " + taxiTrip + " into Kafka");
        ProducerRecord<String, byte[]> recordToSend =
                new ProducerRecord<>(KAFKA_TOPIC_NAME, taxiTripByteData);

        kafkaProducer.send(recordToSend);
        kafkaProducer.flush();

        return true;
    }
}
