package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.GameBankHandler;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * The RollDiceCommand rolls the dice, handles the thief and hands out the resources of the rolled field.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class RollDice {
    private final Dice dice = new Dice();
    private final SettlersBoard settlersBoard;
    private final TurnOrderHandler turnOrderHandler;
    private final GameBankHandler gameBankHandler;

    /**
     * Creates the RollDiceCommand
     *
     * @param settlersBoard    The current settlers board
     * @param turnOrderHandler The current turn order handler with all players
     */
    public RollDice(final SettlersBoard settlersBoard, final TurnOrderHandler turnOrderHandler) {
        this.settlersBoard = settlersBoard;
        this.turnOrderHandler = turnOrderHandler;
        this.gameBankHandler = new GameBankHandler();
    }

    /**
     * Rolls dice and reacts accordingly to the diced value.
     */
    public void rollDice() {
        int diceValue = dice.throwDice();
        printMessage(turnOrderHandler.getCurrentPlayer().getPlayerFaction() + "rolled a " + diceValue);
        if (diceValue == 7) {
            sevenRolled();
        } else {
            gameBankHandler.handoutResourcesOfTheRolledField(diceValue, settlersBoard);
        }
    }

    private void sevenRolled() {
        for (Player player : turnOrderHandler.getPlayerTurnOrder()) {
            if (player.playerHasMoreThanSevenResources()) {
                player.deletesHalfOfResources();
                printMessage("The faction " + player.getPlayerFaction() + " had more than seven resources, that's why half of it has been deleted");
            }
        }
        final Thief thief = settlersBoard.getThief();
        thief.placeThiefOnField();
        thief.stealCardFromNeighbor(turnOrderHandler);
    }
}
