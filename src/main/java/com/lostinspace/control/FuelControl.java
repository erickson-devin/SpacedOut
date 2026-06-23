package com.lostinspace.control;

import com.lostinspace.model.Fuel;
import com.lostinspace.model.Planet;
import java.util.Random;

/**
 * Handles fuel mining mechanics.
 * Fixes: parameters are now ACTUALLY used (no more hardcoded overrides).
 *
 * Mining formula:
 *   yield = playerInput + random(1..10) - surfaceDensity/5
 *   capped between 0 and planet depth
 *   minimum 1 unit on success
 */
public final class FuelControl {

    private FuelControl() {}

    private static final Random RNG = new Random();

    public sealed interface MineResult
        permits FuelControl.Success,
                FuelControl.ExceededDepth,
                FuelControl.InvalidInput {}

    /** Mining succeeded — amount is the fuel units collected. */
    public record Success(double amount)         implements MineResult {}
    /** Mining drill went too deep — no fuel collected. */
    public record ExceededDepth(int depth)       implements MineResult {}
    /** User entered an out-of-range input. */
    public record InvalidInput(String reason)    implements MineResult {}

    /**
     * Attempt to mine fuel from the current planet.
     *
     * @param planet    The current planet (provides depth + surfaceDensity)
     * @param fuel      The ship's current fuel supply (will be updated on success)
     * @param userInput Player's drill depth guess (must be 1-10)
     * @return A sealed MineResult indicating what happened
     */

    public static MineResult mine(Planet planet, Fuel fuel, int userInput) {
        if (userInput < 1 || userInput > 10) {
            return new InvalidInput("Please enter a number between 1 and 10.");
        }

        if (!planet.hasFuelDeposit()) {
            return new InvalidInput("There is no mineable fuel on " + planet.name() + ".");
        }

        int random           = RNG.nextInt(10) + 1;
        double densityFactor = planet.surfaceDensity() / 5.0;
        double rawYield      = userInput + random - densityFactor;

        if (rawYield > planet.depth()) {
            return new ExceededDepth(planet.depth());
        }

        double yield = Math.max(1.0, rawYield);
        double added = fuel.add(yield);
        return new Success(added);
    }

    /**
     * Check if fuel is sufficient for travel.
     * Returns the percentage, or -1 if below minimum threshold.
     */
    public static double getFuelPercent(Fuel fuel) {
        return fuel.getPercent();
    }
}
