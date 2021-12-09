package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptCoordinates;

/**
 * Contains the logic for the BuildSettlementCommand
 *
 * @author weberph5
 */
public class BuildSettlementCommand extends AbstractBuildCommand {
    /**
     * Creates an instance of the BuildSettlementCommand
     *
     * @param currentPlayer the current player that wants to build.
     * @param settlersBoard the current SettlersBoard.
     * @author weberph5
     */
    public BuildSettlementCommand(Player currentPlayer, SettlersBoard settlersBoard) {
        super(currentPlayer, settlersBoard);
    }

    /**
     * Executes the BuildSettlementCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Point settlementCoordinates = promptCoordinates("Corner");
        if (Settlement.build(getCurrentPlayer(), settlementCoordinates, getSettlersBoard())) {
            printMessage("Settlement successfully built!");
        } else {
            printMessage("Building the settlement was not successful!");
        }
    }
}
