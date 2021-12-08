package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.List;

/**
 * AbstractClass that contains the logic regarding a Infrastructure.
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

    public Point getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }
}
