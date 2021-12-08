package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
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

    static boolean hasRoad(Player owner, Point coordinates, SettlersBoard board) {
        if (!board.getAdjacentEdges(coordinates).isEmpty()) {
            java.util.List<Road> roads = board.getAdjacentEdges(coordinates);
            List<Player> roadOwners = new ArrayList<>();
            for (Road road : roads) {
                Player roadOwner = road.getOwner();
                roadOwners.add(roadOwner);
            }
            return roadOwners.contains(owner);
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
