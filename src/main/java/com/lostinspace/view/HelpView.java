package com.lostinspace.view;

import com.lostinspace.model.Game;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Help screen — explains the game mechanics.
 */
public class HelpView extends BaseView {

    public HelpView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Choice");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        ConsoleUI.titleBox(console, "  ❓  HOW TO PLAY — LOST IN SPACE  ❓  ", Ansi.BRIGHT_CYAN);
        console.println();

        printSection("🎯  GOAL",
            "You crashed on Neptune. Navigate 25 planets to reach Earth alive.",
            "Each hop costs fuel. Mine fuel, fight aliens, and solve puzzles along the way.");

        printSection("🚀  NAVIGATION",
            "From the Game Menu, press L to Lift Off.",
            "Pick a destination by number. Each planet shows its fuel cost.",
            "You cannot travel if you don't have enough fuel — mine first!");

        printSection("⛽  FUEL",
            "Fuel is your lifeline. Start at 50 units (max 100).",
            "Mine fuel on planets with a ⛽ marker.",
            "Enter a drill depth (1–10). Higher = more risk, more reward.",
            "Fall below 20% and you'll be stranded!");

        printSection("👾  COMBAT",
            "When a planet has a 👾 marker, you can engage the alien.",
            "Choose: [B]attle, [F]lee, or [T]aunt (throw dirt).",
            "Defeating an alien earns fuel and score.",
            "Equip a stronger weapon in the Weapons menu for more damage.");

        printSection("⚔   WEAPONS",
            "You start with 6 weapons. Equip one from the Weapons menu.",
            "Damage: how hard you hit. Speed Bonus: improves flee success.",
            "The Terminator is the hardest hitter. The Slingshot helps you run.");

        printSection("🔐  PUZZLES",
            "Planets with 🔐 have number-guessing puzzles (1–100).",
            "Solve them to earn bonus fuel. You get a hint after 3 wrong guesses.");

        printSection("💾  SAVING",
            "Press S in the Game Menu or Main Menu to save your game.",
            "Press R in the Main Menu to restore a saved game.");

        console.println();
        ConsoleUI.menuTop(console);
        ConsoleUI.menuItem(console, "Q", "Return to previous menu", Ansi.RED, Ansi.WHITE);
        ConsoleUI.menuBottom(console);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        return true; // any input exits help
    }

    private void printSection(String title, String... lines) {
        console.println();
        console.println("  " + Ansi.BOLD + Ansi.BRIGHT_CYAN + title + Ansi.RESET);
        for (String line : lines) {
            console.println("    " + Ansi.DIM + line + Ansi.RESET);
        }
    }
}
