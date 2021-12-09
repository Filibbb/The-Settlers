package ch.zhaw.catan.utilities;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Utility class with helping methods for testing purposes
 *
 * @author abuechi
 */
public class PlayerResourceStockUtility {

    private PlayerResourceStockUtility() {
    }

    /**
     * Helps asserting resource card stock of players
     *
     * @param model    model for testing containing relevant testing data
     * @param expected expected result map
     * @author tebe
     */
    public static void assertPlayerResourceCardStockEquals(GameDataContainer model, Map<Faction, Map<Resource, Integer>> expected) {
        for (int i = 0; i < expected.keySet().size(); i++) {
            final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
            Faction faction = currentPlayer.getPlayerFaction();
            for (Resource resource : Resource.values()) {
                assertEquals(expected.get(faction).get(resource), currentPlayer.getResourceCardCountFor(resource),
                        "Resource card stock of player " + i + " [faction " + faction + "] for resource type " + resource + " does not match.");
            }
            model.getTurnOrderHandler().switchToNextPlayer();
        }
    }
}
