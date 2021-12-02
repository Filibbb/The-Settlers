package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.Structure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        player.addResourceCardToHand(Resource.BRICK);
        player.addResourceCardToHand(Resource.LUMBER, LUMBER_COUNT);
        player.addResourceCardToHand(Resource.ORE, ORE_COUNT);
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
        player.addResourceCardToHand(Resource.BRICK);
        assertEquals(player.getResourceCardsInHand().get(Resource.BRICK), 2);
    }

    /**
     * Tests if adding multiple cards of a resource card to hand works
     */
    @Test
    public void testAddResourceCardsToHand() {
        player.addResourceCardToHand(Resource.BRICK, 2);
        assertEquals(player.getResourceCardsInHand().get(Resource.BRICK), LUMBER_COUNT);
    }

    /**
     * Tests if adding a resource card with a negative count works
     * Must throw a runtime because it means the devs wrongly used the add method to remove.
     */
    @Test
    public void testAddResourceCardToHandWithNegativeCount() {
        assertThrows(RuntimeException.class, () -> player.addResourceCardToHand(Resource.BRICK, -2));
    }

    /**
     * Tests if getting resource count works for resources and resources that are not in hand
     */
    @Test
    public void getResourceCardCountFor() {
        assertEquals(player.getResourceCardCountFor(Resource.BRICK), ORE_COUNT);
        assertEquals(player.getResourceCardCountFor(Resource.LUMBER), LUMBER_COUNT);
        assertEquals(player.getResourceCardCountFor(Resource.GRAIN), 0);
    }

    /**
     * Tests if removing a single resource from hand works
     */
    @Test
    public void removeResourceCardFromHand() {
        player.removeResourceCardFromHand(Resource.LUMBER);
        assertEquals(player.getResourceCardCountFor(Resource.LUMBER), 2);
    }

    /**
     * Tests if removing multiple resources from hand works
     */
    @Test
    public void testRemoveResourceCardsFromHand() {
        player.removeResourceCardFromHand(Resource.LUMBER, 2);
        assertEquals(player.getResourceCardCountFor(Resource.LUMBER), ORE_COUNT);
    }

    /**
     * Tests if removing a resource card that is not in hand works
     */
    @Test
    public void testRemoveResourceCardThatIsNoneFromHand() {
        player.removeResourceCardFromHand(Resource.GRAIN);
        assertEquals(player.getResourceCardCountFor(Resource.GRAIN), 0);

        player.removeResourceCardFromHand(Resource.GRAIN, 5);
        assertEquals(player.getResourceCardCountFor(Resource.GRAIN), 0);
    }

    /**
     * Tests if increasing the built structure counter works as expected
     */
    @Test
    public void increaseBuiltStructures() {
        player.increaseBuiltStructures(Structure.ROAD);
        player.increaseBuiltStructures(Structure.ROAD);
        player.increaseBuiltStructures(Structure.SETTLEMENT);
        player.increaseBuiltStructures(Structure.SETTLEMENT);

        assertEquals(player.getBuiltStructuresCounter().get(Structure.ROAD), 2);
        assertEquals(player.getBuiltStructuresCounter().get(Structure.SETTLEMENT), 2);
        assertEquals(player.getBuiltStructuresCounter().get(Structure.CITY), 0);
    }

    /**
     * Tests if the check for enough remaining structures for a player to build works correctly
     */
    @Test
    public void checkStructureStock() {
        player.increaseBuiltStructures(Structure.ROAD);
        player.increaseBuiltStructures(Structure.ROAD);
        player.increaseBuiltStructures(Structure.ROAD);

        final boolean stockStatusForRoads = player.hasEnoughInStructureStock(Structure.ROAD);
        assertTrue(stockStatusForRoads);
    }

    /**
     * Tests if the check for enough remaining structures for a player to build works if maxex out on a structure type
     */
    @Test
    public void checkStructureStockForNoMoreRemainingStructures() {
        for (int index = 0; index < Structure.CITY.getStockPerPlayer(); index++) {
            player.increaseBuiltStructures(Structure.CITY);
        }

        final boolean stockStatusForCities = player.hasEnoughInStructureStock(Structure.CITY);
        assertFalse(stockStatusForCities);
    }

    /**
     * Tests the check to check if enough resources are available to build a road
     */
    @Test
    public void hasEnoughLiquidityForRoad() {
        final boolean hasEnoughForRoad = player.checkLiquidity(Structure.ROAD);
        assertTrue(hasEnoughForRoad);
    }

    /**
     * Tests the check if the player hasnt gotten enough resources to build a city
     */
    @Test
    public void hasNotEnoughLiquidityForCity() {
        final boolean hasEnoughForCity = player.checkLiquidity(Structure.CITY);
        assertFalse(hasEnoughForCity);
    }

    /**
     * Tests if deleting half of a all resources works correctly.
     */
    @Test
    public void deletesHalfOfResources() {
        player.deletesHalfOfResources();
        assertEquals(player.getTotalResourceCardCount(), 2); //TODO @fupat002 => this method seems to do something odd, please recheck (also does it need to delete half of all resources?)
    }

}