package com.lostinspace.view;

import com.lostinspace.control.GameControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Player;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * The very first screen the player sees.
 * Shows the title banner, story intro, and asks for the player's name.
 */
public class StartProgramView extends BaseView {

    public StartProgramView(BufferedReader keyboard, PrintWriter console) {
        super(keyboard, console, null, "Enter your name");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);

        // Animated title (fast typewriter)
        console.println(Ansi.BRIGHT_CYAN + AsciiArt.TITLE + Ansi.RESET);

        ConsoleUI.divider(console);
        console.println();

        // Story intro — typewriter effect
        String[] lines = {
            "You awaken to find yourself on an unfamiliar and barren planet.",
            "Intense winds howl. Fourteen distant moons drift overhead.",
            "Your spaceship has crashed — all communication with Earth is lost.",
            "",
            "Next to the wreckage you spot a shiny metal box.",
            "Inside: a star map. Your first clue. Your only hope.",
            "",
            "You are LOST IN SPACE.",
            "",
            "Navigate through 25 destinations, mine fuel, fight alien creatures,",
            "and solve the puzzles that stand between you and home.",
            "",
            Ansi.BRIGHT_YELLOW + "Your mission: return to Earth. Alive." + Ansi.RESET,
        };

        for (String line : lines) {
            if (line.isEmpty()) {
                console.println();
            } else {
                ConsoleUI.typewriter(console, "  " + line);
            }
        }

        console.println();
        ConsoleUI.divider(console);
        console.println();
    }

    @Override
    protected boolean doAction(String name) {
        if (name.length() < 2) {
            error("Name must be at least 2 characters long.");
            return false;
        }

        // Create the new game
        Game game = GameControl.createNewGame(name);

        console.println();
        ConsoleUI.titleBox(console,
            "Welcome aboard, " + name + "! Safe travels.",
            Ansi.GREEN);
        console.println();

        // Hand off to main menu
        MainMenuView mainMenu = new MainMenuView(keyboard, console, game);
        mainMenu.display();

        return true; // done — exits the start loop
    }
}
