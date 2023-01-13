package streamingsystems.implemented;

import streamingsystems.MovingItem;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Represents a moving item that is used in commands and events.
 */
public class MovingItemImpl implements MovingItem {
    private final String name;
    private int[] location;
    private int numberOfMoves;
    private int value;

    /**
     * @param name          The name of the moving item.
     * @param location      The location of the moving item.
     * @param numberOfMoves The number of moves the moving item has made.
     * @param value         The value of the moving item.
     */
    public MovingItemImpl(String name, int[] location, int numberOfMoves,
                          int value) {
        this.name = name;
        this.location = location;
        this.numberOfMoves = numberOfMoves;
        this.value = value;
    }

    /**
     * @param movingItem The moving item to create a version of this class from.
     */
    public MovingItemImpl(MovingItem movingItem) {
        this.name = movingItem.getName();
        this.location = movingItem.getLocation();
        this.numberOfMoves = movingItem.getNumberOfMoves();
        this.value = movingItem.getNumberOfMoves();
    }

    /**
     * @param name The name of the moving item.
     */
    public MovingItemImpl(String name) {
        this(name, new int[]{0, 0, 0}, 0, 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getLocation() {
        return location;
    }

    @Override
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * @param location The new location of the moving item.
     */
    public void setLocation(int[] location) {
        this.location = location;
    }

    /**
     * @param vectorToMove The vector to move the moving item with.
     */
    public void move(int[] vectorToMove) {
        this.location = IntStream.range(0, this.location.length)
                .mapToObj(i -> this.location[i] + vectorToMove[i])
                .mapToInt(i -> i).toArray();
    }

    /**
     * Increment the number of moves the moving item has made.
     */
    public void addMoveToMoveCounter() {
        this.numberOfMoves++;
    }

    /**
     * Set the value of the moving item.
     *
     * @param value The new value of the moving item.
     */
    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "MovingItemDTO{" + "name='" + name + '\'' + ", location=" + Arrays.toString(
                location) + ", numberOfMoves=" + numberOfMoves + ", value=" + value + '}';
    }
}
