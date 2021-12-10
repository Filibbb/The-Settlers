package ch.zhaw.catan.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ch.zhaw.catan.board.Resource.*;
import static ch.zhaw.catan.infrastructure.Structure.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class to check if player class works correctly and does the correct calculations etc.
 *
 * @author abuechi
 * @version 1.0.0
 */
class PlayerTest {

    private static final int LUMBER_COUNT = 3;
    private static final int ORE_COUNT = 1;
    private static final int TOTAL_RESOURCES = 5;

    private Player player;

    /**
     * Creates initial resource test setup for a player
     */
    @BeforeEach
    public void setUp() {
        player = new Player(Faction.RED);
        player.addResourceCardToHand(BRICK);
        player.addResourceCardToHand(LUMBER, LUMBER_COUNT);
        player.addResourceCardToHand(ORE, ORE_COUNT);
    }

    /**
     * Tests if total card count is correctly calculated
     */
    @Test
    public void totalResourceCardCountCorrect() {
        assertEquals(player.getTotalResourceCardCount(), TOTAL_RESOURCES);
    }

    /**
     * Tests if adding a single resource card to hand works as expected
     */
    @Test
    public void testAddSingleResourceCardToHand() {
        player.addResourceCardToHand(BRICK);
        assertEquals(player.getResourceCardsInHand().get(BRICK), 2);
    }

    /**
     * Tests if adding multiple cards of a resource card to hand works
     */
    @Test
    public void testAddResourceCardsToHand() {
        player.addResourceCardToHand(BRICK, 2);
        assertEquals(player.getResourceCardsInHand().get(BRICK), LUMBER_COUNT);
    }

    /**
     * Tests if adding a resource card with a negative count works
     * Must throw a runtime because it means the devs wrongly used the add method to remove.
     */
    @Test
    public void testAddResourceCardToHandWithNegativeCount() {
        assertThrows(RuntimeException.class, () -> player.addResourceCardToHand(BRICK, -2));
    }

    /**
     * Tests if getting resource count works for resources and resources that are not in hand
     */
    @Test
    public void getResourceCardCountFor() {
        assertEquals(player.getResourceCardCountFor(BRICK), ORE_COUNT);
        assertEquals(player.getResourceCardCountFor(LUMBER), LUMBER_COUNT);
        assertEquals(player.getResourceCardCountFor(GRAIN), 0);
    }

    /**
     * Tests if removing a single resource from hand works
     */
    @Test
    public void removeResourceCardFromHand() {
        player.removeResourceCardFromHand(LUMBER);
        assertEquals(player.getResourceCardCountFor(LUMBER), 2);
    }

    /**
     * Tests if increasing the built structure counter works as expected
     */
    @Test
    public void increaseBuiltStructures() {
        player.incrementStructureCounterFor(ROAD);
        player.incrementStructureCounterFor(ROAD);
        player.incrementStructureCounterFor(SETTLEMENT);
        player.incrementStructureCounterFor(SETTLEMENT);

        assertEquals(player.getBuiltStructuresCounter().get(ROAD), 2);
        assertEquals(player.getBuiltStructuresCounter().get(SETTLEMENT), 2);
        assertEquals(player.getBuiltStructuresCounter().get(CITY), 0);
    }

    /**
     * Tests if the check for enough remaining structures for a player to build works correctly
     */
    @Test
    public void checkStructureStock() {
        player.incrementStructureCounterFor(ROAD);
        player.incrementStructureCounterFor(ROAD);
        player.incrementStructureCounterFor(ROAD);

        final boolean stockStatusForRoads = player.hasEnoughInStructureStock(ROAD);
        assertTrue(stockStatusForRoads);
    }

    /**
     * Tests if the check for enough remaining structures for a player to build works if maxex out on a structure type
     */
    @Test
    public void checkStructureStockForNoMoreRemainingStructures() {
        for (int index = 0; index < CITY.getStockPerPlayer(); index++) {
            player.incrementStructureCounterFor(CITY);
        }

        final boolean stockStatusForCities = player.hasEnoughInStructureStock(CITY);
        assertFalse(stockStatusForCities);
    }

    /**
     * Tests the check to check if enough resources are available to build a road
     */
    @Test
    public void hasEnoughLiquidityForRoad() {
        final boolean hasEnoughForRoad = player.hasEnoughLiquidityFor(ROAD);
        assertTrue(hasEnoughForRoad);
    }

    /**
     * Tests the check if the player hasnt gotten enough resources to build a city
     */
    @Test
    public void hasNotEnoughLiquidityForCity() {
        final boolean hasEnoughForCity = player.hasEnoughLiquidityFor(CITY);
        assertFalse(hasEnoughForCity);
    }

    /**
     * Tests if deleting half of a all resources works correctly.
     */
    @Test
    public void deletesHalfOfResources() {
        player.deletesHalfOfResources();
        assertEquals(player.getTotalResourceCardCount(), 3);
    }
}