package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

public class City extends AbstractInfrastructure {
    /**
     * Cities may only be built with the build method. Therefore, constructor is private.
     *
     * @param owner    player owner
     * @param position position where the infrastructure is being set to.
     * @author weberph5
     */
    private City(Player owner, Point position) {
        super(owner, position);
        owner.increaseBuiltStructures(Structure.CITY);
        owner.decreaseBuiltStructures(Structure.SETTLEMENT);
        owner.incrementWinningPoints();
    }

    /**
     * Build method for building a new City.
     *
     * @param owner       player to whom the city should be assigned to.
     * @param coordinates position where the city is being set to.
     * @param board       the current board to place the city on.
     * @return true if successfully built, false if not.
     * @author weberph5
     */
    public static boolean build(Player owner, Point coordinates, SettlersBoard board) {
        if (canBuild(owner, coordinates, board)) {
            board.setCorner(coordinates, new City(owner, coordinates));
            payCity(owner);
            return true;
        } else return false;
    }

    private static boolean canBuild(Player owner, Point coordinates, SettlersBoard board) {
        if (board.getCorner(coordinates).getOwner().equals(owner)) {
            return (owner.checkLiquidity(Structure.CITY) && owner.hasEnoughInStructureStock(Structure.CITY));
        } else return false;
    }

    private static void payCity(Player owner) {
        owner.removeResourceCardFromHand(Resource.ORE, 3);
        owner.removeResourceCardFromHand(Resource.WOOL, 2);
    }
}