package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.infrastructure.Structure;

import java.util.*;

import static ch.zhaw.catan.infrastructure.Structure.*;
import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static java.lang.Math.toIntExact;

/**
 * This class manages the player data and faction.
 *
 * @author abuechi
 */
public class Player {
    private final Faction playerFaction;
    private final Map<Structure, Integer> builtStructuresCounter = new HashMap<>(Map.of(ROAD, 0, SETTLEMENT, 0, CITY, 0));
    private final Map<Resource, Integer> resourceCardsInHand = new HashMap<>();
    private int winningPointCounter = 0;

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
        winningPointCounter++;
    }

    /**
     * Check if the current player has enough structures remaining of that type to build specified structure
     *
     * @param structure the structure the player wants to build
     * @return true if player has enough remaining structures, false otherwise.
     * @throws RuntimeException if unknown structure type is entered.
     * @author weberph5
     */
    public boolean hasEnoughInStructureStock(Structure structure) {
        switch (structure) {
            case ROAD -> {
                return builtStructuresCounter.get(ROAD) < ROAD.getStockPerPlayer();
            }
            case SETTLEMENT -> {
                return builtStructuresCounter.get(SETTLEMENT) < SETTLEMENT.getStockPerPlayer();
            }
            case CITY -> {
                return builtStructuresCounter.get(CITY) < CITY.getStockPerPlayer();
            }
            default -> throw new RuntimeException("Unknown structure type " + structure);
        }
    }

    /**
     * Check if the current player has enough resources to build specified structure
     *
     * @param structure the structure the player wanted to build
     * @return true if player has enough resources, false otherwise.
     * @author weberph5
     */
    public boolean hasEnoughLiquidityFor(Structure structure) {
        final Map<Resource, Integer> resourceCards = getResourceCardsInHand();
        return structure.getCostsAsMap().entrySet().stream().
                allMatch(structureResourceCost -> resourceCards.get(structureResourceCost.getKey()) != null &&
                        resourceCards.get(structureResourceCost.getKey()) >= toIntExact(structureResourceCost.getValue()));
    }


    /**
     * Increases the build counter for the structure by 1.
     *
     * @param structureType the structure the player built.
     * @author weberph5
     */
    public void incrementStructureCounterFor(Structure structureType) {
        Integer currentCounter = builtStructuresCounter.get(structureType);
        currentCounter = currentCounter != null ? currentCounter : 0;
        builtStructuresCounter.put(structureType, currentCounter + 1);
    }

    /**
     * Decreases the build counter for the structure by 1.
     * This is used to put a settlement back into the stock when it is upgraded to a city.
     *
     * @param structureType the structure the player removes.
     * @author weberph5
     */
    public void decreaseStructureCounterFor(Structure structureType) {
        Integer currentCounter = builtStructuresCounter.get(structureType);
        currentCounter = currentCounter != null ? currentCounter : 0;
        builtStructuresCounter.put(structureType, currentCounter - 1);
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
        int cardsToGiveUp = Math.floorDiv(getTotalResourceCardCount(), 2);
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
        if (count >= 0) {
            if (resourceCardsInHand.containsKey(resource)) {
                final Integer cardCount = resourceCardsInHand.get(resource);
                resourceCardsInHand.put(resource, cardCount + count);
            } else {
                resourceCardsInHand.put(resource, count);
            }
        } else {
            throw new RuntimeException("Unexpected result: Adding Resource cards with negative counter. Please use `removeResourceCardFromHand` instead.");
        }
    }

    /**
     * Removes exactly one of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @author abuechi
     */
    public void removeResourceCardFromHand(Resource resource) {
        removeResource(resource, 1);
    }

    /**
     * Removes the specified count of the specified resource to the hand of the player
     *
     * @param resource the resource type you want to add to the hand.
     * @param count    the count on how many resource cards are required to be added
     * @author abuechi
     */
    public void removeResourceCardFromHand(Resource resource, int count) {
        removeResource(resource, count);
    }

    private void removeResource(Resource resource, int count) {
        if (resourceCardsInHand.containsKey(resource)) {
            final Integer cardCount = resourceCardsInHand.get(resource);
            resourceCardsInHand.put(resource, Math.max(cardCount - count, 0));
        }
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

    public int getWinningPointCounter() {
        return winningPointCounter;
    }

    public Map<Structure, Integer> getBuiltStructuresCounter() {
        return builtStructuresCounter;
    }

    /**
     * Selects a random resource from the current players hand
     *
     * @return a random resource of the player
     * @author fupat002
     */
    public Resource getRandomResourceInHand() {
        final Random random = new Random();
        final List<Resource> resourcesInHand = new ArrayList<>(resourceCardsInHand.keySet());
        if (!resourcesInHand.isEmpty()) {
            return resourcesInHand.get(random.nextInt(resourcesInHand.size()));
        } else {
            return null;
        }
    }

    /**
     * Pays the resource cost of the structure built by the player
     *
     * @param structureType the type that requires payment
     * @author abuechi
     */
    public void payForStructure(Structure structureType) {
        if (hasEnoughLiquidityFor(structureType)) {
            structureType.getCostsAsMap().forEach((resource, count) -> removeResourceCardFromHand(resource, toIntExact(count)));
        } else {
            printMessage("Not enough resources to build specified structure " + structureType);
        }
    }
}
