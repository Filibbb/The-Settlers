package ch.zhaw.catan.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;

/**
 * A helper Class that contains all printing methods and provides prompts
 *
 * @author abuechi
 */
public class CommandLineHandler {
    private static final int MIN_X_COORDINATE = 2;
    private static final int MAX_X_COORDINATE = 12;
    private static final int MIN_Y_COORDINATE = 3;
    private static final int MAX_Y_COORDINATE = 19;
    private static final TextIO TEXT_IO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> TEXT_TERMINAL = TEXT_IO.getTextTerminal();

    private CommandLineHandler() {
    }

    /**
     * A static helper method that prompts the user to enter an x and y coordinate
     *
     * @return a point with the entered coordinates
     * @author abuechi
     */
    public static Point promptCoordinates(String coordinateContext) {
        int coordinateX = promptMinMaxValueInt("Enter x coordinate( " + coordinateContext + " ): ", MIN_X_COORDINATE, MAX_X_COORDINATE);
        int coordinateY = promptMinMaxValueInt("Enter y coordinate( " + coordinateContext + " ): ", MIN_Y_COORDINATE, MAX_Y_COORDINATE);
        return new Point(coordinateX, coordinateY);
    }

    /**
     * Prompts the user for entering an action
     *
     * @return the string entered for further validation.
     * @author abuechi
     */
    public static String promptNextUserAction() {
        return promptNextUserInput("Please enter your next action:");
    }

    /**
     * Prompts the user to enter something depending on the context
     *
     * @param contextMessage the context that is displayed as a hint for the user on what to enter
     * @return the entered string
     */
    public static String promptNextUserInput(String contextMessage) {
        return TEXT_IO.newStringInputReader().read(contextMessage);
    }

    /**
     * Prompts the user for entering a context specific number between the specified range
     *
     * @param promptMessage the message that is displayed upon prompt
     * @param minValue      minimum that needs to be entered
     * @param maxValue      maximum that needs to be entered
     * @return an integer in between specified min / max value
     * @author abuechi
     */
    public static int promptMinMaxValueInt(String promptMessage, int minValue, int maxValue) {
        return TEXT_IO.newIntInputReader().withMinVal(minValue).withMaxVal(maxValue).read(promptMessage);
    }

    /**
     * Prints the given message as is.
     *
     * @author abuechi
     */
    public static void printMessage(String message) {
        TEXT_TERMINAL.println(message);
    }
}
