package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RollDiceCommand {
    private final Dice dice = new Dice();
    private final SettlersBoard settlersBoard;
    private final List<Player> players;

    public RollDiceCommand(SettlersBoard settlersBoard, TurnOrderHandler turnOrderHandler) {
        this.settlersBoard = settlersBoard;
        this.players = turnOrderHandler.getPlayerTurnOrder();
    }

    /**
     * Executes the Roll Dice Command.
     *
     * @author fupat002
     */
    public void execute() {
        int diceValue = dice.dice();
        if (diceValue == 7) {
            sevenRolled();
        } else {
            handoutResourcesOfTheRolledField(diceValue);
        }
    }

    void handoutResourcesOfTheRolledField(int diceValue) {
        List<Point> allFieldsWithDiceValue = settlersBoard.getFieldsByDiceValue(diceValue);
        for (Point field : allFieldsWithDiceValue) {
            if (!settlersBoard.isThiefOnField(field)) {
                Resource resourceOfRolledField = settlersBoard.getResourceOfField(field);
                ArrayList<Point> occupiedCornersOfField = settlersBoard.getCornerCoordinatesOfOccupiedField(field);
                for (Point occupiedCorner : occupiedCornersOfField) {
                    Settlement buildingOnCorner = settlersBoard.getBuildingOnCorner(occupiedCorner);
                    Player owner = buildingOnCorner.getOwner();
                    owner.addResourceCardToHand(resourceOfRolledField);
                }
            }
        }
    }

    private void sevenRolled() {
        for (Player player : players) {
            if (player.playerHasMoreThanSevenResources()) {
                player.deletesHalfOfResources();
            }
        }
        settlersBoard.placeThiefOnField();
        //steal from player
    }
}
