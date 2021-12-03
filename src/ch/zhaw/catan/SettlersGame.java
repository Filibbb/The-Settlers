package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;
import ch.zhaw.catan.commands.CommandHandler;
import ch.zhaw.catan.commands.Commands;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Road;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Faction;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ch.zhaw.catan.commands.Commands.*;
import static ch.zhaw.catan.player.FactionsUtil.getRandomAvailableFaction;


/**
 * This class performs all actions related to modifying the game state.
 * <p>
 * TODO: (your documentation)
 *
 * @author TODO
 */
public class SettlersGame {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private final Dice dice = new Dice();
    private final TurnOrderHandler turnOrderHandler = new TurnOrderHandler();
    private final SettlersBoard settlersBoard = new SettlersBoard();
    private final SettlersBoardTextView settlersBoardTextView = new SettlersBoardTextView(settlersBoard);
    private final CommandHandler commandHandler = new CommandHandler(turnOrderHandler);
    private int requiredPointsToWin = 0;
    private ArrayList<Player> players;

    /**
     * Constructs a SiedlerGame game state object.
     *
     * @param winPoints the number of points required to win the game
     * @throws IllegalArgumentException if winPoints is lower than
     *                                  three or players is not between two and four
     */
    public SettlersGame(int winPoints) {
        requiredPointsToWin = winPoints;
    }

    /**
     * Starts the current game instance.
     */
    public void start() {
        printIntroduction();
        setupNewGame();
        startFoundationPhase();
        startMainPhase();
    }

    private void startMainPhase() {
        boolean samePlayerAsBefore = false;
        while (!hasWinner()) {
            if (!samePlayerAsBefore) {
                final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
                textTerminal.println("It's " + currentPlayer.getPlayerFaction() + " turn. Choose your actions down below:");
                textTerminal.println("If you are done with your turn, enter END TURN command.");
                commandHandler.executeCommand(SHOW_COMMANDS);
            }
            final String userInput = textIO.newStringInputReader().read("Please enter your next action:");
            final Commands commandByRepresentation = getCommandByRepresentation(userInput);
            samePlayerAsBefore = commandByRepresentation != null && commandByRepresentation != END_TURN;
            if (commandByRepresentation != null) {
                commandHandler.executeCommand(commandByRepresentation);
            } else {
                textTerminal.println("This command is not available. Use 'SHOW COMMANDS' for available commands.");
            }
        }
    }

    private void startFoundationPhase() {
        textTerminal.println("Now that everything is settled. Lets found your settlements!");
        final List<Player> playerTurnOrder = turnOrderHandler.getPlayerTurnOrder();

        foundationPhase(playerTurnOrder, false);

        textTerminal.println("First placement done. Now reverse order to place the last settlement of the foundation phase.");

        foundationPhase(playerTurnOrder, true);

        textTerminal.println("Foundation phase complete! Swapping to normal game now! Good luck!");
        textTerminal.println(settlersBoardTextView.toString());
    }

    private void foundationPhase(final List<Player> playerTurnOrder, boolean reverse) {
        if (reverse) {
            Collections.reverse(playerTurnOrder);
        }
        for (Player player : playerTurnOrder) {
            textTerminal.println("It's " + player.getPlayerFaction() + " turn to build a Settlement and a Road adjacent to it.");
            initialBuild(player);
        }

    }

    private void initialBuild(Player player) {
        int coordinateX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of corner");
        int coordinateY = textIO.newIntInputReader().withMinVal(3).withMaxVal(12).read("Enter y coordinate of corner");
        int endPointX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of the endpoint of the adjacent road");
        int endPointY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter x coordinate of the endpoint of the adjacent road");
        Point coordinates = new Point(coordinateX, coordinateY);
        Point endPoint = new Point(endPointX, endPointY);
        if (settlersBoard.hasCorner(coordinates) && (settlersBoard.getCorner(coordinates) == null) && (settlersBoard.getNeighboursOfCorner(coordinates).isEmpty()) && settlersBoard.getEdge(coordinates, endPoint) == null) {
            Settlement.initialBuild(player, coordinates, settlersBoard);
            Road.initialBuild(player, coordinates, endPoint, settlersBoard);

        } else {
            textTerminal.println("You can not build on the entered coordinates. Please try again");
            initialBuild(player);
        }
    }

    private boolean hasWinner() {
        for (Player player : players) {
            if (player.getWinningPointCounter() == requiredPointsToWin) {
                return true;
            }
        }
        return false;
    }

    private void printIntroduction() {
        textTerminal.println("Welcome to The Settlers!");
    }

    private void setupNewGame() {
        int numberOfPlayers = textIO.newIntInputReader().withMinVal(2).withMaxVal(4).read("Please enter the number of players. 2, 3 or 4 players are supported.");
        addPlayersToGame(numberOfPlayers);
        textTerminal.println(settlersBoardTextView.toString());
        determineTurnOrder();
    }

    private void determineTurnOrder() {
        final List<DiceResult> diceResults = dice.rollForPlayers(players);
        final boolean successFul = turnOrderHandler.determineInitialTurnOrder(diceResults);
        if (successFul) {
            textTerminal.println("Faction " + turnOrderHandler.getCurrentPlayer().getPlayerFaction() + " is the lucky player who is allowed to start.");
        } else {
            textTerminal.println("Several players rolled the same number. Do the whole thing again.");
            determineTurnOrder();
        }
    }

    /**
     * Adds players to the game.
     *
     * @param numberOfPlayers the selected numbers of players
     */
    public void addPlayersToGame(int numberOfPlayers) {
        players = new ArrayList<>(numberOfPlayers);
        for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
            players.add(new Player(getRandomAvailableFaction()));
        }
    }

    /**
     * Returns the {@link Faction}s of the active players.
     *
     * <p>The order of the player's factions in the list must
     * correspond to the order in which they play.
     * Hence, the player that sets the first settlement must be
     * at position 0 in the list etc.
     *
     * <strong>Important note:</strong> The list must contain the
     * factions of active players only.</p>
     *
     * @return the list with player's factions
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the game board.
     *
     * @return the game board
     */
    public SettlersBoard getBoard() {
        return settlersBoard;
    }

    /**
     * This method mainly exists to make sure the pre-existing tests can be executed.
     *
     * @return current player
     */
    public Player getCurrentPlayer() {
        return turnOrderHandler.getCurrentPlayer();
    }

    /**
     * Places a settlement in the founder's phase (phase II) of the game.
     *
     * <p>The placement does not cost any resource cards. If payout is
     * set to true, for each adjacent resource-producing field, a resource card of the
     * type of the resource produced by the field is taken from the bank (if available) and added to
     * the players' stock of resource cards.</p>
     *
     * @param position the position of the settlement
     * @param payout   if true, the player gets one resource card per adjacent resource-producing field
     * @return true, if the placement was successful
     */
    public boolean placeInitialSettlement(Point position, boolean payout) {
        // TODO: Implement
        return false;
    }

    /**
     * Places a road in the founder's phase (phase II) of the game.
     * The placement does not cost any resource cards.
     *
     * @param roadStart position of the start of the road
     * @param roadEnd   position of the end of the road
     * @return true, if the placement was successful
     */
    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
        // TODO: Implement
        return false;
    }

    /**
     * This method takes care of actions depending on the dice throw result.
     * <p>
     * A key action is the payout of the resource cards to the players
     * according to the payout rules of the game. This includes the
     * "negative payout" in case a 7 is thrown and a player has more than
     * resource cards.
     * <p>
     * If a player does not get resource cards, the list for this players'
     * {@link Faction} is <b>an empty list (not null)</b>!.
     *
     * <p>
     * The payout rules of the game take into account factors such as, the number
     * of resource cards currently available in the bank, settlement types
     * (settlement or city), and the number of players that should get resource
     * cards of a certain type (relevant if there are not enough left in the bank).
     * </p>
     *
     * @param dicethrow the resource cards that have been distributed to the players
     * @return the resource cards added to the stock of the different players
     */
    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        // TODO: Implement
        return null;
    }

    /**
     * Builds a settlement at the specified position on the board.
     *
     * <p>The settlement can be built if:
     * <ul>
     * <li> the player possesses the required resource cards</li>
     * <li> a settlement to place on the board</li>
     * <li> the specified position meets the build rules for settlements</li>
     * </ul>
     *
     * @param position the position of the settlement
     * @return true, if the placement was successful
     */
    public boolean buildSettlement(Point position) {
        // TODO: Implement
        return false;
    }

    /**
     * Builds a city at the specified position on the board.
     *
     * <p>The city can be built if:
     * <ul>
     * <li> the player possesses the required resource cards</li>
     * <li> a city to place on the board</li>
     * <li> the specified position meets the build rules for cities</li>
     * </ul>
     *
     * @param position the position of the city
     * @return true, if the placement was successful
     */
    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement
        return false;
    }

    /**
     * Builds a road at the specified position on the board.
     *
     * <p>The road can be built if:
     * <ul>
     * <li> the player possesses the required resource cards</li>
     * <li> a road to place on the board</li>
     * <li> the specified position meets the build rules for roads</li>
     * </ul>
     *
     * @param roadStart the position of the start of the road
     * @param roadEnd   the position of the end of the road
     * @return true, if the placement was successful
     */
    public boolean buildRoad(Point roadStart, Point roadEnd) {
        // TODO: Implement
        return false;
    }


    /**
     * Trades in  resource cards of the
     * offered type for  resource cards of the wanted type.
     * <p>
     * The trade only works when bank and player possess the resource cards
     * for the trade before the trade is executed.
     *
     * @param offer offered type
     * @param want  wanted type
     * @return true, if the trade was successful
     */
    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        // TODO: Implement
        return false;
    }

    /**
     * Returns the winner of the game, if any.
     *
     * @return the winner of the game or null, if there is no winner (yet)
     */
    public Faction getWinner() {
        // TODO: Implement
        return null;
    }


    /**
     * Places the thief on the specified field and steals a random resource card (if
     * the player has such cards) from a random player with a settlement at that
     * field (if there is a settlement) and adds it to the resource cards of the
     * current player.
     *
     * @param field the field on which to place the thief
     * @return false, if the specified field is not a field or the thief cannot be
     * placed there (e.g., on water)
     */
    public boolean placeThiefAndStealCard(Point field) {
        //TODO: Implement (or longest road functionality)
        return false;
    }


    //TODO REMOVE once tests are updated!!
    public TurnOrderHandler getTurnOrderHandler() {
        return turnOrderHandler;
    }
}
