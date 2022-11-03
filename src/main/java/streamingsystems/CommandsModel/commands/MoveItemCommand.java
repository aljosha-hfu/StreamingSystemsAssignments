package streamingsystems.CommandsModel.commands;


import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.Helpers;
import streamingsystems.implemented.events.MovingItemDeletedEvent;
import streamingsystems.implemented.events.MovingItemMovedEvent;

public class MoveItemCommand extends Command {
    final String id;
    final int[] vector;

    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public void handle() {
        if (DomainModel.getInstance().itemHasReachedMaximumMoves(id)) {
            DomainModel.getInstance().removeMovingItemNameFromModel(id);
            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
            return;
        }

        int[] newMovingItemPosition = Helpers.addArrays(DomainModel.getInstance().getPositionForMovingItemName(id), vector);

        String existingMovingItemAtNewPositionId = DomainModel.getInstance().getItemNameForPosition(newMovingItemPosition);

        if (existingMovingItemAtNewPositionId == null) {
            EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));

            DomainModel.getInstance().moveMovingItem(id, vector);
            DomainModel.getInstance().incrementNumberOfMovesForMovingItemNameByOne(id);
        } else {
            DomainModel.getInstance().removeMovingItemNameFromModel(existingMovingItemAtNewPositionId);

            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(existingMovingItemAtNewPositionId));
        }
    }
}
