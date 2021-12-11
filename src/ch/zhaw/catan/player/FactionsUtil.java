package ch.zhaw.catan.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A utility class for dealing with factions.
 *
 * @author abuechi
 * @version 1.0.0
 */
public class FactionsUtil {
    private static final List<Faction> ALL_AVAILABLE_FACTIONS = new ArrayList<>(Arrays.asList(Faction.values()));

    private FactionsUtil() {
    }

    /**
     * Retrieves a pseudo-random faction based on all available factions.
     *
     * @return one of the factions that are available.
     * @author abuechi
     */
    public static Faction getRandomAvailableFaction() {
        final Random randomizer = new Random();
        final int randomFactionIndex = randomizer.nextInt(ALL_AVAILABLE_FACTIONS.size());
        final Faction selectedFaction = ALL_AVAILABLE_FACTIONS.get(randomFactionIndex);
        ALL_AVAILABLE_FACTIONS.remove(selectedFaction);
        return selectedFaction;
    }
}
