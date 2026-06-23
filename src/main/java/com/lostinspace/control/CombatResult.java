package com.lostinspace.control;

/**
 * Sealed hierarchy representing every possible outcome of a combat action.
 * Using sealed + pattern-matching switch eliminates exception-as-control-flow.
 */
public sealed interface CombatResult
    permits CombatResult.PlayerHit,
            CombatResult.AlienHit,
            CombatResult.AlienDefeated,
            CombatResult.PlayerDefeated,
            CombatResult.Fled,
            CombatResult.CaughtFleeing,
            CombatResult.Miss {

    /** Player landed a hit on the alien. alienHpRemaining = new HP. */
    record PlayerHit(int damage, int alienHpRemaining, boolean critical) implements CombatResult {}

    /** Alien counter-attacked the player. playerHpRemaining = new HP. */
    record AlienHit(int damage, int playerHpRemaining) implements CombatResult {}

    /** Alien HP reached zero — player wins this fight. */
    record AlienDefeated(double fuelReward, double scoreGain) implements CombatResult {}

    /** Player HP reached zero — game over. */
    record PlayerDefeated() implements CombatResult {}

    /** Player successfully fled the combat. */
    record Fled() implements CombatResult {}

    /** Player tried to flee but failed — alien retaliates. */
    record CaughtFleeing(int damage, int playerHpRemaining) implements CombatResult {}

    /** Complete miss — no damage on either side. */
    record Miss(String description) implements CombatResult {}
}
