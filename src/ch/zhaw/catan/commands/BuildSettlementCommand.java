package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
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
public class BuildSettlementCommand implements Command {
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    /**
     * Creates an instance of the BuildSettlementCommand
     *
     * @param turnOrderHandler the TurnOrderHandler for the current game.
     * @param settlersBoard    the current SettlersBoard.
     * @author weberph5
     */
    public BuildSettlementCommand(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    /**
     * Executes the BuildSettlementCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Point settlementCoordinates = promptCoordinates("Corner");
        Player player = turnOrderHandler.getCurrentPlayer();
        if (Settlement.build(player, settlementCoordinates, settlersBoard)) {
            printMessage("Settlement successfully built!");
        } else {
            printMessage("Building the settlement was not successful!");
        }
    }
}
