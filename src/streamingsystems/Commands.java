package streamingsystems;

public interface Commands {
    void createItem(MovingItem movingItem);
    void deleteItem(String id);
    void moveItem(String id, int[] vector);
    void changeValue(String id, int newValue);
}