package streamingsystems01.implemented;


import streamingsystems01.MovingItem;

import java.util.Arrays;

/**
 * Represents a moving item that is used in the query handler.
 */
public class MovingItemDTO implements MovingItem {
    private final String name;
    private final int[] location;
    private final int numberOfMoves;
    private final int value;

    /**
     * @param name          The name of the moving item.
     * @param location      The location of the moving item.
     * @param numberOfMoves The number of moves the moving item has made.
     * @param value         The value of the moving item.
     */
    public MovingItemDTO(String name, int[] location, int numberOfMoves,
                         int value) {
        this.name = name;
        this.location = location;
        this.numberOfMoves = numberOfMoves;
        this.value = value;
    }

    /**
     * @param movingItem The moving item to create a DTO from.
     */
    public MovingItemDTO(MovingItemImpl movingItem) {
        this.name = movingItem.getName();
        this.location = movingItem.getLocation();
        this.numberOfMoves = movingItem.getNumberOfMoves();
        this.value = movingItem.getNumberOfMoves();
    }

    /**
     * @param name The name of the moving item.
     */
    public MovingItemDTO(String name) {
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

    @Override
    public String toString() {
        return "MovingItemDTO{" + "name='" + name + '\'' + ", location=" + Arrays.toString(
                location) + ", numberOfMoves=" + numberOfMoves + ", value=" + value + '}';
    }
}
