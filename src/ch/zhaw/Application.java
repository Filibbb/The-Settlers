package ch.zhaw;

import ch.zhaw.catan.SettlersGame;
import ch.zhaw.catan.gamelogic.Dice;

/**
 * Simple main class to start the game.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Application {
    private static final int WIN_POINTS = 5;

    /**
     * Main Method to start the game. No arguments needed.
     *
     * @param args none needed.
     */
    public static void main(String[] args) {
        SettlersGame settlersGame = new SettlersGame(WIN_POINTS);
        settlersGame.start();
    }
}
