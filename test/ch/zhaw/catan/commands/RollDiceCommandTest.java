package ch.zhaw.catan.commands;


import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing class to check if RollDiceCommand works correctly.
 *
 * @author fupat002
 * @version 1.0.0
 */
public class RollDiceCommandTest {

    private static final int DICE_VALUE = 5;
    private final List<Player> players = new ArrayList<>();
    private final SettlersBoard settlersBoard = new SettlersBoard();

    /**
     * Creates initial dice roll test.
     */
    @BeforeEach
    public void setUp() {
        players.add(new Player(Faction.RED));
        players.add(new Player(Faction.BLUE));
        players.add(new Player(Faction.GREEN));
        //auf dem Settlers board die Spieler einem Feld zuweisen.
    }

    /**
     * Tests if handing out the resource of the rolled field works.
     */
    @Test
    public void RollDiceCommand(){
        //TODO: Implement when building and RollDiceCommand is finished.
    }
}
