package streamingsystems.implemented;

import streamingsystems.Helpers;
import streamingsystems.MovingItem;

import java.util.Arrays;

public class MovingItemImpl implements MovingItem {
    private final String name;
    private int[] location;
    private int numberOfMoves;
    private int value;

    public MovingItemImpl(String name, int[] location, int numberOfMoves, int value) {
        this.name = name;
        this.location = location;
        this.numberOfMoves = numberOfMoves;
        this.value = value;
    }

    public MovingItemImpl(MovingItem movingItem) {
        this.name = movingItem.getName();
        this.location = movingItem.getLocation();
        this.numberOfMoves = movingItem.getNumberOfMoves();
        this.value = movingItem.getNumberOfMoves();
    }

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

    public void setLocation(int[] location) {
        this.location = location;
    }

    public void move(int[] location) {
        this.location = Helpers.addArrays(this.location, location);
    }


    public void addMoveToMoveCounter() {
        this.numberOfMoves++;
    }

    public void setValue(int value) {
        this.value = value;
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
