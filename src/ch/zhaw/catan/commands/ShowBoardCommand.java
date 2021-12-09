package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ShowBoardCommand implements Command {

    private final SettlersBoard settlersBoard;
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public ShowBoardCommand(SettlersBoard settlersBoard) {
        this.settlersBoard = settlersBoard;

    }

    @Override
    public void execute() {
        final SettlersBoardTextView settlersBoardTextView = new SettlersBoardTextView(settlersBoard);
        textTerminal.println(settlersBoardTextView.toString());

    }
}
