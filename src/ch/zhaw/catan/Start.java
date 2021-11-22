package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.InputReader;
import org.beryx.textio.TextTerminal;

public class Start {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();

   public void startGame(){
       printIntroduction();
       int numberOfPlayers = textIO.newIntInputReader().withMinVal(2).withMaxVal(4).read("Please enter the number of players. 2, 3 or 4 players are supported.");
       int winPoints = textIO.newIntInputReader().withMinVal(3).withMaxVal(12).read("Please enter the win points needed to win.");
       SiedlerGame game = new SiedlerGame(winPoints,numberOfPlayers);
   }

    private void printIntroduction(){
        textTerminal.println("Welcome to The Settlers!");
    }
}
