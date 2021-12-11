package ch.zhaw.catan.board;

import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.utilities.GameDataContainer;
import ch.zhaw.catan.utilities.ThreePlayerStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static ch.zhaw.catan.board.BoardUtil.getDefaultLandTilePlacement;
import static ch.zhaw.catan.board.Resource.WOOL;
import static ch.zhaw.catan.utilities.ThreePlayerStandard.INITIAL_SETTLEMENT_POSITIONS;
import static ch.zhaw.catan.utilities.ThreePlayerStandard.getAfterSetupPhase;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {Settlerboard}
 *
 * @author abuechi, tebe
 */
class SettlersBoardTest {
    private final static Point WATER_FIELD = new Point(13, 11);
    private final static Point LAND_FIELD =  new Point(8, 14);
    private final static int DEFAULT_NUMBER_OF_PLAYERS = 3;
    private SettlersBoard settlersBoard;

    @BeforeEach
    public void setUp() {
        settlersBoard = new SettlersBoard();
    }

    /**
     * Tests whether the game board meets the required layout/land placement.
     */
    @Test
    public void requirementLandPlacementTest() {
        assertEquals(getDefaultLandTilePlacement().size(), settlersBoard.getFields().size(), "Check if explicit init must be done (violates spec): "
                + "modify initializeSiedlerGame accordingly.");
        for (Map.Entry<Point, Land> e : getDefaultLandTilePlacement().entrySet()) {
            assertEquals(e.getValue(), settlersBoard.getField(e.getKey()),
                    "Land placement does not match default placement.");
        }
    }

    /**
     * Tests whether the {@link ThreePlayerStandard#getAfterSetupPhase()}} game board is not empty (returns
     * an object) at positions where settlements and roads have been placed.
     */
    @Test
    public void requirementSettlementAndRoadPositionsOccupiedThreePlayerStandard() {
        GameDataContainer model = getAfterSetupPhase();
        final TurnOrderHandler turnOrderHandler = model.getTurnOrderHandler();
        assertEquals(DEFAULT_NUMBER_OF_PLAYERS, turnOrderHandler.getPlayerTurnOrder().size());
        final SettlersBoard settlersBoard = model.getSettlersBoard();

        for (Player player : turnOrderHandler.getPlayerTurnOrder()) {
            final Faction playerFaction = player.getPlayerFaction();
            assertNotNull(settlersBoard.getCorner(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).first));
            assertNotNull(settlersBoard.getCorner(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).second));
            assertNotNull(settlersBoard.getEdge(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).first, ThreePlayerStandard.INITIAL_ROAD_ENDPOINTS.get(playerFaction).first));
            assertNotNull(settlersBoard.getEdge(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).second, ThreePlayerStandard.INITIAL_ROAD_ENDPOINTS.get(playerFaction).second));
        }
    }

    /**
     * Tests whether a field is water or not.
     */
    @Test
    public void testsIfAFieldIsWater(){
        assertTrue(settlersBoard.isWater(WATER_FIELD));
        assertFalse(settlersBoard.isWater(LAND_FIELD));
    }

    /**
     * Test whether a player has a neighbor with resources or not.
     */
    @Test
    public void testIfAPlayerHasANeighborWithResources(){
        final GameDataContainer model = getAfterSetupPhase();
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        final Player playerTwo = model.getTurnOrderHandler().getPlayerTurnOrder().get(1);
        playerTwo.addResourceCardToHand(WOOL, 5);
        assertTrue(model.getSettlersBoard().hasNeighborWithResource(new Point(10, 14), currentPlayer));
        assertFalse(model.getSettlersBoard().hasNeighborWithResource(new Point(3, 11), playerTwo));
    }
}