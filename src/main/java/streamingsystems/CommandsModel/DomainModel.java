package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.ConfigManager;
import streamingsystems.MovingItemListTools;
import streamingsystems.communication.KafkaExtractor;
import streamingsystems.implemented.MovingItemImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for checks against commands to be executed, if they are e.g. valid.
 */
public class DomainModel {

    final static public String TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();
    private static final DomainModel singletonInstance = new DomainModel();
    private final Logger logger;

    private DomainModel() {
        logger = LoggerFactory.getLogger(DomainModel.class);
        logger.info("DomainModel Instance created.");
    }

    /**
     * @return The singleton instance of the DomainModel.
     */
    public static DomainModel getInstance() {
        return singletonInstance;
    }

    /**
     * @param movingItemName The name of the moving item to search.
     * @return The number of moves of the moving item.
     */
    public int getNumberOfMovesForMovingItemName(String movingItemName) {
        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(TOPIC_NAME);
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        return movingItems.get(movingItemName).getNumberOfMoves();
    }

    /**
     * @param movingItemName The name of the moving item to search.
     * @return The position of the moving item name.
     */
    public int[] getPositionForMovingItemName(String movingItemName) {
        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(TOPIC_NAME);
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        return movingItems.get(movingItemName).getLocation();
    }

    /**
     * @param position The position to search.
     * @return True if a moving item exists at the position.
     */
    public boolean itemExistsOnPosition(int[] position) {
        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(TOPIC_NAME);
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        long numberOfItemsAtPosition = movingItems.values().stream()
                .filter(eachItem -> Arrays.equals(eachItem.getLocation(),
                        position)).count();
        return numberOfItemsAtPosition > 0;
    }

    /**
     * @param positionToFind The position to search.
     * @return The name of the moving item at the position.
     */
    public String getItemNameForPosition(int[] positionToFind) {
        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(TOPIC_NAME);
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        Optional<String> foundItemName = movingItems.entrySet().stream()
                .filter(entry -> Arrays.equals(entry.getValue().getLocation(),
                        positionToFind)).map(Map.Entry::getKey)
                .collect(Collectors.toSet()).stream().findFirst();
        return foundItemName.orElse(null);
    }

    /**
     * @param movingItemName The name of the moving item to search.
     * @return True if the moving item name exists.
     */
    public boolean movingItemNameExists(String movingItemName) {
        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(TOPIC_NAME);
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        return movingItems.containsKey(movingItemName);
    }
}
