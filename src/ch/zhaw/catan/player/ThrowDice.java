package ch.zhaw.catan.player;

import ch.zhaw.catan.board.GameBoard;
import ch.zhaw.catan.board.Resource;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ThrowDice {
    Random random = new Random();

    /**
     * The player with the highest number has to start.
     */
    public void highRoll(){

    }

    public void throwDice(Player player){
        int diceValue = dice();
        //TODO: if dice == 7 -> ...
        handOutResourcesAfterDiceThrow(player, diceValue);
    }

    private void handOutResourcesAfterDiceThrow(Player player, int diceValue){
        //TODO: check if settlement or city for one ore two resources.
        if(player.playerOccupiesField(diceValue)){
            player.addResourceCardToHand(player.getResourceByDiceValue(diceValue));
        }
    }

    private int dice(){
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);;
        return firstDice + secondDice;
    }
}
