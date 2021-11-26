package ch.zhaw.catan.board;

import ch.zhaw.hexboard.HexBoardTextView;

import java.awt.*;
import java.util.Map;

import static ch.zhaw.hexboard.Label.convertToFieldValueToLabel;

/**
 * Settlerboard specific textview implementation
 *
 * @author abuechi
 * @version 1.0.0
 */
public class SettlersBoardTextView extends HexBoardTextView<Land, String, String, String> {

    /**
     * Creates a TextView based on given board.
     *
     * @param board the gameboard used for playing
     */
    public SettlersBoardTextView(SettlersBoard board) {
        super(board);
        setLowerFieldLabelsForBoard(board);
    }

    private void setLowerFieldLabelsForBoard(SettlersBoard board) {
        for (Map.Entry<Point, Integer> gameField : board.getDiceNumberPlacements().entrySet()) {
            setLowerFieldLabel(gameField.getKey(), convertToFieldValueToLabel(gameField.getValue()));
        }
    }


}
