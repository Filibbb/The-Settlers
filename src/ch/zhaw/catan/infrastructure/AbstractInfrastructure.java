package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.List;

/**
 * AbstractClass that contains the logic regarding an Infrastructure.
 *
 * @author weberph5
 * @version 1.0.0
 */
public abstract class AbstractInfrastructure {
    private final Player owner;
    private final Point position;

    /**
     * @param owner    player owner
     * @param position position where the infrastructure is being set to.
     * @author weberph5
     */
    protected AbstractInfrastructure(Player owner, Point position) {
        this.owner = owner;
        this.position = position;
    }

    /**
     * Get Structure Type as Enum implemented by subclasses
     *
     * @return the structure type as enum
     */
    protected abstract Structure getStructureType();

    /**
     * Abstract method to check if structure can be built
     *
     * @return boolean if structure can be built
     */
    protected abstract boolean canBuild(SettlersBoard board);

    /**
     * Checks if the position has a road adjacent to it since this is a requirement for building a settlement or road.
     *
     * @param cornerCoordinates the cornerCoordinates where structure is being built
     * @param board             the current SettlersBoard
     * @return true if a road is adjacent, false if not
     */
    protected boolean hasOwnRoadAdjacent(Point cornerCoordinates, SettlersBoard board) {
        final List<Road> adjacentEdges = board.getAdjacentEdges(cornerCoordinates);
        if (!adjacentEdges.isEmpty()) {
            for (Road adjacentRoads : adjacentEdges) {
                if (adjacentRoads.getOwner().getPlayerFaction().equals(owner.getPlayerFaction())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to get the owner of an infrastructure
     *
     * @return Player owner of the infrastructure
     */
    public Player getOwner() {
        return owner;
    }

    public Point getPosition() {
        return position;
    }
}
