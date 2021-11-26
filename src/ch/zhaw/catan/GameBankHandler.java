package ch.zhaw.catan;

import ch.zhaw.catan.board.Resource;

import java.util.Map;

public class GameBankHandler {
    // RESOURCE CARD DECK
    public static final Map<Resource, Integer> INITIAL_RESOURCE_CARDS_BANK = Map.of(Resource.LUMBER, 19,
            Resource.BRICK, 19, Resource.WOOL, 19, Resource.GRAIN, 19, Resource.ORE, 19);
    private static final int FOUR_TO_ONE_TRADE_OFFER = 4;
    private static final int FOUR_TO_ONE_TRADE_WANT = 1;
}
