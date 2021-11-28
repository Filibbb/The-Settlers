package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;

/**
 * A wrapper object that contains a player and their related dice result in order to help to figure out player order.
 *
 * @author abuechi
 * @version 1.0.0
 */
public class DiceResult {
    private final Integer diceResult;
    private final Player player;

    /**
     * Creates a diceresult object with the player and the result of their dicethrow
     *
     * @param diceResult the result of a player dice
     * @param player     the player related to the dice throw.
     */
    public DiceResult(Integer diceResult, Player player) {
        this.diceResult = diceResult;
        this.player = player;
    }

    public Integer getDiceResult() {
        return diceResult;
    }

    public Player getPlayer() {
        return player;
    }

}
