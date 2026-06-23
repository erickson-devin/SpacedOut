package com.lostinspace.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The player's weapon inventory.
 * Backed by a List&lt;Weapon&gt; — no more "String weapons" field.
 * Tracks which weapon is currently equipped.
 */
public class Inventory implements Serializable {

    private final List<Weapon> weapons = new ArrayList<>();
    private int equippedIndex = 0; // index into weapons list

    public Inventory() {}

    /** Add a weapon to the inventory. */
    public void addWeapon(Weapon w) {
        weapons.add(w);
    }

    /** Returns an unmodifiable view of all weapons. */
    public List<Weapon> getWeapons() {
        return Collections.unmodifiableList(weapons);
    }

    /** Equip the weapon at the given index (0-based). Returns false if invalid. */
    public boolean equip(int index) {
        if (index < 0 || index >= weapons.size()) return false;
        this.equippedIndex = index;
        return true;
    }

    /** Returns the currently equipped weapon, if any. */
    public Optional<Weapon> getEquippedWeapon() {
        if (weapons.isEmpty()) return Optional.empty();
        return Optional.of(weapons.get(equippedIndex));
    }

    /** Equipped weapon name for display ("None" if empty). */
    public String equippedName() {
        return getEquippedWeapon().map(Weapon::name).orElse("None");
    }

    /** Equipped weapon damage (0 if none). */
    public int equippedDamage() {
        return getEquippedWeapon().map(Weapon::damage).orElse(0);
    }

    /** Equipped weapon speed bonus (0 if none). */
    public int equippedSpeedBonus() {
        return getEquippedWeapon().map(Weapon::speedBonus).orElse(0);
    }

    public int size() { return weapons.size(); }
    public boolean isEmpty() { return weapons.isEmpty(); }

    @Override
    public String toString() {
        if (weapons.isEmpty()) return "Inventory[empty]";
        var sb = new StringBuilder("Inventory[\n");
        for (int i = 0; i < weapons.size(); i++) {
            sb.append("  ").append(i == equippedIndex ? "► " : "  ")
              .append(i + 1).append(". ").append(weapons.get(i)).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
