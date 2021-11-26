package ch.zhaw.catan.build;

import ch.zhaw.catan.board.Structure;
import ch.zhaw.catan.player.Player;

import java.awt.*;

public class Road extends AbstractInfrastructure {
    private Point startPoint;
    private Point endPoint;

    public Road(Player owner, Point position, Point endPoint){
        super(owner, position);
        this.endPoint = endPoint;

        owner.increaseBuiltStructures(owner, Structure.ROAD);
    }

    @Override
    public Point getPosition() {
        startPoint = super.getPosition();
        return startPoint; //TODO return road position as Edge
    }

    @Override
    public Player getOwner() {
        return super.getOwner();
    }
}