package ch.zhaw.catan.commands;

/**
 * Commands enum that contain available commands
 *
 * @author fupat002
 * @version 1.0.0
 */
public enum Commands {
    EXIT_COMMAND("EXIT", "EXIT: Exits the game early without a winner"),
    SHOW_HAND("SHOW HAND", "SHOW HAND: See how many resources you own"),
    SHOW_BOARD("SHOW BOARD", "SHOW BOARD: Show the game board"),
    BUILD_SETTLEMENT("BUILD SETTLEMENT", "BUILD SETTLEMENT: Build a settlement on a corner."),
    BUILD_ROAD("BUILD ROAD", "BUILD ROAD: Build a road adjacent to a settlement or another road."),
    BUILD_CITY("BUILD CITY", "BUILD CITY: Upgrade a settlement to a city"),
    TRADE_WITH_BANK("BANK TRADE", "BANK TRADE: Trade 4 of your resources to any resource"),
    END_TURN("END TURN", "END TURN: End the current turn"),
    SHOW_COMMANDS("SHOW COMMANDS", "SHOW COMMANDS : Show list of available commands.");

    private final String representation;
    private final String commandInfo;

    /**
     * The enum constructor in order to save the related values to the enums.
     *
     * @param representation the command representation
     * @param commandInfo    the description on what the command does
     */
    Commands(String representation, String commandInfo) {
        this.representation = representation;
        this.commandInfo = commandInfo;
    }

    /**
     * Get a command by its representation
     *
     * @param commandRepresentation the string representing the command
     * @return the command enum
     */
    public static Commands getCommandByRepresentation(final String commandRepresentation) {
        for (Commands value : Commands.values()) {
            if (value.representation.equals(commandRepresentation)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Get command information
     *
     * @return command information
     */
    public String getCommandInfo() {
        return commandInfo;
    }
}
