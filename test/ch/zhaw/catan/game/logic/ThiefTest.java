package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.GameBankHandler;
import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.utilities.GameDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
import static ch.zhaw.catan.utilities.PlayerResourceStockUtility.assertPlayerResourceCardStockEquals;
import static ch.zhaw.catan.utilities.ThreePlayerStandard.getAfterSetupPhase;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThiefTest {
    private final static Point THIEF_POSITION = new Point(6, 8);
    private final GameBankHandler gameBankHandler = new GameBankHandler();
    private GameDataContainer model;
    private Thief thief;

    /**
     * Creates thief test.
     */
    @BeforeEach
    public void setUp() {
        model = getAfterSetupPhase();
        thief = model.getSettlersBoard().getThief();
    }

    /**
     * Tests whether the thief works.
     */
    @Test
    public void stealCardFromNeighborAfterThiefPlacementTest() {
        final TurnOrderHandler turnOrderHandler = model.getTurnOrderHandler();

        for (Player player : turnOrderHandler.getPlayerTurnOrder()) {
            player.addResourceCardToHand(Resource.ORE);
        }
        assertTrue(initialSettlementBuild(model.getTurnOrderHandler().getPlayerTurnOrder().get(1), new Point(7, 7), model.getSettlersBoard()));
        thief.setThiefPosition(THIEF_POSITION);
        thief.stealCardFromNeighbor(model.getTurnOrderHandler());

        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.RED, Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 0, Resource.ORE, 2, Resource.LUMBER, 0),
                Faction.BLUE, Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 0, Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.GREEN, Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 0, Resource.ORE, 1, Resource.LUMBER, 0));
        assertPlayerResourceCardStockEquals(model, expected);
    }


    /**
     * Tests whether the payout/non-payout works when the thief blocks the field.
     */
    @Test
    public void blockedFieldByThief() {
        thief.setThiefPosition(THIEF_POSITION);
        for (int i : List.of(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)) {
            gameBankHandler.handoutResourcesOfTheRolledField(i, model.getSettlersBoard());
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 1, Resource.WOOL, 1,
                        Resource.BRICK, 1, Resource.ORE, 0, Resource.LUMBER, 1),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 1, Resource.WOOL, 3, Resource.BRICK, 0,
                        Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 1,
                        Resource.ORE, 0, Resource.LUMBER, 1));
        assertPlayerResourceCardStockEquals(model, expected);
    }

    /**
     * Tests whether the payout/non-payout works when the thief blocks a field with multiple settlements.
     */
    @Test
    public void multipleSettlementsBlockedByThief() {
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        assertTrue(initialSettlementBuild(currentPlayer, new Point(7, 7), model.getSettlersBoard()));
        thief.setThiefPosition(THIEF_POSITION);
        for (int diceValue : List.of(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)) {
            gameBankHandler.handoutResourcesOfTheRolledField(diceValue, model.getSettlersBoard());
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 2, Resource.WOOL, 2,
                        Resource.BRICK, 1, Resource.ORE, 0, Resource.LUMBER, 1),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 1, Resource.WOOL, 3, Resource.BRICK, 0,
                        Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 1,
                        Resource.ORE, 0, Resource.LUMBER, 1));
        assertPlayerResourceCardStockEquals(model, expected);
    }
}
