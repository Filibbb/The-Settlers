package ch.zhaw.catan.commands;

import ch.zhaw.catan.GameBankHandler;
import ch.zhaw.catan.player.Player;

import static ch.zhaw.catan.board.Resource.getResourceByRepresentation;
import static ch.zhaw.catan.board.Resource.isValidResource;
import static ch.zhaw.catan.io.CommandLineHandler.printMessage;
import static ch.zhaw.catan.io.CommandLineHandler.promptNextUserInput;

/**
 * Command for trading resources with bank
 */
public class TradeWithBankCommand implements Command {
    private final GameBankHandler gameBankHandler = new GameBankHandler();
    private final Player currentPlayer;

    /**
     * Inits TradeWithBank Command
     *
     * @param currentPlayer the player whose turn it is currently
     */
    public TradeWithBankCommand(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        final String offeredResource = promptNextUserInput("Enter the resource you offer (Abv.):");
        final String requestedResource = promptNextUserInput("Enter the resource you wish to receive (Abv.):");
        if (isValidResource(offeredResource) && isValidResource(requestedResource)) {
            gameBankHandler.tradeWithBankFourToOne(getResourceByRepresentation(offeredResource), getResourceByRepresentation(requestedResource), currentPlayer);
        } else {
            printMessage("The resources you entered were not found amongst the resources available in-game. Make sure you entered the abbreviation.");
        }
    }
}
