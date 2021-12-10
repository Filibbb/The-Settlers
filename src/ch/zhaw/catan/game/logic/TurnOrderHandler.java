package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;

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
    private List<Player> playerTurnOrder = new ArrayList<>();
    private Player currentPlayer;

    /**
     * The player with the highest number has to start.
     *
     * @param diceResults the set with all players/participants and their roll results.
     * @return boolean value expressing the success of determining the initial turnorder
     * @author fupat002, abuechi
     */
    public boolean determineInitialTurnOrder(final List<DiceResult> diceResults) {
        int highestDiceValue = getMaxDiceValue(diceResults);
        if (highestValueIsDuplicated(diceResults, highestDiceValue)) {
            return false;
        } else {
            final List<Player> playerOrder = new ArrayList<>();
            diceResults.sort(Comparator.comparing(DiceResult::getDiceResult));
            Collections.reverse(diceResults);
            for (DiceResult diceResult : diceResults) {
                playerOrder.add(diceResult.getPlayer());
            }
            playerTurnOrder = playerOrder;
            currentPlayer = playerOrder.get(FIRST);
            return true;
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

    /**
     * Checks if player did not change from previous current player
     *
     * @param player the player to check if its the same as the current
     * @return boolean if is changed
     */
    public boolean currentPlayerDidChange(Player player) {
        return !currentPlayer.equals(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayerTurnOrder() {
        return playerTurnOrder;
    }
}
