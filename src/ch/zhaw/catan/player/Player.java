package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;

import java.util.*;

/**
 * This class manages the player data and faction.
 *
 * @author abuechi
 */
public class Player {
    private final Faction playerFaction;
    private int winningPoints = 0;
    private final Set<OccupiedResourceField> allOccupiedResourceFields = new HashSet<>();//TODO: wenn ein neues Dorf platziert wird ein Set-Eintrag adden.
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
    public void handOverHalfOfResources() {
        int cardsToGiveUp = (getTotalResourceCardCount() + 1) / 2;
        while (cardsToGiveUp != 0) {
            for (Resource card : resourceCards.keySet()) {
                if ((cardsToGiveUp != 0) && (resourceCards.get(card) > 0)) {
                    int cardCountWithOneRemoved = resourceCards.get(card) - 1;
                    resourceCards.put(card, cardCountWithOneRemoved);
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

    /**
     * Checks if the Player occupies a field with the number thrown.
     *
     * @param diceValue the number thrown.
     * @return true if the player occupies a field with the number thrown.
     * @author fupat002
     */
    public boolean playerOccupiesField(int diceValue) {
        for (OccupiedResourceField occupiedField : allOccupiedResourceFields) {
            if (occupiedField.getDiceValue() == diceValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the resource of a field with the dice value from a player that he has occupied.
     *
     * @param diceValue number between 2 and 12. Dice value of the field.
     * @return the resource of the field with the dice value.
     * @author fupat002
     */
    //TODO: Wenn zwei Ressourcenfelder den gleichen Würfelwert haben funktioniert es noch nicht so wie gewollt.
    public Resource getResourceByDiceValue(int diceValue) {
        for (OccupiedResourceField occupiedField : allOccupiedResourceFields) {
            if (occupiedField.getDiceValue() == diceValue) {
                return occupiedField.getResource();
            }
        }
        return null;
    }

    /**
     * Counts the winning points of a field with a specific dice value.
     *
     * @param diceValue number between 2 and 12. Dice value of the field.
     * @return the amount of winning points on a resource field.
     * @author fupat002
     */
    public int countWinningPointsOnRolledFields(int diceValue) {
        int resourceCounter = 0;
        for (OccupiedResourceField occupiedField : allOccupiedResourceFields) {
            if (occupiedField.getDiceValue() == diceValue) {
                if (occupiedField.isOccupiedWithCity()) {
                    resourceCounter += 2;
                } else {
                    resourceCounter++;
                }
            }
        }
        return resourceCounter;
    }

    /**
     * Adds a resource field to the set.
     *
     * @param resource  the resource of the field.
     * @param diceValue the dice value of the field.
     * @author fupat002
     */
    public void addResourceField(Resource resource, int diceValue) {
        allOccupiedResourceFields.add(new OccupiedResourceField(resource, diceValue));
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
