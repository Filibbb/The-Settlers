package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;

public class Thief {
    private final SettlersBoard settlersBoard;
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private Point thiefPosition;

    public Thief(SettlersBoard settlersBoard){
        this.settlersBoard = settlersBoard;
    }

    /**
     * Asks the user for coordinates for the thief placement and handles the thief placements.
     *
     * @author fupat002
     */
    public void placeThiefOnField() {
        int xCoordinate = textIO.newIntInputReader().read("You rolled a seven. Where do you want to place the thief? Enter the X coordinate of the center");
        int yCoordinate = textIO.newIntInputReader().read(" Enter the Y coordinate of the center");
        Point thiefPosition = new Point(xCoordinate, yCoordinate);
        if (isValidThiefPlacement(thiefPosition)) {
            setThiefPosition(thiefPosition);
        } else {
            textTerminal.println("Place the thief in the Center of a Field!");
            placeThiefOnField();
        }
        printConsequencesOfThiefPlacement();
    }

    /**
     * Prints the information of the field occupied by the thief.
     *
     * @author fupat002
     */
    public void printInfoOfFieldOccupiedByThief(){
        textTerminal.println("The thief is on this field ("+ thiefPosition +").");
        textTerminal.println("This Factions don't get any " + settlersBoard.getResourceOfField(thiefPosition) + ":");
        for(Point occupiedCorner : settlersBoard.getOccupiedCornerCoordinatesOfField(thiefPosition)){
            textTerminal.println(settlersBoard.getBuildingOnCorner(occupiedCorner).getOwner().getPlayerFaction().toString());
        }
    }

    /**
     * Returns true if the thief is on the field.
     *
     * @param field     the coordinates of the field
     * @return          the presence of the thief in the field
     * @author          fupat002
     */
    public boolean isThiefOnField(Point field) {
        return thiefPosition != null && thiefPosition.equals(field);
    }

    public void setThiefPosition(Point thiefPosition) {
        this.thiefPosition = thiefPosition;
    }

    public void stealCardFromNeighbor(TurnOrderHandler turnOrderHandler) {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        if (settlersBoard.hasNeighborWithRessource(thiefPosition, currentPlayer)) {
            stealRandomCard(turnOrderHandler.getCurrentPlayer(), settlersBoard.getNeighbor(thiefPosition, currentPlayer));
        }
    }

    private void printConsequencesOfThiefPlacement(){
        if(!settlersBoard.getOccupiedCornerCoordinatesOfField(thiefPosition).isEmpty()){
            printInfoOfFieldOccupiedByThief();
        }else{
            textTerminal.println("So far nobody has been affected with this placement.");
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
