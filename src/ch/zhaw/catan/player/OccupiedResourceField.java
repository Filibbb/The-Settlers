package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;

public class OccupiedResourceField {
    private final Resource resource;
    private final int diceValue;
    private boolean settlement;
    private boolean city;

    public OccupiedResourceField(Resource resource, int diceValue) {
        this.resource = resource;
        this.diceValue = diceValue;
        this.settlement = true;
        this.city = false;
    }

    /**
     * A player can upgrade a settlement to a
     */
    public void upgradeToCity(){
        settlement = false;
        city = true;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public Resource getResource() {
        return resource;
    }
}
