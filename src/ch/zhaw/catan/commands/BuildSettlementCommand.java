package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;

/**
 * Contains the logic for the BuildSettlementCommand
 *
 * @author weberph5
 */
public class BuildSettlementCommand implements Command {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
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
        int coordinateX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of corner");
        int coordinateY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of corner");
        Point settlementCoordinates = new Point(coordinateX, coordinateY);
        Player player = turnOrderHandler.getCurrentPlayer();
        if (Settlement.build(player, settlementCoordinates, settlersBoard)) {
            textTerminal.println("Settlement successfully built!");
        } else textTerminal.println("Building the settlement was not successful!");
    }
}
