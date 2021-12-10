package ch.zhaw.catan.utilities;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.infrastructure.Road.initialRoadBuild;
import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
import static ch.zhaw.catan.player.Faction.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class can be used to prepare some predefined siedler game situations and, for some
 * of the situations, it provides information about the expected game state,
 * for example the number of resource cards in each player's stock or the expected resource
 * card payout when the dices are thrown (for each dice value).
 * <br>
 * The basic game situations upon which all other situations that can be retrieved are based is
 * the following:
 * <pre>
 *                                 (  )            (  )            (  )            (  )
 *                              //      \\      //      \\      //      \\      //      \\
 *                         (  )            (  )            (  )            (  )            (  )
 *                          ||      ~~      ||      ~~      ||      ~~      ||      ~~      ||
 *                          ||              ||              ||              ||              ||
 *                         (  )            (  )            (  )            (  )            (  )
 *                      //      \\      //      \\      //      \\      //      \\      //      \\
 *                 (  )            (  )            (  )            (bb)            (  )            (  )
 *                  ||      ~~      ||      LU      ||      WL      bb      WL      ||      ~~      ||
 *                  ||              ||      06      ||      03      bb      08      ||              ||
 *                 (  )            (  )            (  )            (  )            (  )            (  )
 *              //      \\      //      \\      rr      \\      //      \\      //      \\      //      \\
 *         (  )            (  )            (rr)            (  )            (  )            (  )            (  )
 *          ||      ~~      ||      GR      ||      OR      ||      GR      ||      LU      ||      ~~      ||
 *          ||              ||      02      ||      04      ||      05      ||      10      ||              ||
 *         (  )            (  )            (  )            (  )            (  )            (  )            (  )
 *      //      \\      //      \\      //      \\      //      \\      //      \\      //      \\      //      \\
 * (  )            (  )            (  )            (  )            (  )            (  )            (  )            (  )
 *  ||      ~~      gg      LU      ||      BR      ||      --      ||      OR      ||      GR      ||      ~~      ||
 *  ||              gg      05      ||      09      ||      07      ||      06      ||      09      ||              ||
 * (  )            (gg)            (  )            (  )            (  )            (  )            (  )            (  )
 *      \\      //      \\      //      \\      //      \\      //      \\      //      \\      bb      \\      //
 *         (  )            (  )            (  )            (  )            (  )            (bb)            (  )
 *          ||      ~~      ||      GR      ||      OR      ||      LU      ||      WL      ||      ~~      ||
 *          ||              ||      10      ||      11      ||      03      ||      12      ||              ||
 *         (  )            (  )            (  )            (  )            (  )            (  )            (  )
 *              \\      //      \\      //      \\      //      \\      //      rr      //      \\      //
 *                 (  )            (  )            (  )            (  )            (rr)            (  )
 *                  ||      ~~      ||      WL      ||      BR      ||      BR      ||      ~~      ||
 *                  ||              ||      08      ||      04      ||      11      ||              ||
 *                 (  )            (  )            (  )            (  )            (  )            (  )
 *                      \\      //      \\      //      \\      gg      \\      //      \\      //
 *                         (  )            (  )            (gg)            (  )            (  )
 *                          ||      ~~      ||      ~~      ||      ~~      ||      ~~      ||
 *                          ||              ||              ||              ||              ||
 *                         (  )            (  )            (  )            (  )            (  )
 *                              \\      //      \\      //      \\      //      \\      //
 *                                 (  )            (  )            (  )            (  )
 * </pre>
 * Resource cards after the setup phase:
 *  <ul>
 *  <li>Player 1: WOOL BRICK</li>
 *  <li>Player 2: WOOL WOOL</li>
 *  <li>Player 3: BRICK</li>
 *  </ul>
 * <p>The main ideas for this setup were the following:</p>
 * <ul>
 * <li>Player one has access to all resource types from the start so that any resource card can be acquired by
 * throwing the corresponding dice value.</li>
 * <li>The settlements are positioned in a way that for each dice value, there is only one resource card paid
 * to one player, except for the dice values 4 and 12.</li>
 * <li>There is a settlement next to water and the owner has access to resource types required to build roads</li>
 * <li>The initial resource card stock of each player does not allow to build anything without getting
 * additional resources first</li>
 * </ul>
 *
 * @author tebe
 */
public class ThreePlayerStandard {
    public static final Map<Faction, Tuple<Point, Point>> INITIAL_SETTLEMENT_POSITIONS =
            Map.of(RED, new Tuple<>(new Point(5, 7), new Point(10, 16)),
                    BLUE, new Tuple<>(new Point(11, 13), new Point(8, 4)),
                    GREEN, new Tuple<>(new Point(2, 12), new Point(7, 19)));
    public static final Map<Faction, Tuple<Point, Point>> INITIAL_ROAD_ENDPOINTS = Map.of(values()[0],
            new Tuple<>(new Point(6, 6), new Point(9, 15)), values()[1],
            new Tuple<>(new Point(12, 12), new Point(8, 6)), values()[2],
            new Tuple<>(new Point(2, 10), new Point(8, 18)));

    /**
     * Returns a siedler game after the setup phase in the setup
     * and with the initial resource card setup as described
     * in {@link ThreePlayerStandard}.
     *
     * @return the siedler game
     */
    public static GameDataContainer getAfterSetupPhase() {
        final List<DiceResult> diceResults = Arrays.asList(new DiceResult(12, new Player(RED)),
                new DiceResult(5, new Player(BLUE)),
                new DiceResult(3, new Player(GREEN)));
        TurnOrderHandler turnOrderHandler = new TurnOrderHandler();
        turnOrderHandler.determineInitialTurnOrder(diceResults);
        SettlersBoard settlersBoard = new SettlersBoard();

        for (int i = 0; i < turnOrderHandler.getPlayerTurnOrder().size(); i++) {
            final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
            Faction faction = currentPlayer.getPlayerFaction();
            assertTrue(initialSettlementBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, settlersBoard));
            assertTrue(initialRoadBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).first, INITIAL_ROAD_ENDPOINTS.get(faction).first, settlersBoard));
            turnOrderHandler.switchToNextPlayer();
        }
        for (int i = 0; i < turnOrderHandler.getPlayerTurnOrder().size(); i++) {
            turnOrderHandler.switchToPreviousPlayer();
            final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
            Faction faction = currentPlayer.getPlayerFaction();
            assertTrue(initialSettlementBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, settlersBoard));

            assertTrue(initialRoadBuild(currentPlayer, INITIAL_SETTLEMENT_POSITIONS.get(faction).second, INITIAL_ROAD_ENDPOINTS.get(faction).second, settlersBoard));
        }

        return new GameDataContainer(settlersBoard, turnOrderHandler);
    }
}
