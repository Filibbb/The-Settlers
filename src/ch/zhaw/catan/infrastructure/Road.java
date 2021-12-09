package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

/**
 * Class that contains the logic regarding a roads.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Road extends AbstractInfrastructure {

    private final Point endPoint;

    /**
     * Roads may only be built with the build method. Therefore constructor is private.
     *
     * @param owner      player owner of the street
     * @param startPoint startPoint where the road starts.
     * @param endPoint   endpoint where the road ends
     * @author weberph5
     */
    private Road(Player owner, Point startPoint, Point endPoint) {
        super(owner, startPoint);
        this.endPoint = endPoint;
        owner.increaseBuiltStructures(Structure.ROAD);
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
        if (canBuild(owner, startPoint, endPoint, board)) {
            board.setEdge(startPoint, endPoint, new Road(owner, startPoint, endPoint));
            payRoad(owner);
            return true;

        } else return false;
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
        if (board.hasEdge(startPoint, endPoint) && board.getEdge(startPoint, endPoint) == null && board.getCorner(startPoint) != null) {
            board.setEdge(startPoint, endPoint, new Road(owner, startPoint, endPoint));
            return true;
        } else return false;
    }

    private static boolean canBuild(Player owner, Point startPoint, Point endPoint, SettlersBoard board) {
        return (owner.checkLiquidity(Structure.ROAD) && owner.hasEnoughInStructureStock(Structure.ROAD) && board.hasEdge(startPoint, endPoint) && board.getEdge(startPoint, endPoint) == null && hasRoadAdjacent(owner, startPoint, board));
    }

    private static void payRoad(Player owner) {
        owner.removeResourceCardFromHand(Resource.LUMBER);
        owner.removeResourceCardFromHand(Resource.BRICK);
    }
}
