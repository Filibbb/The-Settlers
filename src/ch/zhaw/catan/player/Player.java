package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.Structure;

import java.util.*;

/**
 * This class manages the player data and faction.
 *
 * @author abuechi
 */
public class Player {
    private final Faction playerFaction;
    private final Map<Structure, Integer> builtStructuresCounter = new HashMap<>(Map.of(Structure.ROAD, 0, Structure.SETTLEMENT, 0, Structure.CITY, 0));
    private final Map<Resource, Integer> resourceCardsInHand = new HashMap<>();
    private int winningPoints = 0;

    /**
     * Creates a player object with the related faction.
     *
     * @param faction {@link Faction} the associated player faction
     */
    public Player(Faction faction) {
        this.playerFaction = faction;
    }

    /**
     * Increments the total winning points of a player
     */
    public void incrementWinningPoints() {
        winningPoints++;
    }

    /**
     * Check if the current player has enough structures remaining of that type to build specified structure
     *
     * @param structure the structure the player wanted to build
     * @return true if player has enough remaining structures, false otherwise.
     * @throws RuntimeException if unknown structure type is entered.
     * @author weberph5
     */
    public boolean checkStructureStock(Structure structure) {
        switch (structure) {
            case ROAD -> {
                return (builtStructuresCounter.get(Structure.ROAD) < Structure.ROAD.getStockPerPlayer());
            }
            case SETTLEMENT -> {
                return (builtStructuresCounter.get(Structure.SETTLEMENT) < Structure.SETTLEMENT.getStockPerPlayer());
            }
            case CITY -> {
                return (builtStructuresCounter.get(Structure.CITY) < Structure.CITY.getStockPerPlayer());
            }
            default -> {
                throw new RuntimeException("Unknown structure type " + structure);
            }
        }
    }

    /**
     * Check if the current player has enough resources to build specified structure
     *
     * @param structure the structure the player wanted to build
     * @return true if player has enough resources, false otherwise.
     * @author weberph5
     */
    public boolean checkLiquidity(Structure structure) {
        Map<Resource, Integer> resourceCards = getResourceCardsInHand();
        switch (structure) {
            case CITY -> {
                return ((resourceCards.get(Resource.ORE) >= 3) && (resourceCards.get(Resource.GRAIN) >= 2));
            }
            case ROAD -> {
                return ((resourceCards.get(Resource.LUMBER) >= 1) && (resourceCards.get(Resource.BRICK) >= 1));
            }
            case SETTLEMENT -> {
                return ((resourceCards.get(Resource.LUMBER) >= 1) && (resourceCards.get(Resource.GRAIN) >= 1) && (resourceCards.get(Resource.BRICK) >= 1) && (resourceCards.get(Resource.WOOL) >= 1));
            }
        }
        return false;
    }

    /**
     * Increases the build counter for the structure by one.
     *
     * @param structure the structure the player built.
     * @author weberph5
     */
    public void increaseBuiltStructures(Structure structure) {
        switch (structure) {
            case ROAD -> {
                Integer builtRoads = builtStructuresCounter.get(Structure.ROAD);
                builtStructuresCounter.put(Structure.ROAD, builtRoads++);
            }
            case SETTLEMENT -> {
                Integer builtSettlements = builtStructuresCounter.get(Structure.SETTLEMENT);
                builtStructuresCounter.put(Structure.SETTLEMENT, builtSettlements++);
            }
            case CITY -> {
                Integer builtCities = builtStructuresCounter.get(Structure.CITY);
                builtStructuresCounter.put(Structure.CITY, builtCities++);
            }
        }
    }

    /**
     * Counts the total resource cards a player holds.
     *
     * @return the total number of resource cards of all resources the player holds
     * @author abuechi
     */
    public int getTotalResourceCardCount() {
        int totalCount = 0;
        for (Map.Entry<Resource, Integer> card : resourceCardsInHand.entrySet()) {
            int cardCount = card.getValue() != null ? card.getValue() : 0;
            totalCount += cardCount;
        }
        return totalCount;
    }

    /**
     * Checks if the player has more than 7 resource cards.
     *
     * @return true if the player has more than 7 resource cards.
     * @author fupat002
     */
    public boolean playerHasMoreThanSevenResources() {
        return getTotalResourceCardCount() > 7;
    }

    /**
     * Deletes half of the resources.
     */
    public void deletesHalfOfResources() {
        int cardsToGiveUp = getTotalResourceCardCount() / 2;
        while (cardsToGiveUp != 0) {
            for (Resource card : resourceCardsInHand.keySet()) {
                if (cardsToGiveUp != 0 && resourceCardsInHand.get(card) > 0) {
                    int cardCountWithOneRemoved = resourceCardsInHand.get(card) - 1;
                    resourceCardsInHand.put(card, cardCountWithOneRemoved);
                    cardsToGiveUp--;
                }
            }
        }
    }

    /**
     * Adds exactly one of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @author abuechi
     */
    public void addResourceCardToHand(Resource resource) {
        addResource(resource, 1);
    }

    /**
     * Adds the specified count of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @param count    the count on how many resource cards are required to be added
     * @author abuechi
     */
    public void addResourceCardToHand(Resource resource, int count) {
        addResource(resource, count);
    }

    private void addResource(Resource resource, int count) {
        if (resourceCardsInHand.containsKey(resource)) {
            final Integer cardCount = resourceCardsInHand.get(resource);
            resourceCardsInHand.put(resource, cardCount + count);
        } else {
            resourceCardsInHand.put(resource, count);
        }
    }

    /**
     * Removes exactly one of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @return returns true if removal was successful or false if removal didnt take place.
     * @author abuechi
     */
    public boolean removeResourceCardFromHand(Resource resource) {
        return removeResource(resource, 1);
    }

    /**
     * Removes the specified count of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @param count    the count on how many resource cards are required to be added
     * @return returns true if removal was successful or false if removal didnt take place.
     * @author abuechi
     */
    public boolean removeResourceCardFromHand(Resource resource, int count) {
        return removeResource(resource, count);
    }

    private boolean removeResource(Resource resource, int count) {
        if (resourceCardsInHand.containsKey(resource)) {
            final Integer cardCount = resourceCardsInHand.get(resource);
            resourceCardsInHand.put(resource, cardCount - count);
            return true;
        }
        return false;
    }

    /**
     * Gets the resource card count for the specified resource
     *
     * @param resource the resource the player wants to know the count for.
     * @return the current count of resource cards of specified type.
     */
    public int getResourceCardCountFor(Resource resource) {
        final Integer cardCount = resourceCardsInHand.get(resource);
        return cardCount != null ? cardCount : 0;
    }

    public Map<Resource, Integer> getResourceCardsInHand() {
        return resourceCardsInHand;
    }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public int getWinningPoints() {
        return winningPoints;
    }

    public Map<Structure, Integer> getBuiltStructuresCounter() {
        return builtStructuresCounter;
    }
}
