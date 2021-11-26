package ch.zhaw.catan.infrastructure;

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
     * Build method for building a new Settlement.
     *
     * @param owner      player to whom the building should be assigned to.
     * @param startPoint startpoint where the road is being set to.
     * @param endPoint   endpoint where the road is being set to.
     * @return a Road Object
     * @author weberph5
     */
    public Road build(Player owner, Point startPoint, Point endPoint) {
        if (owner.checkLiquidity(Structure.ROAD)) {
            if (owner.checkStructureStock(Structure.ROAD)) {
                return new Road(owner, startPoint, endPoint);
            }
        }
        return null;
    }
}
