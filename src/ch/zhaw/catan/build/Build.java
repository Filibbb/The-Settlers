package ch.zhaw.catan.build;

import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.board.Structure;

import java.awt.*;

public class Build {

    public boolean buildRoad(Player owner, Point startPoint, Point endPoint) {
        if (Player.checkLiquidity(owner, Structure.ROAD)) {
            if (Player.checkStructureStock(owner, Structure.ROAD)) {
                new Road(owner, startPoint, endPoint);

                return true;
            }

            return false;
        }
        return false;
    }

    public boolean buildSettlement(Player owner, Point coordinates) {
        if (Player.checkLiquidity(owner, Structure.SETTLEMENT))
            if (Player.checkStructureStock(owner, Structure.SETTLEMENT)) {
                new Settlement(owner, coordinates);

                return true;
            }
        return false;
    }


}
