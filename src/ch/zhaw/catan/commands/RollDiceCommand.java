package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.Thief;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The RollDiceCommand rolls the dice, handles the thief and hands out the resources of the rolled field.
 *
 * @version 1.0.0
 * @author fupat002
 */
public class RollDiceCommand implements Command{
    private final Dice dice = new Dice();
    private final SettlersBoard settlersBoard;
    private final TurnOrderHandler turnOrderHandler;
    private final List<Player> players;
    private final Thief thief;

    /**
     * Creates the RollDiceCommand
     *
     * @param settlersBoard     The current settlers board
     * @param turnOrderHandler  The current turn order handler with all players
     */
    public RollDiceCommand(SettlersBoard settlersBoard, TurnOrderHandler turnOrderHandler, Thief thief) {
        this.settlersBoard = settlersBoard;
        this.turnOrderHandler = turnOrderHandler;
        this.players = turnOrderHandler.getPlayerTurnOrder();
        this.thief = thief;
    }

    /**
     * Executes the Roll Dice Command.
     */
    @Override
    public void execute() {
        int diceValue = dice.dice();
        if (diceValue == 7) {
            sevenRolled();
        } else {
            handoutResourcesOfTheRolledField(diceValue);
        }
    }

    public void handoutResourcesOfTheRolledField(int diceValue) {
        List<Point> allFieldsWithDiceValue = settlersBoard.getFieldsByDiceValue(diceValue);
        for (Point field : allFieldsWithDiceValue) {
            if (!thief.isThiefOnField(field)) {
                Resource resourceOfRolledField = settlersBoard.getResourceOfField(field);
                ArrayList<Point> occupiedCornersOfField = settlersBoard.getOccupiedCornerCoordinatesOfField(field);
                for (Point occupiedCorner : occupiedCornersOfField) {
                    Settlement buildingOnCorner = settlersBoard.getBuildingOnCorner(occupiedCorner);
                    Player owner = buildingOnCorner.getOwner();
                    owner.addResourceCardToHand(resourceOfRolledField);
                }
            } else {
                thief.printInfoOfFieldOccupiedByThief();
            }
        }
    }

    private void sevenRolled() {
        for (Player player : players) {
            if (player.playerHasMoreThanSevenResources()) {
                player.deletesHalfOfResources();
            }
        }
        thief.placeThiefOnField();
        thief.stealCardFromNeighbor(turnOrderHandler);
    }
}
