package ch.zhaw.catan.commands;

import ch.zhaw.catan.game.logic.TurnOrderHandler;

/**
 * Contains the logic for end turn command
 *
 * @author abuechi
 */
public class EndTurnCommand implements Command {
    private final TurnOrderHandler turnOrderHandler;

    /**
     * Creates an instance of endturn command
     *
     * @param turnOrderHandler the turnorderhandler for the current game instance
     */
    public EndTurnCommand(TurnOrderHandler turnOrderHandler) {
        this.turnOrderHandler = turnOrderHandler;
    }

    /**
     * Switches to the next player on turn end.
     */
    @Override
    public void execute() {
        turnOrderHandler.switchToNextPlayer();
    }
}
