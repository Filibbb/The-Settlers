package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.City;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptCoordinates;

/**
 * Contains the logic for the BuildCity command
 *
 * @author weberph5
 */
public class BuildCityCommand implements Command {
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    /**
     * Creates an instance of the BuildCityCommand
     *
     * @param turnOrderHandler the TurnOrderHandler for the current game.
     * @param settlersBoard    the current SettlersBoard.
     * @author weberph5
     */
    public BuildCityCommand(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    /**
     * Executes the BuildCity command
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Point cityCoordinates = promptCoordinates("Corner");
        Player player = turnOrderHandler.getCurrentPlayer();
        if (City.build(player, cityCoordinates, settlersBoard)) {
            printMessage("City successfully built!");
        } else {
            printMessage("Building the city was not successful!");
        }
    }
}
