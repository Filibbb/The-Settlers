package ch.zhaw.catan.build;

import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.board.Structure;

import java.awt.*;

public class Road {

    public Road(Player owner, Point startPoint, Point endPoint){

        owner.increaseBuiltStructures(owner, Structure.ROAD);
    }

}
