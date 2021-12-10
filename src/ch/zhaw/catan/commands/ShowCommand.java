package ch.zhaw.catan.commands;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * Command class for the ShowCommand command
 *
 * @author abuechi
 * @version 1.0.0
 */
public class ShowCommand implements Command {

    /**
     * Shows a list and description of all available commands.
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        printMessage("Available Commands (Case sensitive!):");
        printMessage("");
        for (Commands commands : Commands.values()) {
            printMessage(commands.getCommandInfo());
        }
        printMessage("");
    }
}
