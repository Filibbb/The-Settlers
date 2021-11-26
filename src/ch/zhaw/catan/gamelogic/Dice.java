package ch.zhaw.catan.gamelogic;

import ch.zhaw.catan.player.Player;
import ch.zhaw.catan.gamelogic.Commands;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Random;
import java.util.Set;

public class Dice {
    Random random = new Random();
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * The player with the highest number has to start.
     *
     * @param participants the set with all players/participants.
     * @return the player with the highest firs throw.
     * @author fupat002
     */
    //TODO let the players roll the dice themselves!
    public Player highRoll(Set<Player> participants) {
        Object[] players = participants.toArray();

        int[] diceThrows = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            diceThrows[i] = dice();
        }
        int highestDiceValue = getMaxDiceValue(diceThrows);
        if (highestValueIsDuplicated(diceThrows, highestDiceValue)) {
            return highRoll(participants);
        } else {
            return (Player) players[indexOfPlayerWithHighestRoll(diceThrows, highestDiceValue)];
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
        return highestDiceValue < 1;
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

    /**
     * rolls the dice and distributes the materials to the players.
     *
     * @param players the set with all players.
     * @author fupat002
     */
    public void throwDice(Set<Player> players) {
        int diceValue = dice();
        if (diceValue == 7) {
            splitResourcesOfPlayersWithMoreThanSeven(players);
        } else {
            for (Player player : players) {
                handOutResourcesAfterDiceThrow(player, diceValue);
            }
        }
    }

    private void splitResourcesOfPlayersWithMoreThanSeven(Set<Player> players) {
        for (Player player : players) {
            if (player.playerHasMoreThanSevenResources()) {
                player.deletesHalfOfResources();
            }
        }
    }

    private void handOutResourcesAfterDiceThrow(Player player, int diceValue) {
        if (player.playerOccupiesField(diceValue)) {
            player.addResourceCardToHand(player.getResourceByDiceValue(diceValue), player.countResourcePointsOnRolledFields(diceValue));
        }
    }

    private int playerThrowDice(){
        textTerminal.println("It's your turn. Roll the dice with: " + Commands.getCommandByRepresentation("ROLLDICE"));
        //TODO:!!!
        return 0;
    }

    private int dice() {
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);
        return firstDice + secondDice;
    }
}
