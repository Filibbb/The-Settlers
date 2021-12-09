package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Land;
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
     * Checks if the position has a road adjacent to it since this is a requirement for building a settlement or road.
     *
     * @param owner       the player that owns the structure
     * @param coordinates the coordinates where structure is being built
     * @param board       the current SettlersBoard
     * @return true if a road is adjacent, false if not
     */
    protected static boolean hasRoadAdjacent(Player owner, Point coordinates, SettlersBoard board) {
        if (!board.getAdjacentEdges(coordinates).isEmpty()) {
            List<Road> surroundingRoads = board.getAdjacentEdges(coordinates);
            for (Road road : surroundingRoads) {
                Player roadOwner = road.getOwner();
                if (roadOwner.equals(owner)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the corner is not on the edge of the board surrounded by water only
     *
     * @param coordinates the coordinates where structure is being built
     * @param board       the coordinates where structure is being built
     * @return true if at least one surrounding field is not water
     */
    protected static boolean isNotWaterOnlyCorner(Point coordinates, SettlersBoard board) {
        List<Land> fieldsAroundCorner = board.getFields(coordinates);
        for (Land land : fieldsAroundCorner) {
            if (land.getResource() != null) {
                return true;
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
}
