package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.City;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;

public class BuildCityCommand implements Command {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    public BuildCityCommand(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    @Override
    public void execute() {
        int coordinateX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of corner");
        int coordinateY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of corner");
        Point cityCoordinates = new Point(coordinateX, coordinateY);
        Player player = turnOrderHandler.getCurrentPlayer();
        if (City.build(player, cityCoordinates, settlersBoard)) {
            textTerminal.println("City successfully built!");
        } else textTerminal.println("Building the city was not successful!");
    }
}
