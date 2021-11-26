package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.SettlersGame.getBoard;

/**
 * Class that contains the logic regarding a settlement.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Settlement extends AbstractInfrastructure {

    /**
     * Settlements may only be built with the build method. Therefore constructor is private.
     *
     * @param owner    player owner
     * @param position position where the settlement is being set to.
     * @author weberph5
     */
    private Settlement(Player owner, Point position) {
        super(owner, position);
        owner.increaseBuiltStructures(Structure.SETTLEMENT);
        owner.incrementWinningPoints();
    }

    /**
     * Build method for building a new Settlement.
     *
     * @param owner       player to whom the building should be assigned to.
     * @param coordinates position where the settlement is being set to.
     * @return a Settlement Object
     * @author weberph5
     */
    public boolean build(Player owner, Point coordinates) {
        SettlersBoard board = getBoard();
        if (board.hasCorner(coordinates) && (board.getNeighboursOfCorner(coordinates).isEmpty() && (!board.getAdjacentEdges(coordinates).isEmpty()) && (owner.checkLiquidity(Structure.SETTLEMENT) && (owner.checkStructureStock(Structure.SETTLEMENT))))) { //TODO: check ownership of adjacent road
            board.setCorner(coordinates, new Settlement(owner, coordinates));
            return true;
        } else return false;

    }
}

