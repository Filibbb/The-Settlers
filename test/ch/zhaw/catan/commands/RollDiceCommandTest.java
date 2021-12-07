package ch.zhaw.catan.commands;


import ch.zhaw.catan.SettlersGameTestBasic;
import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.games.GameDataContainer;
import ch.zhaw.catan.games.ThreePlayerStandard;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.games.ThreePlayerStandard.getAfterSetupPhase;
import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing class to check if RollDiceCommand works correctly.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class RollDiceCommandTest {
    private GameDataContainer model;
    private RollDiceCommand rollDiceCommand;

    /**
     * Creates initial dice roll test.
     */
    @BeforeEach
    public void setUp() {
        model = getAfterSetupPhase();
        rollDiceCommand = new RollDiceCommand(model.getSettlersBoard(), model.getTurnOrderHandler());
    }

    /**
     * Throws each dice value except 7 once and tests whether the resource
     * card stock of the players matches the expected stock.
     */
    @Test
    public void requirementDiceThrowPlayerResourceCardStockUpdateTest() {
        for (int i : List.of(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)) {
            rollDiceCommand.handoutResourcesOfTheRolledField(i);
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 1, Resource.WOOL, 1,
                        Resource.BRICK, 1, Resource.ORE, 1, Resource.LUMBER, 1),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 1, Resource.WOOL, 3, Resource.BRICK, 0,
                        Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 1,
                        Resource.ORE, 0, Resource.LUMBER, 1));
        SettlersGameTestBasic.assertPlayerResourceCardStockEquals(model, expected);
    }

    /**
     * Tests whether payout with multiple settlements of the same player at one field works
     */
    @Test
    public void requirementTwoSettlementsSamePlayerSameFieldResourceCardPayout() {
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        assertTrue(initialSettlementBuild(currentPlayer, new Point(7, 7), model.getSettlersBoard()));
        for (int diceValue : List.of(4, 4, 4)) {
            rollDiceCommand.handoutResourcesOfTheRolledField(diceValue);
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 0, Resource.WOOL, 0,
                        Resource.BRICK, 0, Resource.ORE, 6, Resource.LUMBER, 0),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 0,
                        Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 3,
                        Resource.ORE, 0, Resource.LUMBER, 0));
        SettlersGameTestBasic.assertPlayerResourceCardStockEquals(model, expected);
    }
}
