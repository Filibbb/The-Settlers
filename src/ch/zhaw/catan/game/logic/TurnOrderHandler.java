package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A class that determines the turn order of the game
 *
 * @author abuechi, fupat002
 * @version 1.0.0
 */
public class TurnOrderHandler {
    private static final int FIRST = 0;
    private final Dice dice = new Dice();
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private List<Player> playerTurnOrder = new ArrayList<>();
    private Player currentPlayer;

    /**
     * The player with the highest number has to start.
     *
     * @param players the set with all players/participants.
     * @author fupat002, abuechi
     */
    public void determineInitialTurnOrder(List<Player> players) {
        final List<DiceResult> diceResults = dice.rollForPlayers(players);
        int highestDiceValue = getMaxDiceValue(diceResults);
        if (highestValueIsDuplicated(diceResults, highestDiceValue)) {
            textTerminal.println("Several players rolled the same number. Do the whole thing again.");
            determineInitialTurnOrder(players);
        } else {
            final List<Player> playerOrder = new ArrayList<>();
            diceResults.sort(Comparator.comparing(DiceResult::getDiceResult));
            for (DiceResult diceResult : diceResults) {
                playerOrder.add(diceResult.getPlayer());
            }
            textTerminal.println("Faction " + playerOrder.get(FIRST).getPlayerFaction() + " rolled a " + highestDiceValue + ". You are the lucky player who is allowed to start.");
            playerTurnOrder = playerOrder;
        }
    }

    /**
     * Switches to the next player in the defined sequence of players in regular turns.
     *
     * @author abuechi
     */
    public void switchToNextPlayer() {
        switchToPlayer(playerTurnOrder);
    }

    /**
     * Switches to the previous player in the defined sequence of players.
     *
     * @author abuechi
     */
    public void switchToPreviousPlayer() {
        List<Player> reversedTurnOrder = new ArrayList<>(playerTurnOrder);
        Collections.reverse(reversedTurnOrder);
        switchToPlayer(reversedTurnOrder);
    }

    private void switchToPlayer(List<Player> playerTurnOrder) {
        final int currentPlayerIndex = playerTurnOrder.indexOf(currentPlayer);
        final int nextPlayerIndex = currentPlayerIndex + 1;
        if (playerTurnOrder.size() > nextPlayerIndex) {
            currentPlayer = playerTurnOrder.get(nextPlayerIndex);
        } else {
            currentPlayer = playerTurnOrder.get(FIRST);
        }
    }

    private boolean highestValueIsDuplicated(List<DiceResult> diceThrows, int highestDiceValue) {
        int highestValueCounter = 0;
        for (DiceResult diceThrow : diceThrows) {
            if (diceThrow.getDiceResult() == highestDiceValue) {
                highestValueCounter++;
            }
        }
        return highestValueCounter > 1;
    }

    private int getMaxDiceValue(List<DiceResult> diceThrows) {
        int maxValue = diceThrows.get(0).getDiceResult();
        for (DiceResult diceThrow : diceThrows) {
            if (diceThrow.getDiceResult() > maxValue) {
                maxValue = diceThrow.getDiceResult();
            }
        }
        return maxValue;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
