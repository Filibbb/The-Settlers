package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptCoordinates;

public class Thief {
    private final SettlersBoard settlersBoard;
    private Point thiefPosition;

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

    public void stealCardFromNeighbor(TurnOrderHandler turnOrderHandler) {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        if (settlersBoard.hasNeighborWithResource(thiefPosition, currentPlayer)) {
            stealRandomCard(turnOrderHandler.getCurrentPlayer(), settlersBoard.getNeighbor(thiefPosition, currentPlayer));
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
        robbedPerson.removeResourceCardFromHand(resource);
        stealer.addResourceCardToHand(resource);
    }
}
