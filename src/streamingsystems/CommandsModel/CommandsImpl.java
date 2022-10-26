package streamingsystems.CommandsModel;

import streamingsystems.MovingItem;

public class CommandsImpl implements Commands {
    @Override
    public void createItem(MovingItem movingItem) {

    }

    @Override
    public void deleteItem(String id) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("ID must be a valid, non-blank string");
        }
    }

    @Override
    public void moveItem(String id, int[] vector) {
        if (vector.length != 3) {
            throw new IllegalArgumentException("Vector must be of length 3 (x, y, z)");
        }
    }

    @Override
    public void changeValue(String id, int newValue) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("ID must be a valid, non-blank string");
        }
    }
}
