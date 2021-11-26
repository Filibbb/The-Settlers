package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.player.Player;

import java.awt.*;

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

    public Point getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }
}
