package ch.zhaw.catan.commands;

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

    public CommandHandler(TurnOrderHandler turnOrderHandler) {
        this.turnOrderHandler = turnOrderHandler;
    }

    public void executeCommand(Commands command) {
        switch (command) {
            case ROLL_DICE:
                //TODO: implement throwDice with distribute resources.
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
