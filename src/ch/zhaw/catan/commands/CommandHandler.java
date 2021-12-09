package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Handles all Commands.
 */
public class CommandHandler {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    public CommandHandler(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    public void executeCommand(Commands command) {
        switch (command) {
            case SHOW_BOARD:
                ShowBoardCommand showBoardCommand = new ShowBoardCommand(settlersBoard);
                showBoardCommand.execute();
                break;
            case SHOW_HAND:
                ShowHandCommand showHandCommand = new ShowHandCommand(turnOrderHandler);
                showHandCommand.execute();
                break;
            case BUILD_SETTLEMENT:
                BuildSettlementCommand buildSettlementCommand = new BuildSettlementCommand(turnOrderHandler, settlersBoard);
                buildSettlementCommand.execute();
                break;
            case BUILD_ROAD:
                BuildRoadCommand buildRoadCommand = new BuildRoadCommand(turnOrderHandler, settlersBoard);
                buildRoadCommand.execute();
                break;
            case BUILD_CITY:
                BuildCityCommand buildCityCommand = new BuildCityCommand(turnOrderHandler, settlersBoard);
                buildCityCommand.execute();
                break;
            case END_TURN:
                final EndTurnCommand endTurnCommand = new EndTurnCommand(turnOrderHandler);
                endTurnCommand.execute();
            case SHOW_COMMANDS:
                final ShowCommand showCommand = new ShowCommand();
                showCommand.execute();
                break;
            default:
                textTerminal.println("This command is not available. Use 'SHOW COMMANDS' for available commands.");
                break;
        }
    }
}
