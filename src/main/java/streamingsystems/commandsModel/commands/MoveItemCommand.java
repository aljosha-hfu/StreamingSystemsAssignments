package streamingsystems.commandsModel.commands;


import streamingsystems.commandsModel.DomainModel;
import streamingsystems.commandsModel.EventStore;
import streamingsystems.commandsModel.meta.Command;
import streamingsystems.Helpers;
import streamingsystems.implemented.events.MovingItemDeletedEvent;
import streamingsystems.implemented.events.MovingItemMovedEvent;

/**
 * Command for moving a moving item.
 */
public class MoveItemCommand extends Command {
    /**
     * The id of the moving item.
     */
    final String id;

    /**
     * The position of the moving item.
     */
    final int[] vector;

    /**
     * @param id     The id of the moving item.
     * @param vector The new vector to apply to the moving item.
     */
    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle() {
        if (DomainModel.getInstance().itemHasReachedMaximumMoves(id)) {
            DomainModel.getInstance().removeMovingItemNameFromModel(id);
            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
            return;
        }

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
    }
}
