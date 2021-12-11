package ch.zhaw.catan.game.logic;

import ch.zhaw.catan.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptNextUserAction;

/**
 * The dice class that handles dicing of the players and provides dicing methods.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class Dice {
    private static final int MAX_BOUND = 6;
    private final Random random = new Random();

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
            printMessage("It is the turn of the player with the faction " + player.getPlayerFaction() + ". Roll the dice with: \"ROLL DICE\"");
            String inputtedText = promptNextUserAction();
            if (inputtedText.equals("ROLL DICE")) {
                final int dicedResult = throwDice();
                diceThrows.add(new DiceResult(dicedResult, player));
                printMessage("Player " + player.getPlayerFaction() + "  rolled a " + dicedResult);
            } else {
                printMessage("Your input is invalid and so is your roll. Restarting the dicing...");
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
    public int throwDice() {
        int firstDice = 1 + random.nextInt(MAX_BOUND);
        int secondDice = 1 + random.nextInt(MAX_BOUND);
        return firstDice + secondDice;
    }
}
