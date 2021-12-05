package ch.zhaw.catan.commands;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.TurnOrderHandler;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all Commands.
 */
public class CommandHandler {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private final TurnOrderHandler turnOrderHandler;
    private final SettlersBoard settlersBoard;

    public CommandHandler(TurnOrderHandler turnOrderHandler, SettlersBoard settlersBoard) {
        this.turnOrderHandler = turnOrderHandler;
        this.settlersBoard = settlersBoard;
    }

    public void executeCommand(Commands command) {
        switch (command) {
            case BUILD_SETTLEMENT:
                //TODO: Implement
                break;
            case ROLL_DICE:
                RollDiceCommand rollDiceCommand = new RollDiceCommand(settlersBoard, turnOrderHandler);
                rollDiceCommand.execute();
                break;
            case END_TURN:
                final EndTurnCommand endTurnCommand = new EndTurnCommand(turnOrderHandler);
                endTurnCommand.execute();
            case SHOW_COMMANDS:
                final ShowCommand showCommand = new ShowCommand();
                showCommand.execute();
                break;
            default:
                textTerminal.println("This command is not available. Use 'SHOW COMMANDS' for available commands.");
                break;
        }
    }
}
