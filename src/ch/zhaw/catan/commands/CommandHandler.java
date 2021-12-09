package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.player.Player;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * Handles all Commands.
 *
 * @author abuechi
 */
public class CommandHandler {

    /**
     * Executes a command based on what was entered
     *
     * @param command the user inputted command
     */
    public void executeCommand(Commands command, TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        switch (command) {
            case BUILD_SETTLEMENT:
                BuildSettlementCommand buildSettlementCommand = new BuildSettlementCommand(currentPlayer, settlersBoard);
                buildSettlementCommand.execute();
                break;
            case BUILD_ROAD:
                BuildRoadCommand buildRoadCommand = new BuildRoadCommand(currentPlayer, settlersBoard);
                buildRoadCommand.execute();
                break;
            case BUILD_CITY:
                BuildCityCommand buildCityCommand = new BuildCityCommand(currentPlayer, settlersBoard);
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
                printMessage("This command is not available. Use 'SHOW COMMANDS' for available commands.");
                break;
        }
    }
}
