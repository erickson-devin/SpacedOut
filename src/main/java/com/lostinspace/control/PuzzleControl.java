package com.lostinspace.control;

import com.lostinspace.model.Planet;
import com.lostinspace.model.Puzzle;
import java.util.Random;

/**
 * Handles puzzle generation and solution checking.
 * Fixes: guess = (1 + solution - 1) no-op bug is gone.
 * Puzzles are now generated per-planet with thematic prompts.
 */
public final class PuzzleControl {

    private PuzzleControl() {}

    private static final Random RNG = new Random();

    public sealed interface PuzzleResult
        permits PuzzleControl.Correct,
                PuzzleControl.TooHigh,
                PuzzleControl.TooLow,
                PuzzleControl.PuzzleInvalidInput {}

    public record Correct(double fuelReward)  implements PuzzleResult {}
    public record TooHigh()                    implements PuzzleResult {}
    public record TooLow()                     implements PuzzleResult {}
    public record PuzzleInvalidInput(String reason) implements PuzzleResult {}

    /** Generate a random puzzle appropriate for the given planet. */
    public static Puzzle generatePuzzle(Planet planet) {
        int answer = RNG.nextInt(100) + 1;
        String prompt = choosePuzzlePrompt(planet.name());
        String hint   = "Think of a number... halfway there might help.";
        double reward = planet.depth() / 5.0;
        return new Puzzle(prompt, hint, answer, Math.max(2.0, reward));
    }

    private static String choosePuzzlePrompt(String planet) {
        return switch (planet) {
            case "Uranus"    -> "The navigation computer is locked. Crack the access code (1-100):";
            case "Saturn"    -> "The ring-sensor data is corrupted. Input the correct frequency (1-100):";
            case "Zarthon"   -> "The crystal matrix resonates at a specific harmonic. Find it (1-100):";
            case "Orblook"   -> "This perfectly round planet hides a perfectly round number. Guess it (1-100):";
            case "Andromida" -> "An ancient intergalactic cipher protects the fuel cache. Decode it (1-100):";
            case "Blowhard"  -> "The wind station broadcasts a single encrypted number. Receive it (1-100):";
            default          -> "The alien datapad is locked. Guess the unlock code (1-100):";
        };
    }

    /**
     * Check a player's guess against the puzzle.
     */
    public static PuzzleResult checkGuess(Puzzle puzzle, int guess) {
        if (guess < 1 || guess > 100) {
            return new PuzzleInvalidInput("Please enter a number between 1 and 100.");
        }
        if (guess == puzzle.answer()) {
            return new Correct(puzzle.fuelReward());
        } else if (guess > puzzle.answer()) {
            return new TooHigh();
        } else {
            return new TooLow();
        }
    }
}
