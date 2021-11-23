package ch.zhaw.catan.player;

import ch.zhaw.catan.board.Resource;

public class OccupiedResourceField {
    private final Resource resource;
    private final int diceValue;
    private boolean occupiedWithSettlement;
    private boolean occupiedWithCity;

    public OccupiedResourceField(Resource resource, int diceValue) {
        this.resource = resource;
        this.diceValue = diceValue;
        this.occupiedWithSettlement = true;
        this.occupiedWithCity = false;
    }

    /**
     * A player can upgrade a settlement to a City.
     */
    public void upgradeToCity(){
        occupiedWithSettlement = false;
        occupiedWithCity = true;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean isOccupiedWithCity() {
        return occupiedWithCity;
    }
}
