# Task 02
## Remove MovingItem after n (= 20) moves:
To achieve this behavior, we implemented a method in the domain model to check if the item has already moved n-1 times before. Then the apply() method of the MoveItemCommand class checks whether the number uses this method to determine if the item should be processed or deleted.

**DomainModel:**
```Java
private final int maximumMoves = 20;

public boolean itemHasReachedMaximumMoves(String id){
        return getNumberOfMovesForMovingItemName(id) >= maximumMoves-1;
        }
```
**MoveItemCommand:**
```Java
@Override
public void handle(){
    if(DomainModel.getInstance().itemHasReachedMaximumMoves(id)){
    DomainModel.getInstance().removeMovingItemNameFromModel(id);
    EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
    return;
}
// Else process event...
}
```

## Check if MovingItem gets moved to a position where another MovingItem is already present and if so, delete the MovingItem that has been there before.
We wrote methods in the DomainModel to check if a MovingItem exists on a position and to get a MovingItem at a specific position.
**DomainModel:**
```Java
public boolean itemExistsOnPosition(int[] position) {
        // Using stream and filter instead of HashMap.containsValue() because the values are int[] and they cannot be compared this way
        long numberOfItemsAtPosition = movingItemsPositions.values().stream().filter(pos -> Arrays.equals(pos, position)).count();
        return numberOfItemsAtPosition > 0;
}

public String getItemNameForPosition(int[] positionToFind) {
        Optional<String> foundItemName = movingItemsPositions.entrySet().stream().filter(entry -> Arrays.equals(entry.getValue(), positionToFind))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet())
        .stream()
        .findFirst();
        return foundItemName.orElse(null);
}
```

We then check in the MoveItemCommand if there is another MovinItem at the position to move to and if so, delete it and move the new MovingItem there.

To calculate a new position when moving a MovingItem, we wrote a helper class with a helper method.

**Helpers:**
```Java
public class Helpers {
    public static int[] addArrays(int[] location1, int[] location2) {
        return IntStream.range(0, location1.length).
                mapToObj(i -> location1[i] + location2[i]).mapToInt(i -> i).toArray();
    }
}
```

**MoveItemCommand:**
```Java
int[] newMovingItemPosition = Helpers.addArrays(DomainModel.getInstance().getPositionForMovingItemName(id), vector);


if (DomainModel.getInstance().itemExistsOnPosition(newMovingItemPosition)) {
    String existingMovingItemAtNewPositionId = DomainModel.getInstance().getItemNameForPosition(newMovingItemPosition);
    DomainModel.getInstance().removeMovingItemNameFromModel(existingMovingItemAtNewPositionId);
    EventStore.getInstance().addEvent(new MovingItemDeletedEvent(existingMovingItemAtNewPositionId));
} else {
    EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
    DomainModel.getInstance().moveMovingItem(id, vector);
    DomainModel.getInstance().incrementNumberOfMovesForMovingItemNameByOne(id);
}
```

## 
