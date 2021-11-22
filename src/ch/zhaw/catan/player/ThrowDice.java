package ch.zhaw.catan.player;

import ch.zhaw.catan.board.GameBoard;
import ch.zhaw.catan.board.Resource;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ThrowDice {
    Random random = new Random();

    /**
     * This method takes care of actions depending on the dice throw result.
     * <p>
     * A key action is the payout of the resource cards to the players
     * according to the payout rules of the game. This includes the
     * "negative payout" in case a 7 is thrown and a player has more than
     * {@link GameBoard#MAX_CARDS_IN_HAND_NO_DROP} resource cards.
     * <p>
     * If a player does not get resource cards, the list for this players'
     * {@link Faction} is <b>an empty list (not null)</b>!.
     *
     * <p>
     * The payout rules of the game take into account factors such as, the number
     * of resource cards currently available in the bank, settlement types
     * (settlement or city), and the number of players that should get resource
     * cards of a certain type (relevant if there are not enough left in the bank).
     * </p>
     *
     * @param dicethrow the resource cards that have been distributed to the players
     * @return the resource cards added to the stock of the different players
     */
    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        // TODO: Implement
        return null;
    }

    /**
     * The player with the highest number can start.
     */
    public void highRoll(){

    }

    public void throwDice(){

    }

    private void handOutResources(Map<Faction, List<Resource>> playerStatus){

    }

    private List<Resource> getResourcesForDiceValue(int diceValue){
        return null;
    }

    private int dice(){
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);;
        return firstDice + secondDice;
    }
}
