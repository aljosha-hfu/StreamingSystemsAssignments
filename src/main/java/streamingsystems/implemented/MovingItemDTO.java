package streamingsystems.implemented;

import streamingsystems.MovingItemImpl;

import java.util.Arrays;

public class MovingItemDTO implements MovingItemImpl {
    private final String name;
    private final int[] location;
    private final int numberOfMoves;
    private final int value;

    public MovingItemDTO(String name, int[] location, int numberOfMoves, int value) {
        this.name = name;
        this.location = location;
        this.numberOfMoves = numberOfMoves;
        this.value = value;
    }

    public MovingItemDTO(MovingItemImpl movingItem) {
        this.name = movingItem.getName();
        this.location = movingItem.getLocation();
        this.numberOfMoves = movingItem.getNumberOfMoves();
        this.value = movingItem.getNumberOfMoves();
    }

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
        return "MovingItemDTO{" +
                "name='" + name + '\'' +
                ", location=" + Arrays.toString(location) +
                ", numberOfMoves=" + numberOfMoves +
                ", value=" + value +
                '}';
    }
}
