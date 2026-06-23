package com.lostinspace.model;

import java.io.Serializable;

/**
 * A weapon the player can equip and use in combat.
 * Immutable record.
 *
 * @param name        Display name
 * @param damage      Base damage per attack
 * @param speedBonus  Bonus added to player speed during flee checks
 * @param rarity      Flavour rarity (Common / Rare / Legendary)
 * @param description Short description shown in inventory
 */
public record Weapon(
    String name,
    int    damage,
    int    speedBonus,
    String rarity,
    String description
) implements Serializable {

    @Override
    public String toString() {
        return "%-18s  dmg:%-4d  spd:+%-3d  [%s]"
            .formatted(name, damage, speedBonus, rarity);
    }

    /** One-liner for status bar display. */
    public String shortName() {
        return name;
    }
}
