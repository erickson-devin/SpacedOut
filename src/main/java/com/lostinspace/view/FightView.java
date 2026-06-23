package com.lostinspace.view;

import com.lostinspace.control.ActorControl;
import com.lostinspace.control.CombatResult;
import com.lostinspace.model.Alien;
import com.lostinspace.model.Game;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.AsciiArt;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Combat screen — fully implemented with sealed CombatResult pattern matching.
 * Tracks alien HP locally, shows ASCII art for attacks, and resolves fight end.
 */
public class FightView extends BaseView {

    private final Alien  alien;
    private       int    alienHp;
    private       boolean fightOver = false;

    public FightView(BufferedReader keyboard, PrintWriter console, Game game, Alien alien) {
        super(keyboard, console, game, "Action");
        this.alien   = alien;
        this.alienHp = alien.maxHp();
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();

        // Encounter banner
        console.println(Ansi.RED + alien.asciiArt() + Ansi.RESET);
        ConsoleUI.typewriter(console,
            Ansi.BRIGHT_RED + "  ⚠  A " + alien.name() + " appears!" + Ansi.RESET, 20);
        console.println("  " + Ansi.DIM + alien.description() + Ansi.RESET);
        console.println();

        printCombatStatus();
        printMenu();
    }

    private void printCombatStatus() {
        int pHp = game.getPlayer().getHp();
        int pMax = game.getPlayer().getMaxHp();
        String pHpColor = pHp > 50 ? Ansi.GREEN : pHp > 25 ? Ansi.YELLOW : Ansi.RED;
        String aHpColor = alienHp > alien.maxHp() * 0.5 ? Ansi.RED : alienHp > alien.maxHp() * 0.25 ? Ansi.YELLOW : Ansi.GREEN;

        console.printf("  %sYOU%s    HP: %s%d/%d%s%n", Ansi.BOLD, Ansi.RESET, pHpColor, pHp, pMax, Ansi.RESET);
        console.printf("  %s%-12s%s HP: %s%d/%d%s%n", Ansi.RED + Ansi.BOLD, alien.name(), Ansi.RESET, aHpColor, alienHp, alien.maxHp(), Ansi.RESET);
        console.println();
    }

    private void printMenu() {
        ConsoleUI.menuTop(console);
        ConsoleUI.menuItem(console, "B", "Battle — Attack the alien",    Ansi.BRIGHT_RED,    Ansi.WHITE);
        ConsoleUI.menuItem(console, "F", "Flee  — Attempt to escape",    Ansi.BRIGHT_YELLOW, Ansi.WHITE);
        ConsoleUI.menuItem(console, "T", "Taunt — Throw dirt and scream",Ansi.DIM,           Ansi.WHITE);
        ConsoleUI.menuBottom(console);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        switch (input.toUpperCase()) {
            case "B" -> attack();
            case "F" -> flee();
            case "T" -> taunt();
            default  -> error("Invalid selection.");
        }
        return fightOver;
    }

    private void attack() {
        CombatResult result = ActorControl.attackAlien(game.getPlayer(), alien, alienHp);

        switch (result) {
            case CombatResult.PlayerHit hit -> {
                alienHp = hit.alienHpRemaining();
                if (hit.critical()) {
                    console.println(Ansi.BRIGHT_YELLOW + AsciiArt.CRITICAL_HIT + Ansi.RESET);
                } else {
                    console.println(Ansi.BRIGHT_RED + AsciiArt.EXPLOSION + Ansi.RESET);
                }
                success("You hit " + alien.name() + " for " + Ansi.BRIGHT_RED + hit.damage() + Ansi.RESET + " damage!");
                // Alien counter-attack result
                CombatResult.AlienHit counter = ActorControl.alienCounterAttack(game.getPlayer(), alien);
                error(alien.name() + " retaliates for " + counter.damage() + " damage! Your HP: " + counter.playerHpRemaining());
            }
            case CombatResult.AlienHit miss -> {
                error("You missed! " + alien.name() + " lands " + miss.damage() + " damage. HP left: " + miss.playerHpRemaining());
            }
            case CombatResult.AlienDefeated victory -> {
                alienHp = 0;
                console.println();
                success(Ansi.BOLD + alien.name() + " defeated!" + Ansi.RESET);
                game.getFuel().add(victory.fuelReward());
                console.println(Ansi.BRIGHT_YELLOW + "  ⛽  +" + "%.1f".formatted(victory.fuelReward()) + " fuel collected!" + Ansi.RESET);
                console.println(Ansi.GREEN + "  ★  +" + "%.0f".formatted(victory.scoreGain()) + " score earned!" + Ansi.RESET);
                console.println();
                fightOver = true;
            }
            case CombatResult.PlayerDefeated ignored -> {
                console.println();
                console.println(Ansi.RED + AsciiArt.GAME_OVER + Ansi.RESET);
                error("You have been defeated by " + alien.name() + "...");
                game.setState(Game.State.LOST);
                fightOver = true;
            }
            default -> info("Nothing happened.");
        }

        if (!fightOver) {
            console.println();
            printCombatStatus();
            printMenu();
        }
    }

    private void flee() {
        CombatResult result = ActorControl.fleeAlien(game.getPlayer(), alien);

        switch (result) {
            case CombatResult.Fled ignored -> {
                success("You successfully fled from " + alien.name() + "!");
                fightOver = true;
            }
            case CombatResult.CaughtFleeing caught -> {
                error("Caught! " + alien.name() + " attacks as you run — took " + caught.damage() + " damage. HP: " + caught.playerHpRemaining());
                console.println();
                printCombatStatus();
                printMenu();
            }
            case CombatResult.PlayerDefeated ignored -> {
                error("You were killed while trying to flee.");
                game.setState(Game.State.LOST);
                fightOver = true;
            }
            default -> info("Something went wrong with the flee attempt.");
        }
    }

    private void taunt() {
        console.println();
        ConsoleUI.typewriter(console,
            Ansi.YELLOW + "  You throw a handful of space-dirt and scream into the void." + Ansi.RESET);
        console.println(Ansi.DIM + "  The " + alien.name() + " tilts its head, confused but unimpressed." + Ansi.RESET);
        console.println(Ansi.DIM + "  For now, nothing happens. For now." + Ansi.RESET);
        console.println();
        printCombatStatus();
        printMenu();
    }
}
