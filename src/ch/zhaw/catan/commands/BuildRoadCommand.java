package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Road;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptCoordinates;

/**
 * Contains the logic for the BuildRoadCommand
 *
 * @author weberph5
 */
public class BuildRoadCommand implements Command {
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    /**
     * Creates an instance of the BuildRoadCommand
     *
     * @param turnOrderHandler the TurnOrderHandler for the current game.
     * @param settlersBoard    the current SettlersBoard.
     * @author weberph5
     */
    public BuildRoadCommand(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    /**
     * Executes the BuildRoadCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Point roadStartPoint = promptCoordinates("Road start point");
        Point roadEndPoint = promptCoordinates("Road end point");
        Player player = turnOrderHandler.getCurrentPlayer();
        if (Road.build(player, roadStartPoint, roadEndPoint, settlersBoard)) {
            printMessage("Road successfully built!");
        } else {
            printMessage("Building the road was not successful!");
        }
    }
}

