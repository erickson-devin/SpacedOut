package com.lostinspace.view;

import com.lostinspace.model.Game;
import com.lostinspace.model.Player;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Base class for all view screens.
 * Holds shared I/O handles and the game context.
 * Implements the display loop: prompt → getInput → doAction → repeat until done.
 */
public abstract class BaseView {

    protected final BufferedReader keyboard;
    protected final PrintWriter    console;
    protected final Game           game;
    protected       String         prompt; // the text shown before every input request

    protected BaseView(BufferedReader keyboard, PrintWriter console, Game game, String prompt) {
        this.keyboard = keyboard;
        this.console  = console;
        this.game     = game;
        this.prompt   = prompt;
    }

    /**
     * Main display loop — shows the screen and processes input until doAction() returns true
     * or the user types Q.
     */
    public void display() {
        render();
        boolean done = false;
        do {
            String input = readLine();
            if (input == null) break;
            if (input.equalsIgnoreCase("Q")) break;
            done = doAction(input);
        } while (!done);
    }

    /** Render the full screen (called once before the input loop begins). */
    protected abstract void render();

    /**
     * Handle a user input. Return true when this view is "done" and should stop looping.
     * Return false to keep prompting (e.g. invalid input, or a menu that loops).
     */
    protected abstract boolean doAction(String input);

    // ─── I/O helpers ─────────────────────────────────────────────────────────

    /**
     * Read a non-empty line of input, re-prompting until the user enters something.
     * Returns null on EOF or error.
     */
    protected String readLine() {
        while (true) {
            console.print(Ansi.BRIGHT_YELLOW + "  ▶ " + Ansi.RESET + prompt + " ");
            console.flush();
            try {
                String line = keyboard.readLine();
                if (line == null) return null;
                line = line.trim();
                if (!line.isEmpty()) return line;
                console.println(Ansi.RED + "  Please enter a value." + Ansi.RESET);
            } catch (IOException e) {
                console.println(Ansi.RED + "  Error reading input: " + e.getMessage() + Ansi.RESET);
                return null;
            }
        }
    }

    /** Read a line without re-prompting on empty (used for confirmation). */
    protected String readLineOnce() {
        try {
            String line = keyboard.readLine();
            return line == null ? "" : line.trim();
        } catch (IOException e) {
            return "";
        }
    }

    /** Print a styled error message. */
    protected void error(String msg) {
        console.println(Ansi.RED + "  ✘  " + msg + Ansi.RESET);
    }

    /** Print a styled success message. */
    protected void success(String msg) {
        console.println(Ansi.GREEN + "  ✔  " + msg + Ansi.RESET);
    }

    /** Print a styled info message. */
    protected void info(String msg) {
        console.println(Ansi.CYAN + "  ℹ  " + msg + Ansi.RESET);
    }

    /** Print a warning message. */
    protected void warn(String msg) {
        console.println(Ansi.BRIGHT_YELLOW + "  ⚠  " + msg + Ansi.RESET);
    }

    /** Render the persistent status bar using current game state. */
    protected void statusBar() {
        if (game == null || game.getPlayer() == null) return;
        Player p = game.getPlayer();
        String planet = "Unknown";
        if (game.getMap() != null) {
            planet = game.getMap().getPlanet(p.getCurrentPlanetIndex())
                         .map(pl -> pl.name()).orElse("Unknown");
        }
        ConsoleUI.statusBar(
            console,
            p.getName(),
            p.getHp(), p.getMaxHp(),
            game.getFuel() != null ? game.getFuel().getCurrent() : 0,
            game.getFuel() != null ? game.getFuel().getCapacity() : 100,
            planet,
            p.getInventory().equippedName()
        );
    }
}
