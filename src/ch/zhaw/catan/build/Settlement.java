package ch.zhaw.catan.build;

import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

public class Settlement extends AbstractInfrastructure {

    public Settlement(Player owner, Point position) {
        super(owner, position);
        owner.increaseBuiltStructures(owner, Structure.SETTLEMENT);
        owner.incrementWinningPoints();
    }

    @Override
    public Player getOwner() {
        return super.getOwner();
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }
}