package ch.zhaw.catan;

/**
 * This {@link Enum} specifies the available factions in the game.
 *
 * @author tebe
 */
public enum Faction {
    RED("rr"),
    BLUE("bb"),
    GREEN("gg"),
    YELLOW("yy");

    private final String representation;

    Faction(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
