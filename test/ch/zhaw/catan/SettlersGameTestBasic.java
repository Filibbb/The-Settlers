package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.utilities.GameDataContainer;
import ch.zhaw.catan.utilities.ThreePlayerStandard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.utilities.ThreePlayerStandard.getAfterSetupPhase;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains some basic tests for the {@link SettlersGame} class
 * The class was changed to make it work with our logic and class hierarchy.
 *
 * <p></p>
 *
 * @author tebe, abuechi
 */
public class SettlersGameTestBasic {

    public static void assertPlayerResourceCardStockEquals(GameDataContainer model, Map<Faction, Map<Resource, Integer>> expected) {
        for (int i = 0; i < expected.keySet().size(); i++) {
            final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
            Faction f = currentPlayer.getPlayerFaction();
            for (Resource r : Resource.values()) {
                assertEquals(expected.get(f).get(r), currentPlayer.getResourceCardCountFor(r),
                        "Resource card stock of player " + i + " [faction " + f + "] for resource type " + r + " does not match.");
            }
            model.getTurnOrderHandler().switchToNextPlayer();
        }
    }

    /**
     * Checks that the resource card payout for different dice values matches
     * the expected payout for the game state {@link ThreePlayerStandard#getAfterSetupPhase()}}.
     * <p>
     *
     * @param diceValue the dice value
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void requirementDiceThrowResourcePayoutThreePlayerStandardTest(int diceValue) {
        GameDataContainer model = getAfterSetupPhase();
        Map<Faction, List<Resource>> expected = ThreePlayerStandard.INITIAL_DICE_THROW_PAYOUT.get(diceValue);
        //FIXME Map<Faction, List<Resource>> actual = model.throwDice(diceValue);
        //FIXME assertEquals(ThreePlayerStandard.INITIAL_DICE_THROW_PAYOUT.get(diceValue), model.throwDice(diceValue));
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhase()}}.
     */
    @Test
    public void requirementPlayerResourceCardStockAfterSetupPhase() {
        GameDataContainer model = getAfterSetupPhase();
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.INITIAL_PLAYER_CARD_STOCK);
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank()}}.
     */
    @Test
    public void requirementPlayerResourceCardStockAfterSetupPhaseAlmostEmptyBank() {
        GameDataContainer model = ThreePlayerStandard.getAfterSetupPhaseAlmostEmptyBank();
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.BANK_ALMOST_EMPTY_RESOURCE_CARD_STOCK);
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank()}}.
     */
    @Test
    public void requirementPlayerResourceCardStockPlayerOneReadyToBuildFifthSettlement() {
        GameDataContainer model = ThreePlayerStandard.getPlayerOneReadyToBuildFifthSettlement();
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_RESOURCE_CARD_STOCK);
    }
}
