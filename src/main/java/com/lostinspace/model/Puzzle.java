package com.lostinspace.model;

import java.io.Serializable;

/**
 * A number-guessing puzzle that blocks progress until solved.
 *
 * @param prompt       Question/flavour text shown to the player
 * @param hint         Hint displayed after 3 wrong guesses
 * @param answer       The correct integer answer (1–100)
 * @param fuelReward   Fuel rewarded for solving
 */
public record Puzzle(
    String prompt,
    String hint,
    int    answer,
    double fuelReward
) implements Serializable {

    @Override
    public String toString() {
        return "Puzzle[answer=%d, reward=%.1f fuel]".formatted(answer, fuelReward);
    }
}
