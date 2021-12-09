package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.infrastructure.Structure.CITY;
import static ch.zhaw.catan.infrastructure.Structure.SETTLEMENT;

/**
 * The city class
 *
 * @author weberph5
 */
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
        final City city = new City(owner, coordinates);
        if (city.canBuild(board)) {
            board.buildCity(city);
            city.finalizeBuild();
            owner.payForStructure(city.getStructureType());
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void finalizeBuild() {
        super.finalizeBuild();
        getOwner().decreaseStructureCounterFor(SETTLEMENT);
        getOwner().incrementWinningPoints();
    }

    /**
     * Get Structure Type as Enum
     *
     * @return the structure type as enum
     */
    @Override
    protected Structure getStructureType() {
        return CITY;
    }

    /**
     * Checks if City can be built.
     *
     * @param board the board to check it upon
     * @return true if it can be built, false otherwise
     */
    @Override
    protected boolean canBuild(SettlersBoard board) {
        final Player owner = getOwner();
        return board.getCorner(getPosition()) != null
                && board.getCorner(getPosition()).getOwner().equals(owner)
                && board.getCorner(getPosition()).getStructureType().equals(SETTLEMENT)
                && owner.hasEnoughLiquidityFor(getStructureType())
                && owner.hasEnoughInStructureStock(getStructureType());
    }

    /**
     * Overrides toString() so it displays faction in capitol letters on board.
     *
     * @return player faction representation in capital letters
     */
    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}