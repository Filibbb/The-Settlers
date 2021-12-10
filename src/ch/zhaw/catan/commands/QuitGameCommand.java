package ch.zhaw.catan.commands;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * Exit Command
 *
 * @author abuechi
 */
public class QuitGameCommand implements Command {

    private static final int EXIT_CODE = 1;

    /**
     * Exits the game on execution
     */
    @Override
    public void execute() {
        printMessage("Due to early quit of one player. Game can not be completed and ends without a winner. Thank you for playing!");
        System.exit(EXIT_CODE);
    }
}
