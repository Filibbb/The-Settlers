package ch.zhaw.catan.build;

import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.board.Structure;

import java.awt.*;

public class Road extends AbstractInfrastructure {
    private Point endPoint;

    public Road(Player owner, Point startPoint, Point endPoint){
        super(owner, startPoint);
        this.endPoint = endPoint;

        owner.increaseBuiltStructures(owner, Structure.ROAD);
    }

}
