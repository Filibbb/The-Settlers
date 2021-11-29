package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The dice class that handles dicing of the players and provides dicing methods.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class Dice {
    private final Random random = new Random();
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * The player with the highest number has to start.
     *
     * @param players the set with all players/participants.
     * @return the player with the highest firs throw.
     * @author fupat002
     */
    public List<DiceResult> rollForPlayers(List<Player> players) {
        List<DiceResult> diceThrows = new ArrayList<>(players.size());
        for (Player player : players) {
            String inputtedText = textIO.newStringInputReader().read("It is the turn of the player with the faction " + player.getPlayerFaction() + ". Roll the dice with: \"ROLL DICE\"");
            if (inputtedText.equals("ROLL DICE")) {
                final int dicedResult = dice();
                diceThrows.add(new DiceResult(dicedResult, player));
                textTerminal.println("Player " + player.getPlayerFaction() + "  rolled a " + dicedResult);
            } else {
                textTerminal.println("Your input is invalid and so is your roll. Restarting the dicing...");
                return rollForPlayers(players);
            }
        }
        return diceThrows;
    }

    /**
     * Roll a 6x6 dice.
     *
     * @return the pseudo random value of the dice result.
     */
    public int dice() {
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);
        return firstDice + secondDice;
    }
}
