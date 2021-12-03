package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.games.ThreePlayerStandard;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ch.zhaw.catan.board.Structure.ROAD;
import static ch.zhaw.catan.board.Structure.SETTLEMENT;
import static ch.zhaw.catan.games.ThreePlayerStandard.INITIAL_ROAD_ENDPOINTS;
import static ch.zhaw.catan.games.ThreePlayerStandard.INITIAL_SETTLEMENT_POSITIONS;
import static ch.zhaw.catan.infrastructure.Road.build;
import static ch.zhaw.catan.infrastructure.Road.initialBuild;
import static ch.zhaw.catan.infrastructure.Settlement.build;
import static ch.zhaw.catan.infrastructure.Settlement.initialBuild;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that contains all sort of infrastructure related tests including building in foundation phase and similar
 *
 * @author abuechi
 */
public class BuildingInfrastructureTests {

    private final static int DEFAULT_TOTAL_HAND_CARDS = 25;
    private final TurnOrderHandler turnOrderHandler = new TurnOrderHandler();
    private final List<DiceResult> diceResults = new ArrayList<>();
    private SettlersBoard settlersBoard;

    @BeforeEach
    void setUp() {
        initDiceResults();
        turnOrderHandler.determineInitialTurnOrder(diceResults);
        addResourcesToPlayers();
        settlersBoard = new SettlersBoard();

    }

    private void initDiceResults() {
        diceResults.add(new DiceResult(12, new Player(Faction.RED)));
        diceResults.add(new DiceResult(5, new Player(Faction.BLUE)));
        diceResults.add(new DiceResult(3, new Player(Faction.GREEN)));
    }

    private void addResourcesToPlayers() {
        for (Player player : turnOrderHandler.getPlayerTurnOrder()) {
            player.addResourceCardToHand(Resource.LUMBER, 5);
            player.addResourceCardToHand(Resource.GRAIN, 5);
            player.addResourceCardToHand(Resource.ORE, 5);
            player.addResourceCardToHand(Resource.WOOL, 5);
            player.addResourceCardToHand(Resource.BRICK, 5);
        }
    }

    private void setupInitialPhase() {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        final Faction faction = currentPlayer.getPlayerFaction();
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, settlersBoard));
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, INITIAL_ROAD_ENDPOINTS.get(faction).first, settlersBoard));

        turnOrderHandler.switchToNextPlayer();

        currentPlayer = turnOrderHandler.getCurrentPlayer();
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, settlersBoard));
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, INITIAL_ROAD_ENDPOINTS.get(faction).second, settlersBoard));
    }

    /**
     * Tests the requirement of setting a road in setup phase for free.
     */
    @Test
    public void requirementBuildRoadInSetupPhase() {
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        final Faction faction = currentPlayer.getPlayerFaction();
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, settlersBoard));
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, INITIAL_ROAD_ENDPOINTS.get(faction).first, settlersBoard));
        assertEquals(currentPlayer.getTotalResourceCardCount(), DEFAULT_TOTAL_HAND_CARDS);
    }

    /**
     * Tests the requirement of setting a road but it has to be next to a settlement in setup phase for free.
     */
    @Test
    public void requirementRoadMustBeBuiltAdjacentToSettlement() {
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        final Faction faction = currentPlayer.getPlayerFaction();
        assertFalse(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, INITIAL_ROAD_ENDPOINTS.get(faction).second, settlersBoard));

        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, settlersBoard));
        assertFalse(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, INITIAL_ROAD_ENDPOINTS.get(faction).first, settlersBoard));
        assertEquals(currentPlayer.getTotalResourceCardCount(), DEFAULT_TOTAL_HAND_CARDS);
    }


    /**
     * Tests whether current player can build two roads after foundation phase.
     * Test does not require a game instance instead requires a board instance and the turnorderhandler for the current player.
     */
    @Test
    public void requirementBuildRoad() {
        setupInitialPhase();
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        assertTrue(build(currentPlayer, new Point(6, 6), new Point(6, 4), settlersBoard));
        assertTrue(build(currentPlayer, new Point(6, 4), new Point(7, 3), settlersBoard));
        assertEquals(currentPlayer.getBuiltStructuresCounter().get(ROAD), 3);
    }


    /**
     * Tests the requirement of setting a settlement in setup phase for free.
     */
    @Test
    public void requirementBuildSettlementInSetupPhase() {
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        final Faction faction = currentPlayer.getPlayerFaction();
        assertTrue(initialBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, settlersBoard));
        assertEquals(currentPlayer.getTotalResourceCardCount(), DEFAULT_TOTAL_HAND_CARDS);
    }

    /**
     * Tests whether current player can build a settlement starting in game state that must be adjacent to a road and therefore no costs are applied
     */
    @Test
    public void requirementSettlementMustBeBuiltAdjacentToRoadAfterSetup() {
        setupInitialPhase();
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        assertFalse(build(currentPlayer, new Point(9, 13), settlersBoard));
        assertEquals(currentPlayer.getBuiltStructuresCounter().get(SETTLEMENT), 1);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.LUMBER), 5);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.BRICK), 5);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.WOOL), 5);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.GRAIN), 5);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.ORE), 5);
    }

    /**
     * Tests whether current player can build a settlement after foundation phase and costs are applied correctly
     */
    @Test
    public void requirementBuildSettlement() {
        setupInitialPhase();
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        assertTrue(build(currentPlayer, new Point(9, 15), new Point(9, 13), settlersBoard));
        assertTrue(build(currentPlayer, new Point(9, 13), settlersBoard));
        assertEquals(currentPlayer.getBuiltStructuresCounter().get(SETTLEMENT), 2);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.LUMBER), 3); //cost because of road building
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.BRICK), 3); //cost because of road building
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.WOOL), 4);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.GRAIN), 4);
        assertEquals(currentPlayer.getResourceCardCountFor(Resource.ORE), 5);
    }

    /**
     * Tests whether player one can build a city starting in game state
     * {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank(int)}.
     */
    @Test
    public void requirementBuildCity() {
        setupInitialPhase();
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        assertFalse(true); //TODO implement test once building city is done
    }
}
