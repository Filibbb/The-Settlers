package ch.zhaw;

import ch.zhaw.catan.SettlersGame;

/**
 * Simple main class to start the game.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Application {
    private static final int WIN_POINTS = 7;

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
