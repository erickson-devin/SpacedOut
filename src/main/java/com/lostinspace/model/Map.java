package com.lostinspace.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The star map — an ordered list of 25 planets/locations.
 * The player starts at index 0 (Neptune) and must reach index 24 (Earth).
 * Travel costs fuel; the game ends when the player reaches Earth.
 */
public class Map implements Serializable {

    private final List<Planet> planets;

    public Map(List<Planet> planets) {
        this.planets = List.copyOf(planets); // immutable defensive copy
    }

    /** All planets in travel order. */
    public List<Planet> getPlanets() {
        return planets;
    }

    /** Retrieve a planet by its map index. Returns empty if out of range. */
    public Optional<Planet> getPlanet(int index) {
        if (index < 0 || index >= planets.size()) return Optional.empty();
        return Optional.of(planets.get(index));
    }

    /** Number of destinations on the map. */
    public int size() { return planets.size(); }

    /** Index of Earth (the final destination). */
    public int earthIndex() { return planets.size() - 1; }

    @Override
    public String toString() {
        return "Map[%d planets]".formatted(planets.size());
    }
}
