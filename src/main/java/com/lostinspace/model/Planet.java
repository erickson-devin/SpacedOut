package com.lostinspace.model;

import com.lostinspace.util.AsciiArt;
import java.io.Serializable;

/**
 * Represents a destination in the game — a planet, moon, asteroid, or anomaly.
 * Immutable by design (record). Mutability is handled at the Game/Map level.
 *
 * @param index          Position index in the Map (0 = starting planet, 24 = Earth)
 * @param name           Display name
 * @param description    Flavour text shown on arrival
 * @param depth          How deep fuel can be mined (affects fuel yield ceiling)
 * @param surfaceDensity How hard it is to mine (higher = harder)
 * @param hasAlien       Whether an alien may spawn here
 * @param hasFuelDeposit Whether the player can mine fuel here
 * @param hasPuzzle      Whether a puzzle is available here
 * @param travelCost     Fuel units consumed to travel here from adjacent planet
 */
public record Planet(
    int    index,
    String name,
    String description,
    int    depth,
    int    surfaceDensity,
    boolean hasAlien,
    boolean hasFuelDeposit,
    boolean hasPuzzle,
    double  travelCost
) implements Serializable {

    /** Returns the ASCII art for this planet. */
    public String asciiArt() {
        return AsciiArt.forPlanet(name);
    }

    /** Short display label: "Earth (index 24)". */
    @Override
    public String toString() {
        return "%s [#%d]".formatted(name, index);
    }

    /** Compact info line for map display. */
    public String toMapLine() {
        return "%-16s  depth:%-4d  density:%-4d  fuel:%-3s  alien:%-3s  puzzle:%s"
            .formatted(name, depth, surfaceDensity,
                       hasFuelDeposit ? "YES" : "no",
                       hasAlien       ? "YES" : "no",
                       hasPuzzle      ? "YES" : "no");
    }
}
