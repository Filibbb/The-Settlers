package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.Resource;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * This enum models the different structures that can be built.
 * <p>
 * The enum provides information about the cost of a structure and how many of
 * these structures are available per player.
 * </p>
 *
 * @author tebe, abuechi
 */
public enum Structure {
    SETTLEMENT(List.of(Resource.LUMBER, Resource.BRICK, Resource.WOOL, Resource.GRAIN), 5),
    CITY(List.of(Resource.ORE, Resource.ORE, Resource.ORE, Resource.GRAIN, Resource.GRAIN), 4),
    ROAD(List.of(Resource.LUMBER, Resource.BRICK), 15);

    private final List<Resource> costs;
    private final int stockPerPlayer;

    Structure(List<Resource> costs, int stockPerPlayer) {
        this.costs = costs;
        this.stockPerPlayer = stockPerPlayer;
    }

    /**
     * Returns the build costs of this structure.
     *
     * @return the build costs in terms of the number of resource cards per resource type
     */
    public Map<Resource, Long> getCostsAsMap() {
        return costs.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns the number of pieces that are available of a certain structure (per
     * player). For example, there are 7 pieces of the structure {@link Structure#ROAD} per player.
     *
     * @return the stock per player
     */
    public int getStockPerPlayer() {
        return stockPerPlayer;
    }
}