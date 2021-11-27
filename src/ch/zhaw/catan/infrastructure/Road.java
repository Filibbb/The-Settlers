package ch.zhaw.catan.infrastructure;

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
    private Point endPoint;

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
     * @param owner      player to whom the building should be assigned to.
     * @param startPoint start point where the road is being set to.
     * @param endPoint   end point where the road is being set to.
     * @return true if successfully built, false if not.
     * @author weberph5
     */
    public boolean build(Player owner, Point startPoint, Point endPoint, SettlersBoard board) {
        if (canBuild(owner, startPoint, endPoint, board)) {
            board.setEdge(startPoint, endPoint, new Road(owner, startPoint, endPoint));
            return true;

        } else return false;
    }

    private boolean canBuild(Player owner, Point startPoint, Point endPoint, SettlersBoard board) {
        return ((owner.checkLiquidity(Structure.ROAD)) && (owner.checkStructureStock(Structure.ROAD) && (board.hasEdge(startPoint, endPoint)) && (board.getEdge(startPoint, endPoint) == null) && (!board.getAdjacentEdges(startPoint).isEmpty()))); //TODO check ownership of adjacent road
    }
}
