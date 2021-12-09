package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.commands.build.BuildCityCommand;
import ch.zhaw.catan.commands.build.BuildRoadCommand;
import ch.zhaw.catan.commands.build.BuildSettlementCommand;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.player.Player;

/**
 * Handles all Commands.
 *
 * @author abuechi
 */
public class CommandHandler {

    /**
     * Executes a command based on what was entered
     *
     * @param selectedCommand the user inputted command
     */
    public void executeCommand(Commands selectedCommand, TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        final Command commandToExecute = getSelectedCommand(selectedCommand, turnOrderHandler, settlersBoard);
        commandToExecute.execute();
    }

    private Command getSelectedCommand(Commands selectedCommand, TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        return switch (selectedCommand) {
            case SHOW_BOARD -> new ShowBoardCommand(settlersBoard);
            case SHOW_HAND -> new ShowHandCommand(currentPlayer);
            case BUILD_SETTLEMENT -> new BuildSettlementCommand(currentPlayer, settlersBoard);
            case BUILD_ROAD -> new BuildRoadCommand(currentPlayer, settlersBoard);
            case BUILD_CITY -> new BuildCityCommand(currentPlayer, settlersBoard);
            case END_TURN -> new EndTurnCommand(turnOrderHandler);
            case EXIT_COMMAND -> new QuitGameCommand();
            case SHOW_COMMANDS -> new ShowCommand();
        };
    }
}
