package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;
import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.game.logic.Thief;
import ch.zhaw.catan.infrastructure.AbstractInfrastructure;
import ch.zhaw.catan.infrastructure.City;
import ch.zhaw.catan.infrastructure.Settlement;
import ch.zhaw.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ch.zhaw.catan.io.CommandLineHandler.printMessage;

/**
 * Handles trading and handing out resources
 */
public class GameBankHandler {
    private static final int FOUR_TO_ONE_TRADE_OFFER = 4;
    private static final int FOUR_TO_ONE_TRADE_WANT = 1;

    /**
     * Trades in  resource cards of the
     * offered type for  resource cards of the wanted type.
     * <p>
     * The trade only works when bank and player possess the resource cards
     * for the trade before the trade is executed.
     *
     * @param offer  offered type
     * @param want   wanted type
     * @param player the player who wants to trade
     * @author abuechi
     */
    public void tradeWithBankFourToOne(Resource offer, Resource want, final Player player) {
        if (player.getResourceCardCountFor(offer) >= FOUR_TO_ONE_TRADE_OFFER) {
            player.removeResourceCardFromHand(offer, FOUR_TO_ONE_TRADE_OFFER);
            player.addResourceCardToHand(want, FOUR_TO_ONE_TRADE_WANT);
        }
    }

    /**
     * A method that hands out resources based on a value rolled by a player
     *
     * @param diceValue the rolled value by the current player
     * @param board     the current game board instance
     * @author fupat002
     */
    public void handoutResourcesOfTheRolledField(int diceValue, final SettlersBoard board) {
        final List<Point> allFieldsWithDiceValue = board.getFieldsByDiceValue(diceValue);
        for (Point field : allFieldsWithDiceValue) {
            final Thief thief = board.getThief();
            if (!thief.isThiefOnField(field)) {
                final Resource resourceOfRolledField = board.getResourceOfField(field);
                final ArrayList<Point> occupiedCornersOfField = board.getOccupiedCornerCoordinatesOfField(field);
                for (Point occupiedCorner : occupiedCornersOfField) {
                    AbstractInfrastructure buildingOnCorner = board.getBuildingOnCorner(occupiedCorner);
                    handOutResourceToBuildingOwner(buildingOnCorner, resourceOfRolledField);
                }
            } else {
                thief.printInfoOfFieldOccupiedByThief();
            }
        }
    }

    private void handOutResourceToBuildingOwner(final AbstractInfrastructure buildingOnCorner, final Resource resourceOfRolledField) {
        final Player owner = buildingOnCorner.getOwner();
        if (buildingOnCorner instanceof Settlement) {
            owner.addResourceCardToHand(resourceOfRolledField);
        } else if (buildingOnCorner instanceof City) {
            owner.addResourceCardToHand(resourceOfRolledField, 2);
        } else {
            printMessage("Something went wrong. Corner has invalid placement");
        }
        printMessage(owner.getPlayerFaction() + " has a settlement on on this field.");
        printMessage("This resource: " + resourceOfRolledField + " got added to your hand.");
    }
}
