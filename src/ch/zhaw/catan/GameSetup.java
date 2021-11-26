package ch.zhaw.catan;

import ch.zhaw.catan.board.GameBoard;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.HexBoardTextView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class GameSetup {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();
    final int WIN_POINTS = 5;

   public void startGame(){
       printIntroduction();
       setupNewGame();
   }

    private void printIntroduction(){
        textTerminal.println("Welcome to The Settlers!");
    }

    private void setupNewGame() {
        int numberOfPlayers = textIO.newIntInputReader().withMinVal(2).withMaxVal(4).read("Please enter the number of players. 2, 3 or 4 players are supported.");
        SiedlerGame game = new SiedlerGame(WIN_POINTS,numberOfPlayers);
        GameBoard gameBoard = new GameBoard();
        HexBoard board = gameBoard.boardInit();
        HexBoardTextView view = new HexBoardTextView(board);
        textTerminal.println(view.toString());

   }
}
