package ch.zhaw.catan.games;

import ch.zhaw.catan.SiedlerGame;
import ch.zhaw.catan.Tuple;
import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.player.Faction;

import java.awt.*;
import java.util.List;
import java.util.Map;

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
    public final static int NUMBER_OF_PLAYERS = 3;

    public static final Map<Faction, Tuple<Point, Point>> INITIAL_SETTLEMENT_POSITIONS =
            Map.of(Faction.values()[0], new Tuple<>(new Point(5, 7), new Point(10, 16)),
                    Faction.values()[1], new Tuple<>(new Point(11, 13), new Point(8, 4)),
                    Faction.values()[2], new Tuple<>(new Point(2, 12), new Point(7, 19)));

    public static final Map<Faction, Tuple<Point, Point>> INITIAL_ROAD_ENDPOINTS = Map.of(Faction.values()[0],
            new Tuple<>(new Point(6, 6), new Point(9, 15)), Faction.values()[1],
            new Tuple<>(new Point(12, 12), new Point(8, 6)), Faction.values()[2],
            new Tuple<>(new Point(2, 10), new Point(8, 18)));

    public static final Map<Faction, Map<Resource, Integer>> INITIAL_PLAYER_CARD_STOCK = Map.of(
            Faction.values()[0], Map.of(Resource.GRAIN, 0, Resource.WOOL, 1,
                    Resource.BRICK, 1, Resource.ORE, 0, Resource.LUMBER, 0),
            Faction.values()[1],
            Map.of(Resource.GRAIN, 0, Resource.WOOL, 2, Resource.BRICK, 0,
                    Resource.ORE, 0, Resource.LUMBER, 0),
            Faction.values()[2],
            Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 1,
                    Resource.ORE, 0, Resource.LUMBER, 0));

    public static final Map<Faction, Map<Resource, Integer>> BANK_ALMOST_EMPTY_RESOURCE_CARD_STOCK = Map.of(
            Faction.values()[0], Map.of(Resource.GRAIN, 8, Resource.WOOL, 9,
                    Resource.BRICK, 9, Resource.ORE, 7, Resource.LUMBER, 9),
            Faction.values()[1],
            Map.of(Resource.GRAIN, 8, Resource.WOOL, 10, Resource.BRICK, 0,
                    Resource.ORE, 0, Resource.LUMBER, 0),
            Faction.values()[2],
            Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 8,
                    Resource.ORE, 0, Resource.LUMBER, 9));

    public static final Map<Faction, Map<Resource, Integer>> PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_RESOURCE_CARD_STOCK = Map.of(
            Faction.values()[0], Map.of(Resource.GRAIN, 2, Resource.WOOL, 2,
                    Resource.BRICK, 3, Resource.ORE, 0, Resource.LUMBER, 3),
            Faction.values()[1],
            Map.of(Resource.GRAIN, 0, Resource.WOOL, 5, Resource.BRICK, 0,
                    Resource.ORE, 0, Resource.LUMBER, 0),
            Faction.values()[2],
            Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.BRICK, 1,
                    Resource.ORE, 0, Resource.LUMBER, 0));

    public static final Map<Integer, Map<Faction, List<Resource>>> INITIAL_DICE_THROW_PAYOUT = Map.of(
            2, Map.of(
                    Faction.values()[0], List.of(Resource.GRAIN),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            3, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.WOOL),
                    Faction.values()[2], List.of()),
            4, Map.of(
                    Faction.values()[0], List.of(Resource.ORE),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of(Resource.BRICK)),
            5, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of(Resource.LUMBER)),
            6, Map.of(
                    Faction.values()[0], List.of(Resource.LUMBER),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            8, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.WOOL),
                    Faction.values()[2], List.of()),
            9, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.GRAIN),
                    Faction.values()[2], List.of()),
            10, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            11, Map.of(
                    Faction.values()[0], List.of(Resource.BRICK),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            12, Map.of(
                    Faction.values()[0], List.of(Resource.WOOL),
                    Faction.values()[1], List.of(Resource.WOOL),
                    Faction.values()[2], List.of()));

    public static final Map<Resource, Integer> RESOURCE_CARDS_IN_BANK_AFTER_STARTUP_PHASE = Map.of(Resource.LUMBER, 19,
            Resource.BRICK, 17, Resource.WOOL, 16, Resource.GRAIN, 19, Resource.ORE, 19);

    public static final Point PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_FIFTH_SETTLEMENT_POSITION = new Point(9, 13);
    public static final List<Point> playerOneReadyToBuildFifthSettlementAllSettlementPositions =
            List.of(INITIAL_SETTLEMENT_POSITIONS.get(Faction.values()[0]).first,
                    INITIAL_SETTLEMENT_POSITIONS.get(Faction.values()[0]).second,
                    new Point(7, 7), new Point(6, 4), PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_FIFTH_SETTLEMENT_POSITION);

    /**
     * Returns a siedler game after the setup phase in the setup
     * and with the initial resource card setup as described
     * in {@link ThreePlayerStandard}.
     *
     * @param winpoints the number of points required to win the game
     * @return the siedler game
     */
    public static SiedlerGame getAfterSetupPhase(int winpoints) {
        SiedlerGame model = new SiedlerGame(winpoints);
        model.addPlayersToGame(NUMBER_OF_PLAYERS);
        for (int i = 0; i < model.getPlayerFactions().size(); i++) {
            Faction f = model.getCurrentPlayerFaction();
            assertTrue(model.placeInitialSettlement(INITIAL_SETTLEMENT_POSITIONS.get(f).first, false));
            assertTrue(model.placeInitialRoad(INITIAL_SETTLEMENT_POSITIONS.get(f).first, INITIAL_ROAD_ENDPOINTS.get(f).first));
            model.switchToNextPlayer();
        }
        for (int i = 0; i < model.getPlayerFactions().size(); i++) {
            model.switchToPreviousPlayer();
            Faction f = model.getCurrentPlayerFaction();
            assertTrue(model.placeInitialSettlement(INITIAL_SETTLEMENT_POSITIONS.get(f).second, true));
            assertTrue(model.placeInitialRoad(INITIAL_SETTLEMENT_POSITIONS.get(f).second, INITIAL_ROAD_ENDPOINTS.get(f).second));
        }
        return model;
    }

    /**
     * Returns a siedler game after the setup phase in the setup
     * described in {@link ThreePlayerStandard} and with the bank almost empty.
     * <p>
     * The following resource cards should be in the stock of the bank:
     * <ul>
     * <li>LUMBER: 1</li>
     * <li>BRICK: 2</li>
     * <li>GRAIN: 3</li>
     * <li>ORE: 13</li>
     * <li>WOOL: 0</li>
     * </ul>
     * <p>
     * The stocks of the players should contain:
     * <br>
     * Player 1:
     * <ul>
     * <li>LUMBER: 9</li>
     * <li>BRICK: 9</li>
     * <li>GRAIN: 8</li>
     * <li>ORE: 7</li>
     * <li>WOOL: 9</li>
     * </ul>
     * Player 2:
     * <ul>
     * <li>LUMBER: 0</li>
     * <li>BRICK: 0</li>
     * <li>GRAIN: 8</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 10</li>
     * </ul>
     * Player 3:
     * <ul>
     * <li>LUMBER: 9</li>
     * <li>BRICK: 8</li>
     * <li>GRAIN: 0</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 0</li>
     * </ul>
     *
     * @param winpoints the number of points required to win the game
     * @return the siedler game
     */
    public static SiedlerGame getAfterSetupPhaseAlmostEmptyBank(int winpoints) {
        SiedlerGame model = getAfterSetupPhase(winpoints);
        throwDiceMultipleTimes(model, 6, 9);
        throwDiceMultipleTimes(model, 11, 8);
        throwDiceMultipleTimes(model, 2, 8);
        throwDiceMultipleTimes(model, 4, 7);
        throwDiceMultipleTimes(model, 12, 8);
        throwDiceMultipleTimes(model, 5, 9);
        throwDiceMultipleTimes(model, 9, 8);
        return model;
    }


    /**
     * Returns a {@link SiedlerGame} with several roads added but none longer than
     * 4 elements. Hence, no player meets the longest road criteria yet. Furthermore,
     * players one and three have enough resource cards to build additional roads and settlements.
     *
     * <p></p>
     * <p>The game board should look as follows:
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
     *          ||      ~~      gg      GR      rr      OR      ||      GR      ||      LU      ||      ~~      ||
     *          ||              gg      02      rr      04      ||      05      ||      10      ||              ||
     *         (  )            (  )            (  )            (  )            (  )            (  )            (  )
     *      //      \\      gg      rr      rr      \\      //      \\      //      \\      //      \\      //      \\
     * (  )            (  )            (  )            (  )            (  )            (  )            (  )            (  )
     *  ||      ~~      gg      LU      ||      BR      ||      --      ||      OR      ||      GR      ||      ~~      ||
     *  ||              gg      05      ||      09      ||      07      ||      06      ||      09      ||              ||
     * (  )            (gg)            (  )            (  )            (  )            (  )            (  )            (  )
     *      \\      //      gg      //      \\      //      \\      //      \\      rr      \\      bb      \\      //
     *         (  )            (  )            (  )            (  )            (  )            (bb)            (  )
     *          ||      ~~      ||      GR      ||      OR      ||      LU      rr      WL      ||      ~~      ||
     *          ||              ||      10      ||      11      ||      03      rr      12      ||              ||
     *         (  )            (  )            (  )            (  )            (  )            (  )            (  )
     *              \\      //      \\      //      \\      //      \\      //      rr      rr      \\      //
     *                 (  )            (  )            (  )            (  )            (rr)            (  )
     *                  ||      ~~      ||      WL      gg      BR      gg      BR      rr      ~~      ||
     *                  ||              ||      08      gg      04      gg      11      rr              ||
     *                 (  )            (  )            (  )            (  )            (  )            (  )
     *                      \\      //      \\      //      gg      gg      \\      //      \\      //
     *                         (  )            (  )            (gg)            (  )            (  )
     *                          ||      ~~      ||      ~~      ||      ~~      ||      ~~      ||
     *                          ||              ||              ||              ||              ||
     *                         (  )            (  )            (  )            (  )            (  )
     *                              \\      //      \\      //      \\      //      \\      //
     *                                 (  )            (  )            (  )            (  )
     * </pre>
     * <p>
     * And the player resource card stocks:
     * <br>
     * Player 1:
     * <ul>
     * <li>LUMBER: 6</li>
     * <li>BRICK: 6</li>
     * <li>GRAIN: 1</li>
     * <li>ORE: 11</li>
     * <li>WOOL: 1</li>
     * </ul>
     * Player 2:
     * <ul>
     * <li>LUMBER: 0</li>
     * <li>BRICK: 0</li>
     * <li>GRAIN: 0</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 2</li>
     * </ul>
     * Player 3:
     * <ul>
     * <li>LUMBER: 6</li>
     * <li>BRICK: 6</li>
     * <li>GRAIN: 1</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 1</li>
     * </ul>
     *
     * @param winpoints the number of points required to win the game
     * @return the siedler game
     */
    public static SiedlerGame getAfterSetupPhaseSomeRoads(int winpoints) {
        SiedlerGame model = getAfterSetupPhase(winpoints);
        throwDiceMultipleTimes(model, 6, 7);
        throwDiceMultipleTimes(model, 11, 6);
        throwDiceMultipleTimes(model, 4, 5);
        throwDiceMultipleTimes(model, 5, 6);
        throwDiceMultipleTimes(model, 2, 1);

        model.switchToNextPlayer();
        model.switchToNextPlayer();
        model.buildRoad(new Point(2, 12), new Point(3, 13));
        buildRoad(model, List.of(new Point(2, 10), new Point(3, 9), new Point(3, 7)));
        model.buildRoad(new Point(8, 18), new Point(8, 16));
        buildRoad(model, List.of(new Point(7, 19), new Point(6, 18), new Point(6, 16)));
        model.switchToNextPlayer();
        model.buildRoad(new Point(10, 16), new Point(11, 15));
        model.buildRoad(new Point(10, 16), new Point(10, 18));
        buildRoad(model, List.of(new Point(9, 15), new Point(9, 13), new Point(10, 12)));
        buildRoad(model, List.of(new Point(5, 7), new Point(5, 9), new Point(4, 10), new Point(3, 9)));

        throwDiceMultipleTimes(model, 6, 6);
        throwDiceMultipleTimes(model, 11, 6);
        throwDiceMultipleTimes(model, 4, 6);
        throwDiceMultipleTimes(model, 5, 6);

        model.switchToNextPlayer();
        model.switchToNextPlayer();
        throwDiceMultipleTimes(model, 5, 4);
        model.tradeWithBankFourToOne(Resource.LUMBER, Resource.GRAIN);
        throwDiceMultipleTimes(model, 5, 4);
        model.tradeWithBankFourToOne(Resource.LUMBER, Resource.WOOL);
        model.switchToNextPlayer();
        return model;
    }


    private static SiedlerGame throwDiceMultipleTimes(SiedlerGame model, int diceValue, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            model.throwDice(diceValue);
        }
        return model;
    }

    /**
     * Returns a siedler game after building four additional roads and two
     * settlements after the setup phase with the resource cards and roads
     * for player one ready to build a fifth settlement at {@link #PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_FIFTH_SETTLEMENT_POSITION}
     * <p></p>
     * <p>The game board should look as follows:
     * <pre>
     *                                 (  )            (  )            (  )            (  )
     *                              //      \\      //      \\      //      \\      //      \\
     *                         (  )            (  )            (  )            (  )            (  )
     *                          ||      ~~      ||      ~~      ||      ~~      ||      ~~      ||
     *                          ||              ||              ||              ||              ||
     *                         (  )            (  )            (  )            (  )            (  )
     *                      //      \\      //      \\      //      \\      //      \\      //      \\
     *                 (  )            (  )            (rr)            (bb)            (  )            (  )
     *                  ||      ~~      ||      LU      rr      WL      bb      WL      ||      ~~      ||
     *                  ||              ||      06      rr      03      bb      08      ||              ||
     *                 (  )            (  )            (  )            (  )            (  )            (  )
     *              //      \\      //      \\      rr      rr      //      \\      //      \\      //      \\
     *         (  )            (  )            (rr)            (rr)            (  )            (  )            (  )
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
     *          ||      ~~      ||      GR      ||      OR      ||      LU      rr      WL      ||      ~~      ||
     *          ||              ||      10      ||      11      ||      03      rr      12      ||              ||
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
     *
     * </pre>
     * <br>
     * <p>And the player resource card stocks:</p>
     * <br>
     * Player 1:
     * <ul>
     * <li>LUMBER: 3</li>
     * <li>BRICK: 3</li>
     * <li>GRAIN: 2</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 2</li>
     * </ul>
     * Player 2:
     * <ul>
     * <li>LUMBER: 0</li>
     * <li>BRICK: 0</li>
     * <li>GRAIN: 0</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 5</li>
     * </ul>
     * Player 3:
     * <ul>
     * <li>LUMBER: 0</li>
     * <li>BRICK: 1</li>
     * <li>GRAIN: 0</li>
     * <li>ORE: 0</li>
     * <li>WOOL: 0</li>
     * </ul>
     *
     * @param winpoints the number of points required to win the game
     * @return the siedler game
     */
    public static SiedlerGame getPlayerOneReadyToBuildFifthSettlement(int winpoints) {
        SiedlerGame model = getAfterSetupPhase(winpoints);
        //generate resources to build four roads and four settlements.
        throwDiceMultipleTimes(model, 6, 8);
        throwDiceMultipleTimes(model, 11, 7);
        throwDiceMultipleTimes(model, 2, 4);
        throwDiceMultipleTimes(model, 12, 3);
        model.buildRoad(new Point(6, 6), new Point(7, 7));
        model.buildRoad(new Point(6, 6), new Point(6, 4));
        model.buildRoad(new Point(9, 15), new Point(9, 13));
        model.buildSettlement(playerOneReadyToBuildFifthSettlementAllSettlementPositions.get(2));
        model.buildSettlement(playerOneReadyToBuildFifthSettlementAllSettlementPositions.get(3));
        return model;
    }

    private static void buildSettlement(SiedlerGame model, Point position, List<Point> roads) {
        buildRoad(model, roads);
        assertTrue(model.buildSettlement(position));
    }

    private static void buildRoad(SiedlerGame model, List<Point> roads) {
        for (int i = 0; i < roads.size() - 1; i++) {
            assertTrue(model.buildRoad(roads.get(i), roads.get(i + 1)));
        }
    }
}
