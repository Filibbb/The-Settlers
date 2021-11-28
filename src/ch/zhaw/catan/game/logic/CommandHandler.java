package ch.zhaw.catan.game.logic;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Handles all Commands.
 */
public class CommandHandler {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private Commands command;

    public void executeCommand(){
        switch(command){
            case ROLL_DICE:
                //TODO: implement throwDice with distribute resources.
                break;
            case SHOW_COMMANDS:
                final ShowCommand showCommand = new ShowCommand();
                showCommand.execute();
                break;
            default:
                textTerminal.println("This command is not available. Use 'SHOW COMMANDS' for available commands.");
                break;
        }
    }

    public void setCommand(String userInput){
        this.command = Commands.getCommandByRepresentation(userInput);
    }

    public Commands getCommand(){
        return command;
    }
}
