package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.ConfigManager;
import streamingsystems.Helpers;
import streamingsystems.MovingItemListGenerator;
import streamingsystems.communication.KafkaExtractor;
import streamingsystems.implemented.MovingItemImpl;

import java.util.*;
import java.util.stream.Collectors;

public class DomainModel {

    private static final DomainModel singletonInstance = new DomainModel();

    final static public String TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();
    private final Logger logger;

    private DomainModel() {
        logger = LoggerFactory.getLogger(DomainModel.class);
        logger.info("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }
    public int getNumberOfMovesForMovingItemName(String movingItemName) {
        KafkaExtractor kafkaExtractor = new KafkaExtractor(TOPIC_NAME);

        LinkedList<Event> kafkaEvents = kafkaExtractor.getEvents();
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        return movingItems.get(movingItemName).getNumberOfMoves();
    }

    public int[] getPositionForMovingItemName(String movingItemName) {
        KafkaExtractor kafkaExtractor = new KafkaExtractor(TOPIC_NAME);

        LinkedList<Event> kafkaEvents = kafkaExtractor.getEvents();
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        return movingItems.get(movingItemName).getLocation();
    }

    public boolean itemExistsOnPosition(int[] position) {

        KafkaExtractor kafkaExtractor = new KafkaExtractor(TOPIC_NAME);

        LinkedList<Event> kafkaEvents = kafkaExtractor.getEvents();
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        long numberOfItemsAtPosition = movingItems.values().stream().filter(eachItem -> Arrays.equals(eachItem.getLocation(), position)).count();
        return numberOfItemsAtPosition > 0;
    }

    public String getItemNameForPosition(int[] positionToFind) {
        KafkaExtractor kafkaExtractor = new KafkaExtractor(TOPIC_NAME);

        LinkedList<Event> kafkaEvents = kafkaExtractor.getEvents();
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        Optional<String> foundItemName = movingItems.entrySet().stream().filter(entry -> Arrays.equals(entry.getValue().getLocation(), positionToFind))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet())
                .stream()
                .findFirst();
        return foundItemName.orElse(null);
    }

    public boolean movingItemNameExists(String movingItemName) {
        KafkaExtractor kafkaExtractor = new KafkaExtractor(TOPIC_NAME);

        LinkedList<Event> kafkaEvents = kafkaExtractor.getEvents();
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        return movingItems.containsKey(movingItemName);
    }
}
