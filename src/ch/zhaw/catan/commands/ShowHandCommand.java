package ch.zhaw.catan.commands;

import ch.zhaw.catan.player.Player;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * Contains the logic for the ShowHandCommand
 *
 * @author weberph5
 */
public class ShowHandCommand implements Command {

    private final Player currentPlayer;

    /**
     * Creates an instance of ShowHandCommand
     *
     * @param turnOrderHandler the current turnOrderHandler
     * @author weberph5
     */
    public ShowHandCommand(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Executes the ShowHandCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        printMessage(currentPlayer.getResourceCardsInHand().toString());
    }
}
