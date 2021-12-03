package ch.zhaw.catan;

import ch.zhaw.catan.board.Land;
import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoardTextView;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.games.ThreePlayerStandard;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.board.SettlersBoard.getDefaultLandPlacement;
import static ch.zhaw.catan.games.ThreePlayerStandard.INITIAL_SETTLEMENT_POSITIONS;
import static ch.zhaw.catan.games.ThreePlayerStandard.getAfterSetupPhase;
import static ch.zhaw.catan.infrastructure.Road.build;
import static ch.zhaw.catan.infrastructure.Settlement.build;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains some basic tests for the {@link SettlersGame} class
 * The class was changed to make it work with our logic and class hierarchy.
 *
 * <p></p>
 *
 * @author tebe, abuechi
 */
public class SettlersGameTestBasic {
    private final static int DEFAULT_WINPOINTS = 5;
    private final static int DEFAULT_NUMBER_OF_PLAYERS = 3;
    private List<DiceResult> diceResults = new ArrayList<>();

    @BeforeEach
    void setUp() {
        initDiceResults();
    }

    private void initDiceResults() {
        diceResults.add(new DiceResult(12, new Player(Faction.RED)));
        diceResults.add(new DiceResult(5, new Player(Faction.BLUE)));
        diceResults.add(new DiceResult(3, new Player(Faction.GREEN)));
        diceResults.add(new DiceResult(1, new Player(Faction.YELLOW)));
    }

    /**
     * Tests whether the functionality for switching to the next/previous player
     * works as expected for different numbers of players.
     *
     * @param numberOfPlayers the number of players
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void requirementPlayerSwitching(int numberOfPlayers) {
        diceResults = diceResults.subList(0, numberOfPlayers);
        TurnOrderHandler turnOrderHandler = new TurnOrderHandler();
        turnOrderHandler.determineInitialTurnOrder(diceResults);

        assertEquals(numberOfPlayers, turnOrderHandler.getPlayerTurnOrder().size(), "Wrong number of players returned by getPlayers()");

        //Switching forward

        for (int i = 0; i < numberOfPlayers; i++) {
            assertEquals(Faction.values()[i], turnOrderHandler.getCurrentPlayer().getPlayerFaction(),
                    "Player order does not match order of Faction.values()");
            turnOrderHandler.switchToNextPlayer();
        }
        assertEquals(Faction.values()[0], turnOrderHandler.getCurrentPlayer().getPlayerFaction(),
                "Player wrap-around from last player to first player did not work.");
        //Switching backward
        for (int i = numberOfPlayers - 1; i >= 0; i--) {
            turnOrderHandler.switchToPreviousPlayer();
            assertEquals(Faction.values()[i], turnOrderHandler.getCurrentPlayer().getPlayerFaction(),
                    "Switching players in reverse order does not work as expected.");
        }
    }

    /**
     * Tests whether the game board meets the required layout/land placement.
     */
    @Test
    public void requirementLandPlacementTest() {
        SettlersGame model = new SettlersGame(DEFAULT_WINPOINTS);

        model.addPlayersToGame(DEFAULT_NUMBER_OF_PLAYERS);
        assertEquals(getDefaultLandPlacement().size(), model.getBoard().getFields().size(), "Check if explicit init must be done (violates spec): "
                + "modify initializeSiedlerGame accordingly.");
        for (Map.Entry<Point, Land> e : getDefaultLandPlacement().entrySet()) {
            assertEquals(e.getValue(), model.getBoard().getField(e.getKey()),
                    "Land placement does not match default placement.");
        }
    }

    /**
     * Tests whether the {@link ThreePlayerStandard#getAfterSetupPhase(int)}} game board is not empty (returns
     * an object) at positions where settlements and roads have been placed.
     */
    @Test
    public void requirementSettlementAndRoadPositionsOccupiedThreePlayerStandard() {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        assertEquals(DEFAULT_NUMBER_OF_PLAYERS, model.getTurnOrderHandler().getPlayerTurnOrder().size());
        for (Player player : model.getTurnOrderHandler().getPlayerTurnOrder()) {
            final Faction playerFaction = player.getPlayerFaction();
            assertNotNull(model.getBoard().getCorner(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).first));
            assertNotNull(model.getBoard().getCorner(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).second));
            assertNotNull(model.getBoard().getEdge(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).first, ThreePlayerStandard.INITIAL_ROAD_ENDPOINTS.get(playerFaction).first));
            assertNotNull(model.getBoard().getEdge(INITIAL_SETTLEMENT_POSITIONS.get(playerFaction).second, ThreePlayerStandard.INITIAL_ROAD_ENDPOINTS.get(playerFaction).second));
        }
    }

    /**
     * Checks that the resource card payout for different dice values matches
     * the expected payout for the game state {@link ThreePlayerStandard#getAfterSetupPhase(int)}}.
     * <p>
     * Note, that for the test to work, the {@link Map} returned by {@link SettlersGame#throwDice(int)}
     * must contain a {@link List} with resource cards (empty {@link List}, if the player gets none)
     * for each of the players.
     *
     * @param diceValue the dice value
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 8, 9, 10, 11, 12})
    public void requirementDiceThrowResourcePayoutThreePlayerStandardTest(int diceValue) {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        Map<Faction, List<Resource>> expectd = ThreePlayerStandard.INITIAL_DICE_THROW_PAYOUT.get(diceValue);
        Map<Faction, List<Resource>> actual = model.throwDice(diceValue);
        assertEquals(ThreePlayerStandard.INITIAL_DICE_THROW_PAYOUT.get(diceValue), model.throwDice(diceValue));
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhase(int)}}.
     */
    @Test
    public void requirementPlayerResourceCardStockAfterSetupPhase() {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.INITIAL_PLAYER_CARD_STOCK);
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank(int)}}.
     */
    @Test
    public void requirementPlayerResourceCardStockAfterSetupPhaseAlmostEmptyBank() {
        SettlersGame model = ThreePlayerStandard.getAfterSetupPhaseAlmostEmptyBank(DEFAULT_WINPOINTS);
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.BANK_ALMOST_EMPTY_RESOURCE_CARD_STOCK);
    }

    /**
     * Tests whether the resource card stock of the players matches the expected stock
     * for the game state {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank(int)}}.
     */
    @Test
    public void requirementPlayerResourceCardStockPlayerOneReadyToBuildFifthSettlement() {
        SettlersGame model = ThreePlayerStandard.getPlayerOneReadyToBuildFifthSettlement(DEFAULT_WINPOINTS);
        assertPlayerResourceCardStockEquals(model, ThreePlayerStandard.PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_RESOURCE_CARD_STOCK);
    }

    /**
     * Throws each dice value except 7 once and tests whether the resource
     * card stock of the players matches the expected stock.
     */
    @Test
    public void requirementDiceThrowPlayerResourceCardStockUpdateTest() {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        for (int i : List.of(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)) {
            model.throwDice(i);
        }
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 1, Resource.WOOL, 2,
                        Resource.BRICK, 2, Resource.ORE, 1, Resource.LUMBER, 1),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 1, Resource.WOOL, 5, Resource.BRICK, 0,
                        Resource.ORE, 0, Resource.LUMBER, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 2,
                        Resource.ORE, 0, Resource.LUMBER, 1));

        assertPlayerResourceCardStockEquals(model, expected);
    }

    private void assertPlayerResourceCardStockEquals(SettlersGame model, Map<Faction, Map<Resource, Integer>> expected) {
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
     * Tests whether payout with multiple settlements of the same player at one field works
     * {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank(int)}.
     */
    @Test
    public void requirementTwoSettlementsSamePlayerSameFieldResourceCardPayout() {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        for (int diceValue : List.of(2, 6, 6, 11)) {
            model.throwDice(diceValue);
        }
        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        assertTrue(build(currentPlayer, new Point(6, 6), new Point(7, 7), model.getBoard()));
        assertTrue(build(currentPlayer, new Point(7, 7), model.getBoard()));
        assertEquals(List.of(Resource.ORE, Resource.ORE), model.throwDice(4).get(currentPlayer.getPlayerFaction()));
    }

    /**
     * Tests whether player two can trade in resources with the bank and has the
     * correct number of resource cards afterwards. The test starts from game state
     * {@link ThreePlayerStandard#getAfterSetupPhaseAlmostEmptyBank(int)}.
     */
    @Test
    public void requirementCanTradeFourToOneWithBank() {
        SettlersGame model = ThreePlayerStandard.getAfterSetupPhaseAlmostEmptyBank(DEFAULT_WINPOINTS);
        model.getTurnOrderHandler().switchToNextPlayer();

        final Player currentPlayer = model.getTurnOrderHandler().getCurrentPlayer();
        Map<Resource, Integer> expectedResourceCards = ThreePlayerStandard.BANK_ALMOST_EMPTY_RESOURCE_CARD_STOCK.get(currentPlayer.getPlayerFaction());
        assertEquals(expectedResourceCards.get(Resource.WOOL), currentPlayer.getResourceCardCountFor(Resource.WOOL));
        assertEquals(expectedResourceCards.get(Resource.LUMBER), currentPlayer.getResourceCardCountFor((Resource.LUMBER)));

        model.tradeWithBankFourToOne(Resource.WOOL, Resource.LUMBER);

        int cardsOffered = 4;
        int cardsReceived = 1;
        assertEquals(expectedResourceCards.get(Resource.WOOL) - cardsOffered, currentPlayer.getResourceCardCountFor(Resource.WOOL));
        assertEquals(expectedResourceCards.get(Resource.LUMBER) + cardsReceived, currentPlayer.getResourceCardCountFor(Resource.LUMBER));
    }

    /***
     * This test is not actually a test and should be removed. However,
     * we leave it in for you to have a quick and easy way to look at the
     * game board produced by {@link ThreePlayerStandard#getAfterSetupPhase(int)},
     * augmented by annotations, which you won't need since we do not ask for
     * more advanced trading functionality using harbours.
     */
    @Disabled
    @Test
    public void print() {
        SettlersGame model = getAfterSetupPhase(DEFAULT_WINPOINTS);
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(6, 6), "N ");
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(5, 7), "NE");
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(5, 9), "SE");
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(6, 10), "S ");
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(7, 7), "NW");
        model.getBoard().addFieldAnnotation(new Point(6, 8), new Point(7, 9), "SW");
        System.out.println(new SettlersBoardTextView(model.getBoard()).toString());
    }
}
