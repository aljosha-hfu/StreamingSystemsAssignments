package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
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
        if (DomainModel.getInstance()
                .getNumberOfMovesForMovingItemName(id) >= 19) {
            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
            return;
        }

        int[] newMovingItemPosition = Helpers.addArrays(
                DomainModel.getInstance().getPositionForMovingItemName(id),
                vector);

        if (DomainModel.getInstance()
                .itemExistsOnPosition(newMovingItemPosition)) {
            String existingMovingItemAtNewPositionId = DomainModel.getInstance()
                    .getItemNameForPosition(newMovingItemPosition);
            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(
                    existingMovingItemAtNewPositionId));
        } else {
            System.out.println(
                    "Position is free! " + newMovingItemPosition[0] + newMovingItemPosition[1] + newMovingItemPosition[2]);
            EventStore.getInstance()
                    .addEvent(new MovingItemMovedEvent(id, vector));

        }
    }
}
