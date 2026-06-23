package com.lostinspace.view;

import com.lostinspace.control.GameControl;
import com.lostinspace.model.Game;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main menu — New Game, Restore, Help, Quit.
 * Reached after entering a name, or when returning from the game.
 */
public class MainMenuView extends BaseView {

    public MainMenuView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Choice");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        console.println();
        ConsoleUI.titleBox(console, "★  MAIN MENU  ★", Ansi.CYAN);
        console.println();

        ConsoleUI.menuTop(console);
        ConsoleUI.menuItem(console, "N", "Start New Game",                   Ansi.BRIGHT_GREEN,  Ansi.WHITE);
        ConsoleUI.menuItem(console, "R", "Restore Existing Game",            Ansi.BRIGHT_YELLOW, Ansi.WHITE);
        ConsoleUI.menuItem(console, "S", "Save Current Game",                Ansi.BRIGHT_YELLOW, Ansi.WHITE);
        ConsoleUI.menuItem(console, "H", "How To Play",                      Ansi.BRIGHT_CYAN,   Ansi.WHITE);
        ConsoleUI.menuDivider(console);
        ConsoleUI.menuItem(console, "Q", "Quit",                             Ansi.RED,           Ansi.WHITE);
        ConsoleUI.menuBottom(console);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        switch (input.toUpperCase()) {
            case "N" -> startNewGame();
            case "R" -> restoreGame();
            case "S" -> saveGame();
            case "H" -> showHelp();
            default  -> {
                error("Invalid selection — try again.");
                render();
            }
        }
        return false; // main menu never exits on its own
    }

    private void startNewGame() {
        // Re-use the existing game if it has a player name; otherwise prompt
        if (game != null && game.getPlayer() != null) {
            GameMenuView gm = new GameMenuView(keyboard, console, game);
            gm.display();
        } else {
            // Re-route to start screen to get a name
            StartProgramView start = new StartProgramView(keyboard, console);
            start.display();
        }
        render(); // redraw after returning
    }

    private void restoreGame() {
        this.prompt = "Enter file path for saved game";
        String filePath = readLine();
        this.prompt = "Choice";

        try {
            Game restored = GameControl.restoreGame(filePath);
            success("Game restored successfully! Welcome back, " + restored.getPlayer().getName() + "!");
            console.println();
            GameMenuView gm = new GameMenuView(keyboard, console, restored);
            gm.display();
        } catch (IOException | ClassNotFoundException e) {
            error("Could not restore game: " + e.getMessage());
        }
        render();
    }

    private void saveGame() {
        if (game == null || game.getPlayer() == null) {
            warn("No active game to save. Start a new game first.");
            return;
        }
        this.prompt = "Enter file path to save game";
        String filePath = readLine();
        this.prompt = "Choice";

        try {
            GameControl.saveGame(game, filePath);
            success("Game saved to: " + filePath);
        } catch (IOException e) {
            error("Could not save game: " + e.getMessage());
        }
    }

    private void showHelp() {
        HelpView help = new HelpView(keyboard, console, game);
        help.display();
        render();
    }
}
