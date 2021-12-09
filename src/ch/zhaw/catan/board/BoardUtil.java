package ch.zhaw.catan.board;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ch.zhaw.catan.board.Land.*;

/**
 * A utility class for creating a board
 *
 * @author abuechi
 */
public class BoardUtil {

    /**
     * Must not be called.
     */
    private BoardUtil() {
    }

    /**
     * Returns the default mapping of the dice values per field.
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
        Point[] waterFields = {new Point(4, 2), new Point(6, 2), new Point(8, 2), new Point(10, 2),
                new Point(3, 5), new Point(11, 5), new Point(2, 8), new Point(12, 8),
                new Point(1, 11), new Point(13, 11), new Point(2, 14), new Point(12, 14),
                new Point(3, 17), new Point(11, 17), new Point(4, 20), new Point(6, 20),
                new Point(8, 20), new Point(10, 20)};

        for (Point waterField : waterFields) {
            landPlacements.put(waterField, WATER);
        }

        landPlacements.put(new Point(5, 5), FOREST);
        landPlacements.put(new Point(7, 5), PASTURE);
        landPlacements.put(new Point(9, 5), PASTURE);

        landPlacements.put(new Point(4, 8), FIELDS);
        landPlacements.put(new Point(6, 8), MOUNTAIN);
        landPlacements.put(new Point(8, 8), FIELDS);
        landPlacements.put(new Point(10, 8), FOREST);

        landPlacements.put(new Point(3, 11), FOREST);
        landPlacements.put(new Point(5, 11), HILLS);
        landPlacements.put(new Point(7, 11), DESERT);
        landPlacements.put(new Point(9, 11), MOUNTAIN);
        landPlacements.put(new Point(11, 11), FIELDS);

        landPlacements.put(new Point(4, 14), FIELDS);
        landPlacements.put(new Point(6, 14), MOUNTAIN);
        landPlacements.put(new Point(8, 14), FOREST);
        landPlacements.put(new Point(10, 14), PASTURE);

        landPlacements.put(new Point(5, 17), PASTURE);
        landPlacements.put(new Point(7, 17), HILLS);
        landPlacements.put(new Point(9, 17), HILLS);

        return Collections.unmodifiableMap(landPlacements);
    }
}
