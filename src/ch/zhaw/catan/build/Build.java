package ch.zhaw.catan.build;

import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.board.Structure;

import java.awt.*;
import java.util.Set;

public class Build {

    public Road buildRoad(Player owner, Point startPoint, Point endPoint) {
        if (Player.checkLiquidity(owner, Structure.ROAD)) {
            if (Player.checkStructureStock(owner, Structure.ROAD)) {
                return new Road(owner, startPoint, endPoint);
            }
        }
        return null;
    }

    public Settlement buildSettlement(Player owner, Point coordinates) {
        if (Player.checkLiquidity(owner, Structure.SETTLEMENT))
            if (Player.checkStructureStock(owner, Structure.SETTLEMENT)) {
                return new Settlement(owner, coordinates);
            }
        return null;
    }


}
