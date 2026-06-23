package com.lostinspace.model;

import com.lostinspace.util.AsciiArt;
import java.io.Serializable;

/**
 * An enemy alien the player may encounter on a planet.
 * Immutable record. Each Alien instance represents a type/species
 * (actual HP in combat is tracked separately in CombatSession).
 *
 * @param name        Display name of the alien species
 * @param type        Art type key (grunt, commander, shadow, crystal, void)
 * @param description Flavour text shown on encounter
 * @param maxHp       Starting HP for combat
 * @param weakAttack  Damage dealt on a weak hit
 * @param medAttack   Damage dealt on a medium hit
 * @param strongAttack Damage dealt on a strong hit
 * @param speed       Used in flee calculations; higher = harder to escape
 * @param fuelReward  Fuel units rewarded on defeat
 */
public record Alien(
    String name,
    String type,
    String description,
    int    maxHp,
    int    weakAttack,
    int    medAttack,
    int    strongAttack,
    int    speed,
    double fuelReward
) implements Serializable {

    /** Returns the ASCII art for this alien's type. */
    public String asciiArt() {
        return AsciiArt.forAlien(type);
    }

    @Override
    public String toString() {
        return "%s (HP:%d  ATK:%d/%d/%d  SPD:%d)"
            .formatted(name, maxHp, weakAttack, medAttack, strongAttack, speed);
    }
}
