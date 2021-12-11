package ch.zhaw.catan.commands.build;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.commands.Command;
import ch.zhaw.catan.player.Player;

/**
 * Abstract class for commands should contain
 *
 * @author abuechi
 */
public abstract class AbstractBuildCommand implements Command {
    private final Player currentPlayer;
    private final SettlersBoard settlersBoard;

    /**
     * Abstract constructor for shared command fields
     *
     * @param currentPlayer the currentPlayer that wants to execute a build command
     * @param settlersBoard the current SettlersBoard.
     * @author abuechi
     */
    protected AbstractBuildCommand(Player currentPlayer, SettlersBoard settlersBoard) {
        this.currentPlayer = currentPlayer;
        this.settlersBoard = settlersBoard;
    }

    /**
     * Retrieves the current instance of the settlersboard
     *
     * @return the board instance
     */
    protected SettlersBoard getSettlersBoard() {
        return settlersBoard;
    }

    /**
     * Gets the current player
     *
     * @return the current player
     */
    protected Player getCurrentPlayer() {
        return currentPlayer;
    }
}
