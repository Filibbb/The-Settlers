package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;

/**
 * Contains the logic for the ShowBoardCommand
 *
 * @author weberph5
 */
public class ShowBoardCommand implements Command {

    private final SettlersBoard settlersBoard;

    /**
     * Creates an instance of ShowBoardCommand
     *
     * @param settlersBoard the current SettlersBoard
     * @author weberph5
     */
    public ShowBoardCommand(SettlersBoard settlersBoard) {
        this.settlersBoard = settlersBoard;
    }

    /**
     * Executes the ShowBoardCommand
     *
     * @author weberph5
     */
    @Override
    public void execute() {
        final SettlersBoardTextView settlersBoardTextView = new SettlersBoardTextView(settlersBoard);
        settlersBoardTextView.printBoard();
    }
}
