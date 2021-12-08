package ch.zhaw.catan.games;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Thief;
import ch.zhaw.catan.game.logic.TurnOrderHandler;

/**
 * This class is a utility class that contains the essential logic classes for playing the settlers game itself.
 * As its not required to test the terminal logic which is settled within the {SettlersGame.class} but instead to have the board and turnOrderhandler that contains the most logic.
 *
 * @author abuechi
 */
public class GameDataContainer {
    private final SettlersBoard settlersBoard;
    private final TurnOrderHandler turnOrderHandler;
    private final Thief thief;

    /**
     * Creates a data holder object used for testing purposes
     *
     * @param settlersBoard    the board instance used for the current game
     * @param turnOrderHandler the turnorder handler containing informations about the players
     */
    public GameDataContainer(SettlersBoard settlersBoard, TurnOrderHandler turnOrderHandler, Thief thief) {
        this.settlersBoard = settlersBoard;
        this.turnOrderHandler = turnOrderHandler;
        this.thief = thief;
    }

    public SettlersBoard getSettlersBoard() {
        return settlersBoard;
    }

    public TurnOrderHandler getTurnOrderHandler() {
        return turnOrderHandler;
    }

    public Thief getThief() {
        return thief;
    }
}
