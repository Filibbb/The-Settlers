package ch.zhaw.catan.commands.build;

import ch.zhaw.catan.board.SettlersBoard;
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
public class BuildCityCommand extends AbstractBuildCommand {

    /**
     * Creates an instance of the BuildCityCommand
     *
     * @param currentPlayer the current player who wants to build.
     * @param settlersBoard the current SettlersBoard.
     * @author weberph5
     */
    public BuildCityCommand(Player currentPlayer, SettlersBoard settlersBoard) {
        super(currentPlayer, settlersBoard);
    }

    /**
     * Executes the BuildCity command
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        Point cityCoordinates = promptCoordinates("Corner");
        if (City.build(getCurrentPlayer(), cityCoordinates, getSettlersBoard())) {
            printMessage("City successfully built!");
        } else {
            printMessage("Building the city was not successful!");
        }
    }
}
