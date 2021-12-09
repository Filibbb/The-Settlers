package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Road;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;

/**
 * Contains the logic for the BuildRoadCommand
 *
 * @author weberph5
 */
public class BuildRoadCommand implements Command {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
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
        int startCoordinateX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of the start point");
        int startCoordinateY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of corner of the start point");
        int endPointX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of the end point");
        int endPointY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of the end point");
        Point roadStartPoint = new Point(startCoordinateX, startCoordinateY);
        Point roadEndPoint = new Point(endPointX, endPointY);
        Player player = turnOrderHandler.getCurrentPlayer();
        if (Road.build(player, roadStartPoint, roadEndPoint, settlersBoard)) {
            textTerminal.println("Road successfully built!");
        } else textTerminal.println("Building the road was not successful!");
    }
}

