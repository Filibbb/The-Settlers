package ch.zhaw.catan.board;

/**
 * This {@link Enum} specifies the available lands in the game. Some land types
 * produce resources (e.g., {@link Land#FOREST}, others do not (e.g.,
 * {@link Land#WATER}.
 *
 * @author tebe
 */
public enum Land {
    FOREST(Resource.LUMBER),
    PASTURE(Resource.WOOL),
    FIELDS(Resource.GRAIN),
    MOUNTAIN(Resource.ORE),
    HILLS(Resource.BRICK),
    WATER("~~"),
    DESERT("--");

    private Resource resource = null;
    private final String representation;

    Land(Resource resource) {
        this(resource.toString());
        this.resource = resource;
    }

    Land(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    /**
     * Returns the {@link Resource} that this land provides or null,
     * if it does not provide any.
     *
     * @return the {@link Resource} or null
     */
    public Resource getResource() {
        return resource;
    }
}