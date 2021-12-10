package ch.zhaw.catan;

import ch.zhaw.catan.board.Land;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;
import ch.zhaw.catan.commands.CommandHandler;
import ch.zhaw.catan.commands.Commands;
import ch.zhaw.catan.game.logic.Dice;
import ch.zhaw.catan.game.logic.DiceResult;
import ch.zhaw.catan.game.logic.RollDice;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.AbstractInfrastructure;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ch.zhaw.catan.commands.Commands.SHOW_COMMANDS;
import static ch.zhaw.catan.commands.Commands.getCommandByRepresentation;
import static ch.zhaw.catan.infrastructure.Road.initialRoadBuild;
import static ch.zhaw.catan.infrastructure.Settlement.initialSettlementBuild;
import static ch.zhaw.catan.io.CommandLineHandler.*;
import static ch.zhaw.catan.player.FactionsUtil.getRandomAvailableFaction;

/**
 * This class performs all actions related to modifying the game state.
 *
 * @author abuechi, fupat002, weberph5
 * @version 1.0.0
 */
public class SettlersGame {
    private static final int MINIMUM_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final Dice dice = new Dice();
    private final TurnOrderHandler turnOrderHandler = new TurnOrderHandler();
    private final SettlersBoard settlersBoard = new SettlersBoard();
    private final SettlersBoardTextView settlersBoardTextView = new SettlersBoardTextView(settlersBoard);
    private final RollDice rollDice = new RollDice(settlersBoard, turnOrderHandler);
    private final CommandHandler commandHandler = new CommandHandler();
    private final int requiredPointsToWin;

    /**
     * Constructs a SettlersGame game state object.
     *
     * @param winPoints the number of points required to win the game
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
        Player previousPlayer = turnOrderHandler.getCurrentPlayer();
        while (!hasWinner()) {
            printMessage("It's " + previousPlayer.getPlayerFaction() + " turn.");
            if (!turnOrderHandler.currentPlayerDidNotChange(previousPlayer)) {
                final Player currentPlayer = turnOrderHandler.getCurrentPlayer();
                final int playerIndex = turnOrderHandler.getPlayerTurnOrder().indexOf(previousPlayer);
                previousPlayer = turnOrderHandler.getPlayerTurnOrder().get(playerIndex);
                printMessage("It's " + currentPlayer.getPlayerFaction() + " turn.");
                rollDice.rollDice();
            }
            printMessage("Choose your actions down below:");
            printMessage("If you are done with your turn, enter END TURN command.");
            commandHandler.executeCommand(SHOW_COMMANDS, turnOrderHandler, settlersBoard);
            playerActionPhase();
        }
    }

    private void playerActionPhase() {
        final String userInput = promptNextUserAction();
        final Commands commandByRepresentation = getCommandByRepresentation(userInput);
        if (commandByRepresentation != null) {
            commandHandler.executeCommand(commandByRepresentation, turnOrderHandler, settlersBoard);
        } else {
            printMessage("This command is not available. Use 'SHOW COMMANDS' for available commands.");
        }
    }

    private void startFoundationPhase() {
        printMessage("Now that everything is settled. Lets found your settlements!");
        final List<Player> playerTurnOrder = turnOrderHandler.getPlayerTurnOrder();
        foundationPhase(playerTurnOrder, false);

        printMessage("First placement done. Now reverse order to place the last settlement of the foundation phase.");

        foundationPhase(playerTurnOrder, true);

        printMessage("Foundation phase complete! Swapping to normal game now! Good luck!");
        handoutResourcesAfterFoundationPhase();
        settlersBoardTextView.printBoard();
    }

    private void handoutResourcesAfterFoundationPhase() {
        for (AbstractInfrastructure infrastructure : settlersBoard.getNonNullCorners()) {
            if (infrastructure instanceof Settlement) {
                for (Land land : settlersBoard.getFieldsOfCorner(infrastructure.getPosition())) {
                    if (!land.equals(Land.WATER)) {
                        infrastructure.getOwner().addResourceCardToHand(land.getResource());
                    }
                }
            }
        }
    }

    private void foundationPhase(final List<Player> playerTurnOrder, boolean reverse) {
        if (reverse) {
            Collections.reverse(playerTurnOrder);
        }
        for (Player player : playerTurnOrder) {
            printMessage("It's " + player.getPlayerFaction() + " turn to build a Settlement and a Road adjacent to it.");
            Point settlementCoordinates = buildInitialSettlement(player);
            buildInitialRoad(player, settlementCoordinates);
        }
    }

    private Point buildInitialSettlement(Player player) {
        Point settlementCoordinates = promptCoordinates("Corner");
        if (!initialSettlementBuild(player, settlementCoordinates, settlersBoard)) {
            printMessage("You can not build on the entered coordinates. Please try again.");
            return buildInitialSettlement(player);
        } else {
            return settlementCoordinates;
        }
    }

    private void buildInitialRoad(Player player, Point startPoint) {
        Point endPoint = promptCoordinates("Endpoint of the adjacent road");
        if (!initialRoadBuild(player, startPoint, endPoint, settlersBoard)) {
            printMessage("Building the road was not successful! Please try again!");
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
        printMessage("Welcome to The Settlers!");
    }

    private void setupNewGame() {
        int numberOfPlayers = promptMinMaxValueInt("Please enter the number of players. 2, 3 or 4 players are supported.", MINIMUM_PLAYERS, MAX_PLAYERS);
        final List<Player> players = createPlayers(numberOfPlayers);
        settlersBoardTextView.printBoard();
        determineTurnOrder(players);
    }

    private void determineTurnOrder(List<Player> players) {
        final List<DiceResult> diceResults = dice.rollForPlayers(players);
        final boolean successFul = turnOrderHandler.determineInitialTurnOrder(diceResults);
        if (successFul) {
            printMessage("Faction " + turnOrderHandler.getCurrentPlayer().getPlayerFaction() + " is the lucky player who is allowed to start.");
        } else {
            printMessage("Several players rolled the same number. Do the whole thing again.");
            determineTurnOrder(players);
        }
    }

    /**
     * Adds players to the game.
     *
     * @param numberOfPlayers the selected numbers of players
     * @return an unordered list of players with the specified number as size
     */
    private List<Player> createPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>(numberOfPlayers);
        for (int playerNumber = 1; playerNumber <= numberOfPlayers; playerNumber++) {
            players.add(new Player(getRandomAvailableFaction()));
        }
        return players;
    }
}
