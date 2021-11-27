package ch.zhaw.catan.gamelogic;

import ch.zhaw.catan.player.Player;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Dice {
    private Random random = new Random();
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * The player with the highest number has to start.
     *
     * @param players the set with all players/participants.
     * @return the player with the highest firs throw.
     * @author fupat002
     */
    public Player highRoll(ArrayList<Player> players) {
        int[] diceThrows = new int[players.size()];
        for (int i = 0; i < players.size(); i++) {
            String inputtedText = textIO.newStringInputReader().read("It is the turn of the player with the faction " + players.get(i).getPlayerFaction() + ". Roll the dice with: \"ROLLDICE\"");
            if (inputtedText.equals("ROLLDICE")) {
                diceThrows[i] = dice();
                textTerminal.println("You Rolled a " + diceThrows[i]);
            }else{
                textTerminal.println("Your input is invalid and so is your roll.");//TODO: Validate command in command handler
            }
        }
        int highestDiceValue = getMaxDiceValue(diceThrows);
        if (highestValueIsDuplicated(diceThrows, highestDiceValue)) {
            textTerminal.println("Several players rolled the same number. Do the whole thing again.");
            return highRoll(players);
        } else {
            Player playerWithTheHighestDiceValue = players.get(indexOfPlayerWithHighestRoll(diceThrows, highestDiceValue));
            textTerminal.println("Faction " + playerWithTheHighestDiceValue.getPlayerFaction() + " rolled a " + diceThrows[indexOfPlayerWithHighestRoll(diceThrows, highestDiceValue)] + ". You are the lucky player who is allowed to start.");
            return playerWithTheHighestDiceValue;
        }
    }

    private int indexOfPlayerWithHighestRoll(int[] diceThrows, int highestDiceValue) {
        for (int i = 0; i < diceThrows.length; i++) {
            if (diceThrows[i] == highestDiceValue) {
                return i;
            }
        }
        return 0;
    }

    private boolean highestValueIsDuplicated(int[] diceThrows, int highestDiceValue) {
        int highestValueCounter = 0;
        for (int diceThrow : diceThrows) {
            if (diceThrow == highestDiceValue) {
                highestValueCounter++;
            }
        }
        return highestValueCounter > 1;
    }

    private int getMaxDiceValue(int[] diceThrows) {
        int maxValue = diceThrows[0];
        for (int i = 1; i < diceThrows.length; i++) {
            if (diceThrows[i] > maxValue) {
                maxValue = diceThrows[i];
            }
        }
        return maxValue;
    }

    private int dice() {
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);
        return firstDice + secondDice;
    }
}
