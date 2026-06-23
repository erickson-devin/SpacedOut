package com.lostinspace.control;

import com.lostinspace.model.*;
import java.util.Random;

/**
 * Handles all combat logic: attacks, flee attempts.
 * Returns sealed CombatResult types — no exceptions for game flow.
 *
 * Combat model:
 *   - Each round, a random roll determines hit quality (weak/medium/strong/miss)
 *   - Critical hits (5% chance) deal 2× damage
 *   - The alien always counter-attacks on the player's turn unless the alien dies first
 *   - Flee success = (playerSpeed + weaponSpeedBonus) - alienSpeed > random(-5..5)
 */
public final class ActorControl {

    private ActorControl() {}

    private static final Random RNG = new Random();

    /**
     * Player attacks an alien.
     *
     * @param player      The player (determines weapon damage via inventory)
     * @param alien       The alien being attacked
     * @param alienHp     Current HP of the alien (tracked by caller)
     * @return A CombatResult indicating what happened
     */
    public static CombatResult attackAlien(Player player, Alien alien, int alienHp) {
        int weaponDamage = player.getInventory().equippedDamage();

        // Roll hit quality (0–99)
        int roll = RNG.nextInt(100);

        // Critical hit (5% chance)
        boolean critical = (roll < 5);
        int multiplier   = critical ? 2 : 1;

        // Hit tier based on alien's attack values (used as difficulty bands)
        int damage;
        if (roll < 15) {
            // Miss — alien retaliates
            int alienDmg = alien.weakAttack() + RNG.nextInt(3);
            boolean died = player.takeDamage(alienDmg);
            return died
                ? new CombatResult.PlayerDefeated()
                : new CombatResult.AlienHit(alienDmg, player.getHp());
        } else if (roll < 45) {
            damage = Math.max(1, (weaponDamage / 3) * multiplier);
        } else if (roll < 80) {
            damage = Math.max(1, (weaponDamage * 2 / 3) * multiplier);
        } else {
            damage = weaponDamage * multiplier;
        }

        int newAlienHp = alienHp - damage;

        if (newAlienHp <= 0) {
            player.addScore(alien.fuelReward() * 10);
            return new CombatResult.AlienDefeated(alien.fuelReward(), alien.fuelReward() * 10);
        }

        // Alien counter-attacks after player hit
        int alienDmg   = alien.weakAttack() + RNG.nextInt(alien.medAttack() - alien.weakAttack() + 1);
        boolean died   = player.takeDamage(alienDmg);

        if (died) return new CombatResult.PlayerDefeated();

        // Return the player hit first (caller will chain to AlienHit for display)
        return new CombatResult.PlayerHit(damage, newAlienHp, critical);
    }

    /**
     * Player attempts to flee from combat.
     *
     * @param player  The player (speed + weapon speed bonus)
     * @param alien   The alien (speed stat)
     * @return Fled, or CaughtFleeing with damage
     */
    public static CombatResult fleeAlien(Player player, Alien alien) {
        int playerSpeed  = player.getSpeed() + player.getInventory().equippedSpeedBonus();
        int alienSpeed   = alien.speed();
        int roll         = RNG.nextInt(11) - 5; // -5 to +5

        boolean escaped = (playerSpeed + roll) > alienSpeed;

        if (escaped) {
            return new CombatResult.Fled();
        } else {
            // Caught — alien lands a hit
            int damage = alien.medAttack() + RNG.nextInt(4);
            boolean died = player.takeDamage(damage);
            return died
                ? new CombatResult.PlayerDefeated()
                : new CombatResult.CaughtFleeing(damage, player.getHp());
        }
    }

    /** Alien counter-attack (called after a player hit connects). */
    public static CombatResult.AlienHit alienCounterAttack(Player player, Alien alien) {
        int damage = alien.weakAttack() + RNG.nextInt(
                         Math.max(1, alien.medAttack() - alien.weakAttack()));
        player.takeDamage(damage);
        return new CombatResult.AlienHit(damage, player.getHp());
    }
}
