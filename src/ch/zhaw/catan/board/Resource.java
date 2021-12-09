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

    /**
     * Checks if resource is a valid resource
     *
     * @param representation the representation to check
     * @return if representation is a valid resource
     * @author abuechi
     */
    public static boolean isValidResource(String representation) {
        for (Resource resource : Resource.values()) {
            if (resource.representation.equals(representation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets resource for representation
     *
     * @param representation the representation to check
     * @return resource if valid representation given
     * @author abuechi
     */
    public static Resource getResourceByRepresentation(String representation) {
        for (Resource resource : Resource.values()) {
            if (resource.representation.equals(representation)) {
                return resource;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return representation;
    }
}
