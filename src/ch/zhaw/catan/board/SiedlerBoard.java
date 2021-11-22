package ch.zhaw.catan.board;

import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SiedlerBoard extends HexBoard<Land, String, String, String> {


    //TODO: Add fields, constructors and methods as you see fit. Do NOT change the signature
    //      of the methods below.

    /**
     * Returns the fields associated with the specified dice value.
     *
     * @param dice the dice value
     * @return the fields associated with the dice value
     */
    public List<Point> getFieldsForDiceValue(int dice) {
        //TODO: Implement.
        return Collections.emptyList();
    }

    /**
     * Returns the {@link Land}s adjacent to the specified corner.
     *
     * @param corner the corner
     * @return the list with the adjacent {@link Land}s
     */
    public List<Land> getLandsForCorner(Point corner) {
        //TODO: Implement.
        return Collections.emptyList();
    }
}
