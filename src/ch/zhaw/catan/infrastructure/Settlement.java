package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

/**
 * Class that contains the logic regarding a settlement.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Settlement extends AbstractInfrastructure {

    /**
     * Settlements may only be built with the build method. Therefore, constructor is private.
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
     * @param board       the current board to place the settlements on.
     * @return true if successfully built, false if not.
     * @author weberph5
     */
    public static boolean build(Player owner, Point coordinates, SettlersBoard board) {
        if (canBuild(owner, coordinates, board)) {
            board.setCorner(coordinates, new Settlement(owner, coordinates));
            paySettlement(owner);
            return true;
        } else return false;
    }

    public static boolean initialBuild(Player owner, Point coordinates, SettlersBoard board) {
        if ((board.hasCorner(coordinates)) && (board.getCorner(coordinates) == null) && (board.getNeighboursOfCorner(coordinates).isEmpty())) {
            board.setCorner(coordinates, new Settlement(owner, coordinates));
            return true;
        } else return false;
    }

    private static boolean canBuild(Player owner, Point coordinates, SettlersBoard board) {
        return (board.hasCorner(coordinates) && (board.getCorner(coordinates) == null) && (board.getNeighboursOfCorner(coordinates).isEmpty() && (!board.getAdjacentEdges(coordinates).isEmpty()) && (owner.checkLiquidity(Structure.SETTLEMENT) && (owner.hasEnoughInStructureStock(Structure.SETTLEMENT)))));//TODO: check ownership of adjacent road
    }

    private static void paySettlement(Player owner) {
        owner.removeResourceCardFromHand(Resource.BRICK);
        owner.removeResourceCardFromHand(Resource.GRAIN);
        owner.removeResourceCardFromHand(Resource.WOOL);
        owner.removeResourceCardFromHand(Resource.LUMBER);
    }
}
