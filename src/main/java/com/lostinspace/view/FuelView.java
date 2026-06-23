package com.lostinspace.view;

import com.lostinspace.control.FuelControl;
import com.lostinspace.control.MapControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Planet;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Fuel mining screen — uses actual planet depth and density.
 * Shows ASCII fuel pump and explains the mining mechanic.
 */
public class FuelView extends BaseView {

    public FuelView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Drill depth (1-10, or Q to cancel)");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();

        Planet planet = MapControl.currentPlanet(game);

        console.println(Ansi.BRIGHT_YELLOW + AsciiArt.FUEL_PUMP + Ansi.RESET);
        ConsoleUI.titleBox(console, "  FUEL MINING — " + planet.name().toUpperCase() + "  ", Ansi.BRIGHT_YELLOW);
        console.println();

        console.println(Ansi.CYAN + "  Planet Stats:" + Ansi.RESET);
        console.println("    Drill Depth Limit : " + Ansi.BRIGHT_WHITE + planet.depth() + Ansi.RESET);
        console.println("    Surface Density   : " + Ansi.BRIGHT_WHITE + planet.surfaceDensity() + Ansi.RESET);
        console.println();
        console.println(Ansi.DIM + """
          Mining formula: yield = (your_input + random) - (density/5)
          If your yield exceeds the depth limit, the drill breaks through.
          Choose a lower input to play it safe, or risk it for more fuel!
          """ + Ansi.RESET);
    }

    @Override
    protected boolean doAction(String input) {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            error("Please enter a number between 1 and 10.");
            return false;
        }

        Planet planet = MapControl.currentPlanet(game);
        FuelControl.MineResult result = FuelControl.mine(planet, game.getFuel(), choice);

        return switch (result) {
            case FuelControl.Success s -> {
                console.println();
                success("You mined %.1f units of fuel!".formatted(s.amount()));
                console.println(Ansi.BRIGHT_YELLOW + "  ⛽  Fuel tank: %.1f / %.1f  (%.1f%%)"
                    .formatted(game.getFuel().getCurrent(),
                               game.getFuel().getCapacity(),
                               game.getFuel().getPercent()) + Ansi.RESET);
                game.getPlayer().addScore(s.amount() * 2);
                console.println();
                yield true;
            }
            case FuelControl.ExceededDepth e -> {
                error("Drill exceeded depth limit of " + e.depth() + "! No fuel collected — try a lower number.");
                yield false;
            }
            case FuelControl.InvalidInput iv -> {
                error(iv.reason());
                yield false;
            }
        };
    }
}
