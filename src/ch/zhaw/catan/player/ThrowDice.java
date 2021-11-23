package ch.zhaw.catan.player;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class ThrowDice {
    Random random = new Random();

    /**
     * The player with the highest number has to start.
     */
    public void highRoll(){

    }

    public void throwDice(Set<Player> players){
        int diceValue = dice();
        if(diceValue == 7){
            //TODO: if dice == 7 -> ...
        }else{
            for (Player player : players) {
                handOutResourcesAfterDiceThrow(player, diceValue);
            }
        }
    }

    private void handOutResourcesAfterDiceThrow(Player player, int diceValue){
        if(player.playerOccupiesField(diceValue)){
            player.addResourceCardToHand(player.getResourceByDiceValue(diceValue), player.countWinningPointsOnRolledFields(diceValue));
        }
    }

    private int dice(){
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);;
        return firstDice + secondDice;
    }
}
