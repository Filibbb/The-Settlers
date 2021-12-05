package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests if {TurnOrderHandler} works correctly
 *
 * @author abuechi
 */
class TurnOrderHandlerTest {
    private List<DiceResult> diceResults = new ArrayList<>();
    private TurnOrderHandler turnOrderHandler;

    @BeforeEach
    void setUp() {
        initDiceResults();
        turnOrderHandler = new TurnOrderHandler();
    }

    private void initDiceResults() {
        diceResults.add(new DiceResult(12, new Player(Faction.RED)));
        diceResults.add(new DiceResult(5, new Player(Faction.BLUE)));
        diceResults.add(new DiceResult(3, new Player(Faction.GREEN)));
        diceResults.add(new DiceResult(1, new Player(Faction.YELLOW)));
    }

    /**
     * Tests whether turn order determination based on dice results is correct.
     */
    @Test
    public void testTurnOrderDetermination() {
        turnOrderHandler.determineInitialTurnOrder(diceResults);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(0).getPlayerFaction(), Faction.RED);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(1).getPlayerFaction(), Faction.BLUE);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(2).getPlayerFaction(), Faction.GREEN);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(3).getPlayerFaction(), Faction.YELLOW);
    }

    /**
     * Tests whether turn order determination based on dice results is correct with unsorted results.
     */
    @Test
    public void testTurnOrderDeterminationForUnsortedResults() {
        List<DiceResult> customDiceResult = new ArrayList<>();

        customDiceResult.add(new DiceResult(3, new Player(Faction.GREEN)));
        customDiceResult.add(new DiceResult(1, new Player(Faction.YELLOW)));
        customDiceResult.add(new DiceResult(5, new Player(Faction.BLUE)));
        customDiceResult.add(new DiceResult(12, new Player(Faction.RED)));

        turnOrderHandler.determineInitialTurnOrder(customDiceResult);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(0).getPlayerFaction(), Faction.RED);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(1).getPlayerFaction(), Faction.BLUE);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(2).getPlayerFaction(), Faction.GREEN);
        assertEquals(turnOrderHandler.getPlayerTurnOrder().get(3).getPlayerFaction(), Faction.YELLOW);
    }


    /**
     * Tests whether the functionality for switching to the next/previous player
     * works as expected for different numbers of players.
     *
     * @param numberOfPlayers the number of players
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testPlayerSwitching(int numberOfPlayers) {
        diceResults = diceResults.subList(0, numberOfPlayers);

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

}