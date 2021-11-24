package ch.zhaw.catan.build;

import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

public class Settlement extends AbstractInfrastructure {

    public Settlement(Player owner, Point coordinates) {
        super(owner, coordinates);
        owner.increaseBuiltStructures(owner, Structure.SETTLEMENT);
        owner.incrementWinningPoints();
    }
}
