package ch.zhaw.catan.board;

import ch.zhaw.catan.infrastructure.AbstractInfrastructure;
import ch.zhaw.catan.infrastructure.Road;
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
public class SettlersBoardTextView extends HexBoardTextView<Land, AbstractInfrastructure, Road, String> {

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
