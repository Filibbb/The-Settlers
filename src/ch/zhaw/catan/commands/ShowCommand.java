package ch.zhaw.catan.commands;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Command class for the ShowCommand command
 *
 * @author abuechi
 * @version 1.0.0
 */
public class ShowCommand {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * Shows a list and description of all available commands.
     *
     * @author weberph5
     */
    public void execute() {
        textTerminal.println("Available Commands (Case sensitive!):");
        textTerminal.println("");
        for (Commands commands : Commands.values()) {
            textTerminal.println(commands.getCommandInfo());
        }
        textTerminal.println("");
    }
}
