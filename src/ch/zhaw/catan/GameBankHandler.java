package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;

import java.util.Map;

public class GameBankHandler {
    // RESOURCE CARD DECK
    public static final Map<Resource, Integer> INITIAL_RESOURCE_CARDS_BANK = Map.of(Resource.LUMBER, 19,
            Resource.BRICK, 19, Resource.WOOL, 19, Resource.GRAIN, 19, Resource.ORE, 19);
    private static final int FOUR_TO_ONE_TRADE_OFFER = 4;
    private static final int FOUR_TO_ONE_TRADE_WANT = 1;

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
}
