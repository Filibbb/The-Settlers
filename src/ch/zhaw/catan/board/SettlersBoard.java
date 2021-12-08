package ch.zhaw.catan.board;

import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Road;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;
import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SettlersBoard extends HexBoard<Land, Settlement, Road, String> {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private Point thiefPosition;
    private final Map<Point, Integer> diceNumberPlacements;
    private final Map<Point, Land> landTilePlacement;

    /**
     * Creates a default settlers board with default initialization of board and dicenumber placements
     */
    public SettlersBoard() {
        landTilePlacement = getDefaultLandTilePlacement();
        addFieldsForLandPlacements(landTilePlacement);
        diceNumberPlacements = getDefaultDiceNumberPlacement();
    }

    /**
     * Returns the defaults mapping of the dice values per field.
     *
     * @return the dice values per field
     * @author tebe
     */
    public static Map<Point, Integer> getDefaultDiceNumberPlacement() {
        Map<Point, Integer> diceNumberAssignments = new HashMap<>();
        diceNumberAssignments.put(new Point(4, 8), 2);
        diceNumberAssignments.put(new Point(7, 5), 3);
        diceNumberAssignments.put(new Point(8, 14), 3);
        diceNumberAssignments.put(new Point(6, 8), 4);
        diceNumberAssignments.put(new Point(7, 17), 4);

        diceNumberAssignments.put(new Point(3, 11), 5);
        diceNumberAssignments.put(new Point(8, 8), 5);
        diceNumberAssignments.put(new Point(5, 5), 6);
        diceNumberAssignments.put(new Point(9, 11), 6);

        diceNumberAssignments.put(new Point(7, 11), 7);
        diceNumberAssignments.put(new Point(9, 5), 8);
        diceNumberAssignments.put(new Point(5, 17), 8);
        diceNumberAssignments.put(new Point(5, 11), 9);
        diceNumberAssignments.put(new Point(11, 11), 9);
        diceNumberAssignments.put(new Point(4, 14), 10);
        diceNumberAssignments.put(new Point(10, 8), 10);
        diceNumberAssignments.put(new Point(6, 14), 11);
        diceNumberAssignments.put(new Point(9, 17), 11);
        diceNumberAssignments.put(new Point(10, 14), 12);
        return Collections.unmodifiableMap(diceNumberAssignments);
    }

    /**
     * Returns the field (coordinate) to {@link Land} mapping for the <a href=
     * "https://www.catan.de/files/downloads/4002051693602_catan_-_das_spiel_0.pdf">standard
     * setup</a> of the game Catan..
     *
     * @return the field to {@link Land} mapping for the standard setup
     * @author tebe
     */
    public static Map<Point, Land> getDefaultLandTilePlacement() {
        Map<Point, Land> landPlacements = new HashMap<>();
        Point[] water = {new Point(4, 2), new Point(6, 2), new Point(8, 2), new Point(10, 2),
                new Point(3, 5), new Point(11, 5), new Point(2, 8), new Point(12, 8), new Point(1, 11),
                new Point(13, 11), new Point(2, 14), new Point(12, 14), new Point(3, 17), new Point(11, 17),
                new Point(4, 20), new Point(6, 20), new Point(8, 20), new Point(10, 20)};

        for (Point p : water) {
            landPlacements.put(p, Land.WATER);
        }

        landPlacements.put(new Point(5, 5), Land.FOREST);
        landPlacements.put(new Point(7, 5), Land.PASTURE);
        landPlacements.put(new Point(9, 5), Land.PASTURE);

        landPlacements.put(new Point(4, 8), Land.FIELDS);
        landPlacements.put(new Point(6, 8), Land.MOUNTAIN);
        landPlacements.put(new Point(8, 8), Land.FIELDS);
        landPlacements.put(new Point(10, 8), Land.FOREST);

        landPlacements.put(new Point(3, 11), Land.FOREST);
        landPlacements.put(new Point(5, 11), Land.HILLS);
        landPlacements.put(new Point(7, 11), Land.DESERT);
        landPlacements.put(new Point(9, 11), Land.MOUNTAIN);
        landPlacements.put(new Point(11, 11), Land.FIELDS);

        landPlacements.put(new Point(4, 14), Land.FIELDS);
        landPlacements.put(new Point(6, 14), Land.MOUNTAIN);
        landPlacements.put(new Point(8, 14), Land.FOREST);
        landPlacements.put(new Point(10, 14), Land.PASTURE);

        landPlacements.put(new Point(5, 17), Land.PASTURE);
        landPlacements.put(new Point(7, 17), Land.HILLS);
        landPlacements.put(new Point(9, 17), Land.HILLS);

        return Collections.unmodifiableMap(landPlacements);
    }

    public Map<Point, Integer> getDiceNumberPlacements() {
        return diceNumberPlacements;
    }

    public Resource getResourceOfField(Point field) {
        for (Map.Entry<Point, Land> fields : landTilePlacement.entrySet()) {
            if (fields.getKey().equals(field)) {
                return fields.getValue().getResource();
            }
        }
        return null;
    }

    /**
     * Returns the fields associated with the specified dice value.
     *
     * @param diceValue the dice value
     * @return the fields associated with the dice value
     * @author fupat002
     */
    public List<Point> getFieldsByDiceValue(int diceValue) {
        List<Point> pointsOfFieldWithDiceValue = new ArrayList<>();
        for (Map.Entry<Point, Integer> field : diceNumberPlacements.entrySet()) {
            if (field.getValue() == diceValue) {
                pointsOfFieldWithDiceValue.add(field.getKey());
            }
        }
        return pointsOfFieldWithDiceValue;
    }

    /**
     * Returns the {@link Land}s adjacent to the specified corner.
     *
     * @param corner the corner
     * @return the list with the adjacent {@link Land}s
     */
    public List<Land> getLandsForCorner(Point corner) {
        return getFields(corner);
    }

    public Settlement getBuildingOnCorner(Point cornerCoordinates) {
        return getCorner(cornerCoordinates);
    }

    public void placeThiefOnField(TurnOrderHandler turnOrderHandler) {
        int xCoordinate = textIO.newIntInputReader().read("You rolled a seven. Where do you want to place the thief? Enter the X coordinate of the center");
        int yCoordinate = textIO.newIntInputReader().read(" Enter the Y coordinate of the center");
        Point thiefPosition = new Point(xCoordinate, yCoordinate);
        if (isValidThiefPlacement(thiefPosition)) {
            setThiefPosition(thiefPosition);
            stealCardFromNeighborAfterThiefPlacement(turnOrderHandler);
        } else {
            textTerminal.println("Place the thief in the Center of a Field!");
            placeThiefOnField(turnOrderHandler);
        }
        printConsequencesOfThiefPlacement();
    }

    private void printConsequencesOfThiefPlacement(){
        if(!getOccupiedCornerCoordinatesOfField(thiefPosition).isEmpty()){
            printInfoOfFieldOccupiedByThief();
        }else{
            textTerminal.println("So far nobody has been affected with this placement.");
        }
    }

    public void printInfoOfFieldOccupiedByThief(){
        textTerminal.println("The thief is on this field ("+ thiefPosition +").");
        textTerminal.println("This Factions don't get any " + getResourceOfField(thiefPosition) + ":");
        for(Point occupiedCorner : getOccupiedCornerCoordinatesOfField(thiefPosition)){
            textTerminal.println(getBuildingOnCorner(occupiedCorner).getOwner().getPlayerFaction().toString());
        }
    }

    public boolean isThiefOnField(Point field) {
        if (thiefPosition != null) {
            return thiefPosition.equals(field);
        } else {
            return false;
        }
    }

    public void setThiefPosition(Point thiefPosition) {
        this.thiefPosition = thiefPosition;
    }

    void stealCardFromNeighborAfterThiefPlacement(TurnOrderHandler turnOrderHandler) {
        Player currentPlayer = turnOrderHandler.getCurrentPlayer();
        if (hasNeighborWithRessource(thiefPosition, currentPlayer)) {
            stealRandomCard(turnOrderHandler.getCurrentPlayer(), getNeighbor(thiefPosition, currentPlayer));
        }
    }

    private boolean isValidThiefPlacement(Point thiefPosition) {
        return getFields().contains(thiefPosition) && !isWater(thiefPosition);
    }

    private boolean isWater(Point field) {
        return landTilePlacement.get(field).equals(Land.WATER);
    }

    private void stealRandomCard(Player stealer, Player robbedPerson) {
        Resource resource = robbedPerson.getRandomResourceInHand();
        robbedPerson.removeResourceCardFromHand(resource);
        stealer.addResourceCardToHand(resource);
    }

    private boolean hasNeighborWithRessource(Point field, Player currentPlayer) {
        List<Settlement> neighbors = getNeighbors(field, currentPlayer);
        for (Settlement neighbor : neighbors) {
            if (neighbor.getOwner().getTotalResourceCardCount() > 0) {
                return true;
            }
        }
        return false;
    }

    private Player getNeighbor(Point field, Player currentPlayer) {
        List<Settlement> neighbors = getNeighbors(field, currentPlayer);
        return neighbors.get(0).getOwner();
    }

    private List<Settlement> getNeighbors(Point field, Player currentPlayer) {
        List<Point> occupiedCorners = getOccupiedCornerCoordinatesOfField(field);
        List<Settlement> neighbors = new ArrayList<>();
        for (Point occupiedCorner : occupiedCorners) {
            if (!currentPlayer.equals(getBuildingOnCorner(occupiedCorner).getOwner())) {
                neighbors.add(getBuildingOnCorner(occupiedCorner));
            }
        }
        return neighbors;
    }

    private void addFieldsForLandPlacements(Map<Point, Land> landPlacement) {
        for (Map.Entry<Point, Land> landEntry : landPlacement.entrySet()) {
            addField(landEntry.getKey(), landEntry.getValue());
        }
    }
}
