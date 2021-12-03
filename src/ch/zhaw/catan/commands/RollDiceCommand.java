package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RollDiceCommand {
    private final Dice dice = new Dice();

    /**
     * Executes the Roll Dice Command.
     *
     * @param players       All players
     * @param settlersBoard The Settlers Board
     * @author fupat002
     */
    public void execute(ArrayList<Player> players, SettlersBoard settlersBoard) {
        int diceValue = dice.dice();
        if (diceValue == 7) {
            sevenRolled(players);
        } else {
            handoutResourcesOfTheRolledField(settlersBoard, diceValue);
        }
    }

    private void handoutResourcesOfTheRolledField(SettlersBoard settlersBoard, int diceValue) {
        List<Point> allFieldsWithDiceValue = settlersBoard.getFieldsByDiceValue(diceValue);
        for (Point field : allFieldsWithDiceValue) {
            Resource resourceOfRolledField = settlersBoard.getResourceOfField(field);
            ArrayList<Point> occupiedCornersOfField = settlersBoard.getCornerCoordinatesOfOccupiedField(field);
            for (Point occupiedCorner : occupiedCornersOfField) {
                Object buildingOnCorner = settlersBoard.getBuildingOnCorner(occupiedCorner);
                Player owner = ((Settlement) buildingOnCorner).getOwner();
                owner.addResourceCardToHand(resourceOfRolledField);
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
