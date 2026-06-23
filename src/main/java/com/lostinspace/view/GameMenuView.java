package com.lostinspace.view;

import com.lostinspace.control.GameControl;
import com.lostinspace.control.MapControl;
import com.lostinspace.model.Game;
import com.lostinspace.model.Planet;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The primary gameplay hub — shown after landing on a planet.
 * Displays the status bar and all available actions.
 */
public class GameMenuView extends BaseView {

    public GameMenuView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Choice");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);

        // Status bar at the top of every game screen
        statusBar();

        // Current planet art + description
        Planet current = MapControl.currentPlanet(game);
        console.println(Ansi.BRIGHT_CYAN + current.asciiArt() + Ansi.RESET);
        console.println("  " + Ansi.BOLD + current.name() + Ansi.RESET
                      + "  —  " + Ansi.DIM + current.description() + Ansi.RESET);
        console.println();

        // Fuel warning
        if (game.getFuel().isLow()) {
            warn("⚠  FUEL CRITICAL — mine more fuel before launching!");
            console.println();
        }

        ConsoleUI.titleBox(console, "  GAME MENU  ", Ansi.CYAN);
        console.println();

        ConsoleUI.menuTop(console);
        ConsoleUI.menuItem(console, "L", "Lift Off  (Launch to another planet)",      Ansi.BRIGHT_GREEN,  Ansi.WHITE);

        if (current.hasFuelDeposit())
            ConsoleUI.menuItem(console, "G", "Gather Fuel  (Mine this planet)",       Ansi.BRIGHT_YELLOW, Ansi.WHITE);

        if (current.hasAlien())
            ConsoleUI.menuItem(console, "E", "Engage Alien  (Battle the local enemy)", Ansi.BRIGHT_RED,   Ansi.WHITE);

        if (current.hasPuzzle())
            ConsoleUI.menuItem(console, "P", "Solve Puzzle  (Earn bonus fuel)",        Ansi.BRIGHT_CYAN,  Ansi.WHITE);

        ConsoleUI.menuItem(console, "F", "View Fuel Level",                            Ansi.CYAN,         Ansi.WHITE);
        ConsoleUI.menuItem(console, "C", "Weapons & Inventory",                        Ansi.MAGENTA,      Ansi.WHITE);
        ConsoleUI.menuItem(console, "M", "View Star Map",                              Ansi.BLUE,         Ansi.WHITE);
        ConsoleUI.menuItem(console, "R", "Print Planet Report",                        Ansi.DIM,          Ansi.WHITE);
        ConsoleUI.menuItem(console, "S", "Save Game",                                  Ansi.BRIGHT_YELLOW,Ansi.WHITE);
        ConsoleUI.menuItem(console, "H", "Help",                                       Ansi.BRIGHT_CYAN,  Ansi.WHITE);
        ConsoleUI.menuDivider(console);
        ConsoleUI.menuItem(console, "Q", "Quit to Main Menu",                          Ansi.RED,          Ansi.WHITE);
        ConsoleUI.menuBottom(console);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        Planet current = MapControl.currentPlanet(game);

        switch (input.toUpperCase()) {
            case "L" -> liftOff();
            case "G" -> {
                if (current.hasFuelDeposit()) gatherFuel();
                else error("No fuel deposits on this planet.");
            }
            case "E" -> {
                if (current.hasAlien()) engageAlien();
                else error("No alien presence detected here. Enjoy the peace.");
            }
            case "P" -> {
                if (current.hasPuzzle()) solvePuzzle();
                else error("No puzzle data found on this planet.");
            }
            case "F" -> viewFuel();
            case "C" -> weaponInventory();
            case "M" -> viewMap();
            case "R" -> printReport();
            case "S" -> saveGame();
            case "H" -> showHelp();
            default  -> error("Unknown command — try again.");
        }

        // After each action, check if the game ended
        if (game.getState() == Game.State.WON) {
            showVictory();
            return true;
        }
        if (game.getState() == Game.State.LOST) {
            showGameOver();
            return true;
        }

        render(); // redraw after each action
        return false;
    }

    // ─── Action handlers ─────────────────────────────────────────────────────

    private void liftOff() {
        NavigationView nav = new NavigationView(keyboard, console, game);
        nav.display();
    }

    private void gatherFuel() {
        FuelView fv = new FuelView(keyboard, console, game);
        fv.display();
    }

    private void engageAlien() {
        Planet current = MapControl.currentPlanet(game);
        MapControl.getAlienForPlanet(current).ifPresentOrElse(
            alien -> {
                FightView fv = new FightView(keyboard, console, game, alien);
                fv.display();
            },
            () -> info("The alien seems to have fled. Lucky you.")
        );
    }

    private void solvePuzzle() {
        PuzzleView pv = new PuzzleView(keyboard, console, game);
        pv.display();
    }

    private void viewFuel() {
        double pct = game.getFuel().getPercent();
        console.println();
        String fuelColor = pct > 50 ? Ansi.GREEN : pct > 20 ? Ansi.YELLOW : Ansi.RED;
        console.println(fuelColor + "  ⛽  Fuel: %.1f / %.1f  (%.1f%%)".formatted(
            game.getFuel().getCurrent(),
            game.getFuel().getCapacity(),
            pct) + Ansi.RESET);

        if (game.getFuel().isLow()) {
            warn("  Fuel is critically low! Mine more before launching.");
        }
        console.println();
    }

    private void weaponInventory() {
        WeaponView wv = new WeaponView(keyboard, console, game);
        wv.display();
    }

    private void viewMap() {
        MapDisplayView mv = new MapDisplayView(keyboard, console, game);
        mv.display();
    }

    private void printReport() {
        this.prompt = "Enter file path for report (e.g. report.txt)";
        String filePath = readLine();
        this.prompt = "Choice";

        try (var out = new PrintWriter(filePath)) {
            var map = game.getMap();
            var player = game.getPlayer();

            out.println("═".repeat(60));
            out.println("  LOST IN SPACE — PLANET REPORT");
            out.println("  Player: " + player.getName());
            out.println("  Play Time: %.1f seconds".formatted(game.getTotalTimeSeconds()));
            out.println("  Score: %.0f".formatted(player.getScore()));
            out.println("═".repeat(60));
            out.println();
            out.printf("%-18s %-8s %-10s %-6s %-6s %-6s%n",
                       "Planet", "Depth", "Density", "Fuel", "Alien", "Puzzle");
            out.println("-".repeat(60));

            for (var planet : map.getPlanets()) {
                out.printf("%-18s %-8d %-10d %-6s %-6s %-6s%n",
                    planet.name(),
                    planet.depth(),
                    planet.surfaceDensity(),
                    planet.hasFuelDeposit() ? "YES" : "no",
                    planet.hasAlien()       ? "YES" : "no",
                    planet.hasPuzzle()      ? "YES" : "no");
            }
            out.flush();
            success("Report saved to: " + filePath);
        } catch (IOException e) {
            error("Could not write report: " + e.getMessage());
        }
    }

    private void saveGame() {
        this.prompt = "Enter file path to save game";
        String filePath = readLine();
        this.prompt = "Choice";

        try {
            GameControl.saveGame(game, filePath);
            success("Game saved to: " + filePath);
        } catch (IOException e) {
            error("Could not save: " + e.getMessage());
        }
    }

    private void showHelp() {
        HelpView help = new HelpView(keyboard, console, game);
        help.display();
    }

    // ─── End-game screens ────────────────────────────────────────────────────

    private void showVictory() {
        ConsoleUI.clearScreen(console);
        console.println(Ansi.BRIGHT_GREEN + AsciiArt.VICTORY + Ansi.RESET);
        console.println();
        console.println(Ansi.BRIGHT_WHITE + "  Final Score: " + Ansi.BRIGHT_YELLOW
                      + "%.0f".formatted(game.getPlayer().getScore()) + Ansi.RESET);
        console.println("  Time Elapsed: %.1f seconds".formatted(game.getTotalTimeSeconds()));
        console.println();
        ConsoleUI.typewriter(console, "  " + Ansi.DIM + "Press ENTER to return to main menu." + Ansi.RESET, 8);
        readLineOnce();
    }

    private void showGameOver() {
        ConsoleUI.clearScreen(console);
        console.println(Ansi.RED + AsciiArt.GAME_OVER + Ansi.RESET);
        console.println();
        ConsoleUI.typewriter(console, "  " + Ansi.DIM + "Press ENTER to return to main menu." + Ansi.RESET, 8);
        readLineOnce();
    }
}
