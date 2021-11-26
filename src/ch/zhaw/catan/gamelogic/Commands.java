package ch.zhaw.catan.gamelogic;

public enum Commands {
    ROLLDICE("ROLLDICE","Roll the Dice");

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
