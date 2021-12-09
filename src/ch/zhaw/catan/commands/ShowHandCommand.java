package ch.zhaw.catan.commands;

import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Contains the logic for the ShowHandCommand
 *
 * @author weberph5
 */
public class ShowHandCommand implements Command {

    private final TurnOrderHandler turnOrderHandler;
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * Creates an instance of ShowHandCommand
     *
     * @param turnOrderHandler the current turnOrderHandler
     * @author weberph5
     */
    public ShowHandCommand(TurnOrderHandler turnOrderHandler) {
        this.turnOrderHandler = turnOrderHandler;
    }

    /**
     * Executes the ShowHandCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        textTerminal.println(currentPlayer.getResourceCardsInHand().toString());
    }
}
