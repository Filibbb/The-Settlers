package ch.zhaw.catan;

import ch.zhaw.catan.board.Land;
import ch.zhaw.catan.board.SiedlerBoard;
import ch.zhaw.hexboard.HexBoardTextView;

public class SiedlerBoardTextView extends HexBoardTextView<Land, String, String, String> {

    public SiedlerBoardTextView(SiedlerBoard board) {
        super(board);
    }

}
