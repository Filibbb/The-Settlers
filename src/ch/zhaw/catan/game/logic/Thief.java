package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptCoordinates;

/**
 * A thief class for the thief utility
 *
 * @author fupat002
 */
public class Thief {
    private final SettlersBoard settlersBoard;
    private Point thiefPosition;

    /**
     * Creates an instance of the thief
     *
     * @param settlersBoard the board instance
     */
    public Thief(SettlersBoard settlersBoard) {
        this.settlersBoard = settlersBoard;
    }

    /**
     * Asks the user for coordinates for the thief placement and handles the thief placements.
     *
     * @author fupat002
     */
    public void placeThiefOnField() {
        printMessage("You rolled a seven. Where do you want to place the thief?");
        Point thiefPosition = promptCoordinates("Center of Field");
        if (isValidThiefPlacement(thiefPosition)) {
            setThiefPosition(thiefPosition);
        } else {
            printMessage("Place the thief in the Center of a Field!");
            placeThiefOnField();
        }
        printConsequencesOfThiefPlacement();
    }

    /**
     * Prints the information of the field occupied by the thief.
     *
     * @author fupat002
     */
    public void printInfoOfFieldOccupiedByThief() {
        printMessage("The thief is on this field (" + thiefPosition + ").");
        printMessage("This Factions don't get any " + settlersBoard.getResourceOfField(thiefPosition) + ":");
        for (Point occupiedCorner : settlersBoard.getOccupiedCornerCoordinatesOfField(thiefPosition)) {
            printMessage(settlersBoard.getBuildingOnCorner(occupiedCorner).getOwner().getPlayerFaction().toString());
        }
    }

    /**
     * Returns true if the thief is on the field.
     *
     * @param field the coordinates of the field
     * @return the presence of the thief in the field
     * @author fupat002
     */
    public boolean isThiefOnField(Point field) {
        return thiefPosition != null && thiefPosition.equals(field);
    }

    public void setThiefPosition(Point thiefPosition) {
        this.thiefPosition = thiefPosition;
    }

    /**
     * Steal card from refactoring
     *
     * @param turnOrderHandler the current instance of the turnorderhandler
     */
    public void stealCardFromNeighbor(TurnOrderHandler turnOrderHandler) {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        if (settlersBoard.hasNeighborWithResource(thiefPosition, currentPlayer)) {
            stealRandomCard(turnOrderHandler.getCurrentPlayer(), settlersBoard.getRandomNeighbor(thiefPosition, currentPlayer));
        }
    }

    private void printConsequencesOfThiefPlacement() {
        if (!settlersBoard.getOccupiedCornerCoordinatesOfField(thiefPosition).isEmpty()) {
            printInfoOfFieldOccupiedByThief();
        } else {
            printMessage("So far nobody has been affected with this placement.");
        }
    }

    private boolean isValidThiefPlacement(Point thiefPosition) {
        return settlersBoard.getFields().contains(thiefPosition) && !settlersBoard.isWater(thiefPosition);
    }

    private void stealRandomCard(Player stealer, Player robbedPerson) {
        Resource resource = robbedPerson.getRandomResourceInHand();
        if (resource != null) {
            robbedPerson.removeResourceCardFromHand(resource);
            stealer.addResourceCardToHand(resource);
        } else {
            printMessage("Couldn't steal a resource card from your neighbors.");
        }
    }
}
