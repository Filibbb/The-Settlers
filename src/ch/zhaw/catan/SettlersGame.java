package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.board.SettlersBoardTextView;
import ch.zhaw.catan.board.Structure;
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
    private int requiredPointsToWin;
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
        textTerminal.println("Now that everything is settled. Lets found your settlements!.");
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
            textTerminal.println("It's " + player.getPlayerFaction() + " turn.");
            //TODO build for free a settlement based on commands at the desired position @weberph5

            if (!reverse) {
                player.increaseBuiltStructures(Structure.ROAD);
            }

            player.increaseBuiltStructures(Structure.SETTLEMENT);
            player.incrementWinningPoints();
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
     * Returns the game board.
     *
     * @return the game board
     */
    public SettlersBoard getBoard() {
        return settlersBoard;
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
