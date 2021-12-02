package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RollDiceCommand {

    public void execute(Dice dice, ArrayList<Player> players, SettlersBoard settlersBoard) {
        int diceValue = dice.dice();
        if (diceValue == 7) {
            sevenRolled(players);
        } else {
            List<Point> allFieldsWithDiceValue = settlersBoard.getFieldsByDiceValue(diceValue);
            for (Point field : allFieldsWithDiceValue) {
                Resource resourceOfRolledField = settlersBoard.getResourcesOfField(field);
                //TODO: get players and there "winning points" of this field
                // and then handout resources to the Players of this field
            }
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
