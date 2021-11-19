package ch.zhaw.catan.board;

/**
 * This {@link Enum} specifies the available resource types in the game.
 *
 * @author tebe
 */
public enum Resource {
    GRAIN("GR"),
    WOOL("WL"),
    LUMBER("LU"),
    ORE("OR"),
    BRICK("BR");

    private final String representation;

    Resource(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
