package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.infrastructure.Structure.ROAD;

/**
 * Class that contains the logic regarding a roads.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Road extends AbstractInfrastructure {
    private final Point endPoint;

    /**
     * Roads may only be built with the build method. Therefore, constructor is private.
     *
     * @param owner      player owner of the street
     * @param startPoint startPoint where the road starts.
     * @param endPoint   endpoint where the road ends
     * @author weberph5
     */
    private Road(Player owner, Point startPoint, Point endPoint) {
        super(owner, startPoint);
        this.endPoint = endPoint;
    }

    /**
     * Method for the initial road builds in the founding phase where conditions differ from the main phase
     *
     * @param owner      player to whom the road should be assigned to.
     * @param startPoint start point where the road is being set to.
     * @param endPoint   end point where the road is being set to.
     * @param board      the current SettlersBoard
     * @return true if successfully built, false if not
     * @author weberph5
     */
    public static boolean initialRoadBuild(Player owner, Point startPoint, Point endPoint, SettlersBoard board) {
        final Road road = new Road(owner, startPoint, endPoint);
        if (road.isValidBuildPositionForRoad(board) && board.getCorner(startPoint) != null) {
            build(board, road);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Build method for building a new road.
     *
     * @param owner      player to whom the road should be assigned to.
     * @param startPoint start point where the road is being set to.
     * @param endPoint   end point where the road is being set to.
     * @return true if successfully built, false if not.
     * @author weberph5
     */
    public static boolean build(Player owner, Point startPoint, Point endPoint, SettlersBoard board) {
        final Road road = new Road(owner, startPoint, endPoint);
        if (road.canBuild(board)) {
            build(board, road);
            owner.payForStructure(road.getStructureType());
            return true;
        }
        return false;
    }

    private static void build(SettlersBoard board, Road road) {
        board.buildRoad(road);
        road.finalizeBuild();
    }

    /**
     * Checks if road can be built.
     *
     * @param board the board to check it upon
     * @return true if it can be built, false otherwise
     */
    @Override
    protected boolean canBuild(SettlersBoard board) {
        final Player owner = getOwner();
        return owner.hasEnoughLiquidityFor(getStructureType())
                && owner.hasEnoughInStructureStock(getStructureType())
                && isValidBuildPositionForRoad(board)
                && (hasOwnRoadAdjacent(getPosition(), board) || hasOwnRoadAdjacent(endPoint, board));
    }

    private boolean isValidBuildPositionForRoad(SettlersBoard board) {
        return board.hasEdge(getPosition(), endPoint)
                && board.isNotWaterOnlyCorner(endPoint)
                && board.getEdge(getPosition(), endPoint) == null;
    }

    /**
     * Get Structure Type as Enum
     *
     * @return the structure type as enum
     */
    @Override
    protected Structure getStructureType() {
        return ROAD;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
