package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.GameBankHandler;
import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.utilities.GameDataContainer;
import ch.zhaw.catan.utilities.ThreePlayerStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.board.Resource.*;
import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
import static ch.zhaw.catan.utilities.PlayerResourceStockUtility.assertPlayerResourceCardStockEquals;
import static ch.zhaw.catan.utilities.ThreePlayerStandard.getAfterSetupPhase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing class to check if RollDiceCommand works correctly.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class GameBankHandlerTest {
    private final GameBankHandler gameBankHandler = new GameBankHandler();
    private GameDataContainer model;

    /**
     * Creates initial dice roll test.
     */
    @BeforeEach
    public void setUp() {
        model = getAfterSetupPhase();
    }

    /**
     * Throws each dice value except 7 once and tests whether the resource
     * card stock of the players matches the expected stock.
     */
    @Test
    public void requirementDiceThrowPlayerResourceCardStockUpdateTest() {
        for (int i : List.of(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)) {
            gameBankHandler.handoutResourcesOfTheRolledField(i, model.getSettlersBoard());
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(GRAIN, 1, WOOL, 1, BRICK, 1, ORE, 1, LUMBER, 1),
                Faction.values()[1], Map.of(GRAIN, 1, WOOL, 3, BRICK, 0, ORE, 0, LUMBER, 0),
                Faction.values()[2], Map.of(GRAIN, 0, WOOL, 0, BRICK, 1, ORE, 0, LUMBER, 1));
        assertPlayerResourceCardStockEquals(model, expected);
    }

    /**
     * Tests whether payout with multiple settlements of the same player at one field works
     */
    @Test
    public void requirementTwoSettlementsSamePlayerSameFieldResourceCardPayout() {
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        assertTrue(initialSettlementBuild(currentPlayer, new Point(7, 7), model.getSettlersBoard()));
        for (int diceValue : List.of(4, 4, 4)) {
            gameBankHandler.handoutResourcesOfTheRolledField(diceValue, model.getSettlersBoard());
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(GRAIN, 0, WOOL, 0, BRICK, 0, ORE, 6, LUMBER, 0),
                Faction.values()[1], Map.of(GRAIN, 0, WOOL, 0, BRICK, 0, ORE, 0, LUMBER, 0),
                Faction.values()[2], Map.of(GRAIN, 0, WOOL, 0, BRICK, 3, ORE, 0, LUMBER, 0));
        assertPlayerResourceCardStockEquals(model, expected);
    }

    /**
     * Tests whether a player can trade in resources with the bank and has the
     * correct number of resource cards afterwards. The test starts from game state
     * {@link ThreePlayerStandard#getAfterSetupPhase()} ()}.
     */
    @Test
    public void requirementCanTradeFourToOneWithBank() {
        GameDataContainer model = ThreePlayerStandard.getAfterSetupPhase();
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        final int initialLumberCount = 10;
        int cardsOffered = 4;
        int cardsReceived = 1;
        currentPlayer.addResourceCardToHand(LUMBER, initialLumberCount);

        gameBankHandler.tradeWithBankFourToOne(LUMBER, WOOL, currentPlayer);

        assertEquals(initialLumberCount - cardsOffered, currentPlayer.getResourceCardCountFor(LUMBER));
        assertEquals(currentPlayer.getResourceCardCountFor(WOOL), cardsReceived);
    }
}
