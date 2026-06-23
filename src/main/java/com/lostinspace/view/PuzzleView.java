package com.lostinspace.view;

import com.lostinspace.control.MapControl;
import com.lostinspace.control.PuzzleControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Planet;
import com.lostinspace.model.Puzzle;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Puzzle screen — number-guessing with per-planet flavour prompts,
 * guess counter, hint after 3 wrong tries, and fuel reward on success.
 */
public class PuzzleView extends BaseView {

    private Puzzle puzzle;
    private int    guesses;
    private static final int HINT_THRESHOLD = 3;

    public PuzzleView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Your guess (1-100)");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();

        Planet planet = MapControl.currentPlanet(game);
        this.puzzle  = PuzzleControl.generatePuzzle(planet);
        this.guesses = 0;

        console.println(Ansi.BRIGHT_CYAN + AsciiArt.PUZZLE_LOCK + Ansi.RESET);
        ConsoleUI.titleBox(console, "  PUZZLE CHALLENGE  ", Ansi.BRIGHT_CYAN);
        console.println();

        ConsoleUI.typewriter(console, "  " + puzzle.prompt());
        console.println();
        console.println(Ansi.DIM + "  Fuel reward for solving: +" + "%.1f".formatted(puzzle.fuelReward()) + " units" + Ansi.RESET);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            error("Enter a number between 1 and 100.");
            return false;
        }

        guesses++;
        PuzzleControl.PuzzleResult result = PuzzleControl.checkGuess(puzzle, guess);

        switch (result) {
            case PuzzleControl.Correct c -> {
                console.println();
                console.println(Ansi.BRIGHT_GREEN + AsciiArt.CHEST + Ansi.RESET);
                success("Correct! You cracked the code in " + guesses + " guess" + (guesses == 1 ? "" : "es") + "!");
                game.getFuel().add(c.fuelReward());
                console.println(Ansi.BRIGHT_YELLOW + "  ⛽  +" + "%.1f".formatted(c.fuelReward()) + " fuel added to your tank!" + Ansi.RESET);
                game.getPlayer().addScore(c.fuelReward() * 5);
                console.println();
                return true;
            }
            case PuzzleControl.TooHigh h -> warn("Too high! Try lower.");
            case PuzzleControl.TooLow  l -> warn("Too low! Try higher.");
            case PuzzleControl.PuzzleInvalidInput iv -> error(iv.reason());
        }

        if (guesses >= HINT_THRESHOLD) {
            info("Hint: " + puzzle.hint());
        }

        console.println(Ansi.DIM + "  Guesses so far: " + guesses + Ansi.RESET);
        return false;
    }
}
