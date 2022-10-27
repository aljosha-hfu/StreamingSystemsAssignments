package streamingsystems.CommandsModel.Meta.Predefined;

import streamingsystems.implemented.MovingItemImpl;

public interface Commands {
    void createItem(MovingItemImpl movingItem);
    void deleteItem(String id);
    void moveItem(String id, int[] vector);
    void changeValue(String id, int newValue);
}