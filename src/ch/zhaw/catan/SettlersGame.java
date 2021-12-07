package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;
import ch.zhaw.catan.commands.CommandHandler;
import ch.zhaw.catan.commands.Commands;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
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
import static ch.zhaw.catan.infrastructure.Road.initialRoadBuild;
import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
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
    private final CommandHandler commandHandler = new CommandHandler(turnOrderHandler, settlersBoard);
    private final int requiredPointsToWin;

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
            Point settlementCoordinates = buildInitialSettlement(player);
            buildInitialRoad(player, settlementCoordinates);
        }

    }

    private Point buildInitialSettlement(Player player) {
        int coordinateX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of corner");
        int coordinateY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of corner");
        Point settlementCoordinates = new Point(coordinateX, coordinateY);
        if (!initialSettlementBuild(player, settlementCoordinates, settlersBoard)) {
            textTerminal.println("You can not build on the entered coordinates. Please try again");
            return buildInitialSettlement(player);
        } else {
            return settlementCoordinates;
        }
    }

    private void buildInitialRoad(Player player, Point startPoint) {
        int endPointX = textIO.newIntInputReader().withMinVal(2).withMaxVal(12).read("Enter x coordinate of the endpoint of the adjacent road");
        int endPointY = textIO.newIntInputReader().withMinVal(3).withMaxVal(19).read("Enter y coordinate of the endpoint of the adjacent road");
        Point endPoint = new Point(endPointX, endPointY);
        if (!initialRoadBuild(player, startPoint, endPoint, settlersBoard)) {
            textTerminal.println("Building the road was not successful! Please try again!");
            buildInitialRoad(player, startPoint);
        }
    }

    private boolean hasWinner() {
        for (Player player : turnOrderHandler.getPlayerTurnOrder()) {
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
        final List<Player> players = createPlayers(numberOfPlayers);
        textTerminal.println(settlersBoardTextView.toString());
        determineTurnOrder(players);
    }

    private void determineTurnOrder(List<Player> players) {
        final List<DiceResult> diceResults = dice.rollForPlayers(players);
        final boolean successFul = turnOrderHandler.determineInitialTurnOrder(diceResults);
        if (successFul) {
            textTerminal.println("Faction " + turnOrderHandler.getCurrentPlayer().getPlayerFaction() + " is the lucky player who is allowed to start.");
        } else {
            textTerminal.println("Several players rolled the same number. Do the whole thing again.");
            determineTurnOrder(players);
        }
    }

    /**
     * Adds players to the game.
     *
     * @param numberOfPlayers the selected numbers of players
     * @return a unordered list of players with the specified number as size
     */
    public List<Player> createPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>(numberOfPlayers);
        for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
            players.add(new Player(getRandomAvailableFaction()));
        }
        return players;
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
}
