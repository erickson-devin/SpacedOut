package com.lostinspace.view;

import com.lostinspace.control.MapControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Planet;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Data-driven navigation — replaces the 25-case switch with a numbered list.
 * The player selects a destination by number. One handler works for all planets.
 */
public class NavigationView extends BaseView {

    public NavigationView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Destination number (or Q to cancel)");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();

        console.println(Ansi.BRIGHT_CYAN + AsciiArt.ROCKET_LAUNCH + Ansi.RESET);

        Planet current = MapControl.currentPlanet(game);
        info("Current location: " + Ansi.BOLD + current.name() + Ansi.RESET);
        info("Current fuel:     " + Ansi.BRIGHT_YELLOW + "%.1f units".formatted(game.getFuel().getCurrent()) + Ansi.RESET);
        console.println();

        ConsoleUI.titleBox(console, "★  NAVIGATION — CHOOSE DESTINATION  ★", Ansi.CYAN);
        console.println();

        List<Planet> planets = game.getMap().getPlanets();
        int playerIdx        = game.getPlayer().getCurrentPlanetIndex();

        ConsoleUI.menuTop(console);

        for (Planet p : planets) {
            boolean isCurrent = (p.index() == playerIdx);
            boolean isEarth   = (p.index() == game.getMap().earthIndex());

            String number = "%2d".formatted(p.index() + 1);
            String name   = "%-18s".formatted(p.name());
            String cost   = "⛽ %.0f".formatted(p.travelCost());
            String flags  = buildFlags(p);

            String numberColor = isCurrent ? Ansi.DIM : Ansi.BRIGHT_YELLOW;
            String nameColor   = isCurrent ? Ansi.DIM : (isEarth ? Ansi.BRIGHT_GREEN : Ansi.WHITE);
            String marker      = isCurrent ? Ansi.DIM + " [HERE]   " + Ansi.RESET : "           ";

            console.printf("%s%s  %s%s%s  %s%s%s  %s%s%s%s%n",
                Ansi.CYAN + ConsoleUI.V + Ansi.RESET + " ",
                numberColor + number + Ansi.RESET,
                nameColor + name + Ansi.RESET,
                Ansi.DIM, cost, Ansi.RESET,
                marker,
                Ansi.DIM, flags, Ansi.RESET,
                " ".repeat(Math.max(0, 5 - flags.length())),
                Ansi.CYAN + ConsoleUI.V + Ansi.RESET
            );
        }

        ConsoleUI.menuBottom(console);
        console.println();

        console.println(Ansi.DIM + "  Legend: 👾=alien  ⛽=fuel  🔐=puzzle  🌍=Earth" + Ansi.RESET);
        console.println();
    }

    private String buildFlags(Planet p) {
        var sb = new StringBuilder();
        if (p.hasAlien())       sb.append("👾");
        if (p.hasFuelDeposit()) sb.append("⛽");
        if (p.hasPuzzle())      sb.append("🔐");
        if (p.index() == game.getMap().earthIndex()) sb.append("🌍");
        return sb.toString();
    }

    @Override
    protected boolean doAction(String input) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            error("Please enter a number from the list.");
            return false;
        }

        int destIndex = choice - 1; // convert 1-based to 0-based
        if (destIndex < 0 || destIndex >= game.getMap().size()) {
            error("Number out of range. Choose between 1 and " + game.getMap().size() + ".");
            return false;
        }

        Planet dest = game.getMap().getPlanet(destIndex).orElseThrow();
        Planet current = MapControl.currentPlanet(game);

        if (destIndex == current.index()) {
            warn("You're already on " + dest.name() + "!");
            return false;
        }

        if (game.getFuel().getCurrent() < dest.travelCost()) {
            error("Not enough fuel! You need %.1f units but only have %.1f."
                .formatted(dest.travelCost(), game.getFuel().getCurrent()));
            warn("Mine more fuel before attempting this journey.");
            return false;
        }

        // Navigate!
        console.println();
        ConsoleUI.typewriter(console, Ansi.BRIGHT_CYAN
            + "  🚀  Launching toward " + dest.name() + "..." + Ansi.RESET);
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}

        boolean success = MapControl.navigateTo(game, destIndex);

        if (success) {
            console.println();
            console.println(dest.asciiArt());
            ConsoleUI.typewriter(console,
                Ansi.GREEN + "  ✔  You have arrived at " + dest.name() + "!" + Ansi.RESET);
            console.println();
            ConsoleUI.typewriter(console, "  " + Ansi.DIM + dest.description() + Ansi.RESET);
            console.println();

            try { Thread.sleep(400); } catch (InterruptedException ignored) {}
            return true; // done — return to GameMenuView
        } else {
            error("Navigation failed. (This should not happen — please report this bug.)");
            return false;
        }
    }
}
