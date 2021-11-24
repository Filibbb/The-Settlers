package ch;

import ch.zhaw.catan.GameSetup;

/**
 * Simple main class to start the game.
 * @author weberph5
 * @version 1.0.0
 */
public class Application {
    /**
     * Main Method to start the game. No arguments needed.
     * @param args none needed.
     */

    public static void main(String[] args) {
        GameSetup gameSetup = new GameSetup();
        gameSetup.startGame();
    }
}
