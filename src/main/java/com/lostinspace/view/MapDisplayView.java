package com.lostinspace.view;

import com.lostinspace.control.MapControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Planet;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Star map display — shows all 25 planets with their key stats and the player's
 * current location marked. Uses the planet's index for selection.
 */
public class MapDisplayView extends BaseView {

    public MapDisplayView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Press ENTER to return");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();

        ConsoleUI.titleBox(console, "★  STAR MAP  ★", Ansi.BRIGHT_CYAN);
        console.println();

        List<Planet> planets = game.getMap().getPlanets();
        int playerIdx        = game.getPlayer().getCurrentPlanetIndex();

        // Header
        console.printf("  %s%-4s %-18s %-8s %-10s %-6s %-6s %-6s%s%n",
            Ansi.BRIGHT_CYAN + Ansi.BOLD,
            "#", "Planet", "Depth", "Density", "⛽Fuel", "👾Alien", "🔐Puzzle",
            Ansi.RESET);
        ConsoleUI.divider(console);

        for (Planet p : planets) {
            boolean isCurrent = (p.index() == playerIdx);
            boolean isEarth   = (p.index() == game.getMap().earthIndex());

            String rowColor = isCurrent ? Ansi.BRIGHT_YELLOW
                            : isEarth   ? Ansi.BRIGHT_GREEN
                            : Ansi.WHITE;

            String marker = isCurrent ? " ◄ YOU" : "";

            console.printf("  %s%-4d %-18s %-8d %-10d %-6s %-7s %-6s%s%s%n",
                rowColor,
                p.index() + 1,
                p.name(),
                p.depth(),
                p.surfaceDensity(),
                p.hasFuelDeposit() ? "YES" : "—",
                p.hasAlien()       ? "YES" : "—",
                p.hasPuzzle()      ? "YES" : "—",
                Ansi.RESET,
                isCurrent ? Ansi.BRIGHT_YELLOW + marker + Ansi.RESET : ""
            );
        }

        console.println();
        console.println(Ansi.DIM + "  ⛽=can mine fuel  👾=alien present  🔐=puzzle available" + Ansi.RESET);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        return true; // any input closes the map
    }
}
