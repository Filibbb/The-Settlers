package ch.zhaw.catan.board;

import ch.zhaw.catan.game.logic.Thief;
import ch.zhaw.catan.infrastructure.AbstractInfrastructure;
import ch.zhaw.catan.infrastructure.City;
import ch.zhaw.catan.infrastructure.Road;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;
import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static ch.zhaw.catan.board.BoardUtil.getDefaultDiceNumberPlacement;
import static ch.zhaw.catan.board.BoardUtil.getDefaultLandTilePlacement;
import static ch.zhaw.catan.board.Land.WATER;

/**
 * This is the Settlers game board that is built on a hex board.
 */
public class SettlersBoard extends HexBoard<Land, AbstractInfrastructure, Road, String> {
    private static final Point INITIAL_THIEF_POSITION = new Point(7, 11);
    private final Map<Point, Integer> diceNumberPlacements;
    private final Map<Point, Land> landTilePlacement;
    private final Thief thief;

    /**
     * Creates a default settlers board with default initialization of board and dice number placements
     */
    public SettlersBoard() {
        landTilePlacement = getDefaultLandTilePlacement();
        addFieldsForLandPlacements(landTilePlacement);
        diceNumberPlacements = getDefaultDiceNumberPlacement();
        this.thief = new Thief(this);
        thief.setThiefPosition(INITIAL_THIEF_POSITION);
    }


    public Map<Point, Integer> getDiceNumberPlacements() {
        return diceNumberPlacements;
    }

    /**
     * Returns the resource of a specific field.
     *
     * @param field center coordinate sof a field
     * @return the resource of the field
     * @author fupat002
     */
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

    public AbstractInfrastructure getBuildingOnCorner(Point cornerCoordinates) {
        return getCorner(cornerCoordinates);
    }

    /**
     * Checks if the field is water
     *
     * @param field center point of field
     * @return true if the field is water
     */
    public boolean isWater(Point field) {
        return landTilePlacement.get(field).equals(WATER);
    }

    /**
     * Returns true if there are other players with resources settled on the field.
     *
     * @param field         to check the neighbors from
     * @param currentPlayer the current player
     * @return true if the player has a neighbor with resources
     */
    public boolean hasNeighborWithResource(Point field, Player currentPlayer) {
        List<AbstractInfrastructure> neighbors = getNeighborsWithResources(field, currentPlayer);
        for (AbstractInfrastructure neighbor : neighbors) {
            if (neighbor.getOwner().getTotalResourceCardCount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a random neighbor of a player on a specific field.
     *
     * @param field         a field
     * @param currentPlayer the current player
     * @return a random neighbor on the field
     */
    public Player getRandomNeighbor(Point field, Player currentPlayer) {
        Random random = new Random();
        List<AbstractInfrastructure> neighbors = getNeighborsWithResources(field, currentPlayer);
        if (neighbors.size() > 1) {
            return neighbors.get(random.nextInt(neighbors.size())).getOwner();
        } else {
            return neighbors.get(0).getOwner();
        }
    }

    private List<AbstractInfrastructure> getNeighborsWithResources(Point field, Player currentPlayer) {
        List<Point> occupiedCorners = getOccupiedCornerCoordinatesOfField(field);
        List<AbstractInfrastructure> neighbors = new ArrayList<>();
        for (Point occupiedCorner : occupiedCorners) {
            Player neighbor = getBuildingOnCorner(occupiedCorner).getOwner();
            if (!currentPlayer.equals(neighbor) && neighbor.getTotalResourceCardCount() > 0) {
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

    /**
     * Sets a road on the edge
     *
     * @param road a road object destined to be set upon the board
     */
    public void buildRoad(Road road) {
        setEdge(road.getPosition(), road.getEndPoint(), road);
    }

    /**
     * Sets a settlement on the corner
     *
     * @param settlement Settlement instance with its owner
     */
    public void buildSettlement(Settlement settlement) {
        buildLivingInfrastructure(settlement);
    }

    /**
     * Sets a city on the corner
     *
     * @param city City instance with its owner
     */
    public void buildCity(City city) {
        buildLivingInfrastructure(city);
    }

    private <T extends AbstractInfrastructure> void buildLivingInfrastructure(T infrastructure) {
        setCorner(infrastructure.getPosition(), infrastructure);
    }

    public Thief getThief() {
        return thief;
    }

    /**
     * Checks if the corner is not on the edge of the board surrounded by water only
     *
     * @param coordinates the coordinates where structure is being built
     * @return true if at least one surrounding field is not water
     */
    public boolean isCornerNotSurroundedByWater(Point coordinates) {
        List<Land> fieldsAroundCorner = getFieldsOfCorner(coordinates);
        for (Land land : fieldsAroundCorner) {
            if (land.getResource() != null) {
                return true;
            }
        }
        return false;
    }
}
