package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;

import java.util.ArrayList;

public class RollDiceCommand {

    public void execute(Dice dice, ArrayList<Player> players) {
        int diceValue = dice.dice();
        if (diceValue == 7) {
            sevenRolled(players);
        } else {
            //getField by dice value -> check the settlements on this field then hand out de resource with the dice value to the owner of the settlement.
        }
    }

    private void sevenRolled(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.playerHasMoreThanSevenResources()) {
                player.deletesHalfOfResources();
            }
        }
    }
}
