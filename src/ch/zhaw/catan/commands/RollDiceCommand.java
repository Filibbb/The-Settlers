package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.Thief;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.AbstractInfrastructure;
import ch.zhaw.catan.infrastructure.City;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The RollDiceCommand rolls the dice, handles the thief and hands out the resources of the rolled field.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class RollDiceCommand implements Command {
    private final Dice dice = new Dice();
    private final SettlersBoard settlersBoard;
    private final TurnOrderHandler turnOrderHandler;
    private final List<Player> players;
    private final Thief thief;
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * Creates the RollDiceCommand
     *
     * @param settlersBoard    The current settlers board
     * @param turnOrderHandler The current turn order handler with all players
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
                    AbstractInfrastructure buildingOnCorner = settlersBoard.getBuildingOnCorner(occupiedCorner);
                    Player owner = buildingOnCorner.getOwner();
                    if (buildingOnCorner instanceof Settlement) {
                        owner.addResourceCardToHand(resourceOfRolledField);
                    } else if (buildingOnCorner instanceof City) {
                        owner.addResourceCardToHand(resourceOfRolledField);
                        owner.addResourceCardToHand(resourceOfRolledField);
                    } else textTerminal.println("Something went wrong. Corner is not valid");
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
