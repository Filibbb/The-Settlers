package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the player data and faction.
 *
 * @author abuechi
 */
public class Player {
    private final Faction playerFaction;
    private int winningPoints = 0;
    private final Map<Resource, Integer> resourceCards = new HashMap<>();

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
     * Counts the total resource cards a player holds.
     *
     * @return the total number of resource cards of all resources the player holds
     * @author abuechi
     */
    public int getTotalResourceCardCount() {
        int totalCount = 0;
        for (Map.Entry<Resource, Integer> card : resourceCards.entrySet()) {
            int cardCount = card.getValue() != null ? card.getValue() : 0;
            totalCount += cardCount;
        }
        return totalCount;
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
        if (resourceCards.containsKey(resource)) {
            final Integer cardCount = resourceCards.get(resource);
            resourceCards.put(resource, cardCount + count);
        } else {
            resourceCards.put(resource, count);
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
        if (resourceCards.containsKey(resource)) {
            final Integer cardCount = resourceCards.get(resource);
            resourceCards.put(resource, cardCount - count);
            return true;
        }
        return false;
    }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public int getWinningPoints() {
        return winningPoints;
    }

    public Map<Resource, Integer> getResourceCards() {
        return resourceCards;
    }
}
