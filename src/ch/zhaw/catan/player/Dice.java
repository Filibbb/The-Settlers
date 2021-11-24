package ch.zhaw.catan.player;

import java.util.Random;
import java.util.Set;

public class Dice {
    Random random = new Random();

    /**
     * The player with the highest number has to start.
     *
     * @param participants the set with all players/participants.
     * @return the player with the highest firs throw.
     * @author fupat002
     */
    public Player highRoll(Set<Player> participants) {
        Object[] players = participants.toArray();
        int[] diceThrows = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            diceThrows[i] = dice();
        }
        int highestDiceValue = getMaxDiceValue(diceThrows);
        if (highestValueIsDouble(diceThrows, highestDiceValue)) {
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

    private boolean highestValueIsDouble(int[] diceThrows, int highestDiceValue) {
        int highestValueCounter = 0;
        for (int diceThrow : diceThrows) {
            if (diceThrow == highestDiceValue) {
                highestValueCounter++;
            }
        }
        return highestDiceValue < 1;
    }

    private int getMaxDiceValue(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
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
                player.handOverHalfOfResources();
            }
        }
    }

    private void handOutResourcesAfterDiceThrow(Player player, int diceValue) {
        if (player.playerOccupiesField(diceValue)) {
            player.addResourceCardToHand(player.getResourceByDiceValue(diceValue), player.countWinningPointsOnRolledFields(diceValue));
        }
    }

    private int dice() {
        int firstDice = 1 + random.nextInt(6);
        int secondDice = 1 + random.nextInt(6);
        return firstDice + secondDice;
    }
}
