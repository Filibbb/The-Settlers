package ch.zhaw.catan.board;

import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class specifies the most important and basic parameters of the game
 * Catan.
 * <p>
 * The class provides definitions such as for the type and number of resource
 * cards or the number of available road elements per player. Furthermore, it
 * provides a dice number to field and a field to land type mapping for the
 * standard setup detailed <a href=
 * "https://www.catan.de/files/downloads/4002051693602_catan_-_das_spiel_0.pdf">here</a>
 * </p>
 *
 * @author tebe
 */
public class GameBoard {
  public static final Point INITIAL_THIEF_POSITION = new Point(7, 11);
  public static final Map<Resource, Integer> INITIAL_RESOURCE_CARDS_BANK = Map.of(Resource.LUMBER, 19,
            Resource.BRICK, 19, Resource.WOOL, 19, Resource.GRAIN, 19, Resource.ORE, 19);

  public GameBoard(){
        boardInit();
      }


public static HexBoard boardInit(){
    HexBoard board = new HexBoard();
    Map<Point, Land> landPlacement= getDefaultLandPlacement();
    for (Point key : landPlacement.keySet()) {
        board.addField(key, landPlacement.get(key));
    }
    return board;
}
    /**
     * Returns the defaults mapping of the dice values per field.
     *
     * @return the dice values per field
     */
    public static Map<Point, Integer> getDefaultDiceNumberPlacement() {
        Map<Point, Integer> assignment = new HashMap<>();
        assignment.put(new Point(4, 8), 2);
        assignment.put(new Point(7, 5), 3);
        assignment.put(new Point(8, 14), 3);
        assignment.put(new Point(6, 8), 4);
        assignment.put(new Point(7, 17), 4);

        assignment.put(new Point(3, 11), 5);
        assignment.put(new Point(8, 8), 5);
        assignment.put(new Point(5, 5), 6);
        assignment.put(new Point(9, 11), 6);

        assignment.put(new Point(7, 11), 7);
        assignment.put(new Point(9, 5), 8);
        assignment.put(new Point(5, 17), 8);
        assignment.put(new Point(5, 11), 9);
        assignment.put(new Point(11, 11), 9);
        assignment.put(new Point(4, 14), 10);
        assignment.put(new Point(10, 8), 10);
        assignment.put(new Point(6, 14), 11);
        assignment.put(new Point(9, 17), 11);
        assignment.put(new Point(10, 14), 12);
        return Collections.unmodifiableMap(assignment);
    }

    /**
     * Returns the field (coordinate) to {@link Land} mapping for the <a href=
     * "https://www.catan.de/files/downloads/4002051693602_catan_-_das_spiel_0.pdf">standard
     * setup</a> of the game Catan..
     *
     * @return the field to {@link Land} mapping for the standard setup
     */
    public static Map<Point, Land> getDefaultLandPlacement() {
        Map<Point, Land> assignment = new HashMap<>();
        Point[] water = {new Point(4, 2), new Point(6, 2), new Point(8, 2), new Point(10, 2),
                new Point(3, 5), new Point(11, 5), new Point(2, 8), new Point(12, 8), new Point(1, 11),
                new Point(13, 11), new Point(2, 14), new Point(12, 14), new Point(3, 17), new Point(11, 17),
                new Point(4, 20), new Point(6, 20), new Point(8, 20), new Point(10, 20)};

        for (Point p : water) {
            assignment.put(p, Land.WATER);
        }

        assignment.put(new Point(5, 5), Land.FOREST);
        assignment.put(new Point(7, 5), Land.PASTURE);
        assignment.put(new Point(9, 5), Land.PASTURE);

        assignment.put(new Point(4, 8), Land.FIELDS);
        assignment.put(new Point(6, 8), Land.MOUNTAIN);
        assignment.put(new Point(8, 8), Land.FIELDS);
        assignment.put(new Point(10, 8), Land.FOREST);

        assignment.put(new Point(3, 11), Land.FOREST);
        assignment.put(new Point(5, 11), Land.HILLS);
        assignment.put(new Point(7, 11), Land.DESERT);
        assignment.put(new Point(9, 11), Land.MOUNTAIN);
        assignment.put(new Point(11, 11), Land.FIELDS);

        assignment.put(new Point(4, 14), Land.FIELDS);
        assignment.put(new Point(6, 14), Land.MOUNTAIN);
        assignment.put(new Point(8, 14), Land.FOREST);
        assignment.put(new Point(10, 14), Land.PASTURE);

        assignment.put(new Point(5, 17), Land.PASTURE);
        assignment.put(new Point(7, 17), Land.HILLS);
        assignment.put(new Point(9, 17), Land.HILLS);

        return Collections.unmodifiableMap(assignment);
    }

}
