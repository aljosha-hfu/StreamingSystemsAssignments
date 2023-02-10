package streamingsystems03.commandsmodel.commands;
import streamingsystems03.commandsmodel.DomainModel;
import streamingsystems03.commandsmodel.EventStore;
import streamingsystems03.commandsmodel.meta.Command;
import streamingsystems03.Helpers;
import streamingsystems03.implemented.events.MovingItemDeletedEvent;
import streamingsystems03.implemented.events.MovingItemMovedEvent;

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
