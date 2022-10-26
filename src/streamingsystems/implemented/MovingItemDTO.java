package streamingsystems.implemented;

import streamingsystems.MovingItem;

public class MovingItemDTO implements MovingItem {
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

    public MovingItemDTO(MovingItem movingItem) {
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
}
