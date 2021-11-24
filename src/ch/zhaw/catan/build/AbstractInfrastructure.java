package ch.zhaw.catan.build;

import ch.zhaw.catan.player.Player;

import java.awt.*;

public abstract class AbstractInfrastructure {
    private Player owner;
    private Point position;


    public AbstractInfrastructure(Player owner, Point position){
        this.owner = owner;
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }
}
