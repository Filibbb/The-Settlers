package ch.zhaw.catan.commands.build;

import ch.zhaw.catan.board.SettlersBoard;
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
public class BuildRoadCommand extends AbstractBuildCommand {

    /**
     * Creates an instance of the BuildRoadCommand
     *
     * @param currentPlayer the current player that wants to build.
     * @param settlersBoard the current SettlersBoard.
     * @author weberph5
     */
    public BuildRoadCommand(Player currentPlayer, SettlersBoard settlersBoard) {
        super(currentPlayer, settlersBoard);
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
        if (Road.build(getCurrentPlayer(), roadStartPoint, roadEndPoint, getSettlersBoard())) {
            printMessage("Road successfully built!");
        } else {
            printMessage("Building the road was not successful!");
        }
    }
}

