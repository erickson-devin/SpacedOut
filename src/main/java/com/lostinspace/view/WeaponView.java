package com.lostinspace.view;

import com.lostinspace.model.Game;
import com.lostinspace.model.Inventory;
import com.lostinspace.model.Weapon;
import com.lostinspace.util.Ansi;
import com.lostinspace.util.ConsoleUI;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Weapons & inventory screen — shows all weapons, lets player equip a different one.
 * All 3 stub methods from the original are now fully implemented.
 */
public class WeaponView extends BaseView {

    public WeaponView(BufferedReader keyboard, PrintWriter console, Game game) {
        super(keyboard, console, game, "Choice");
    }

    @Override
    protected void render() {
        ConsoleUI.clearScreen(console);
        statusBar();
        ConsoleUI.titleBox(console, "  WEAPONS & INVENTORY  ", Ansi.MAGENTA);
        console.println();

        ConsoleUI.menuTop(console);
        ConsoleUI.menuItem(console, "C", "Currently Equipped Weapon",  Ansi.BRIGHT_MAGENTA, Ansi.WHITE);
        ConsoleUI.menuItem(console, "I", "Full Inventory List",         Ansi.MAGENTA,        Ansi.WHITE);
        ConsoleUI.menuItem(console, "X", "Equip a Different Weapon",    Ansi.BRIGHT_YELLOW,  Ansi.WHITE);
        ConsoleUI.menuDivider(console);
        ConsoleUI.menuItem(console, "Q", "Back to Game Menu",           Ansi.RED,            Ansi.WHITE);
        ConsoleUI.menuBottom(console);
        console.println();
    }

    @Override
    protected boolean doAction(String input) {
        switch (input.toUpperCase()) {
            case "C" -> showEquipped();
            case "I" -> showInventory();
            case "X" -> equipWeapon();
            default  -> error("Invalid selection.");
        }
        render();
        return false;
    }

    private void showEquipped() {
        Inventory inv = game.getPlayer().getInventory();
        inv.getEquippedWeapon().ifPresentOrElse(
            w -> {
                console.println();
                ConsoleUI.titleBox(console, "Currently Equipped", Ansi.MAGENTA);
                console.println();
                printWeaponDetails(w);
                console.println();
            },
            () -> warn("No weapon equipped.")
        );
    }

    private void showInventory() {
        Inventory inv = game.getPlayer().getInventory();
        List<Weapon> weapons = inv.getWeapons();

        console.println();
        ConsoleUI.titleBox(console, "Full Inventory (" + weapons.size() + " weapons)", Ansi.MAGENTA);
        console.println();

        if (weapons.isEmpty()) {
            warn("Your inventory is empty.");
            return;
        }

        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i);
            boolean isEquipped = inv.getEquippedWeapon()
                                    .map(eq -> eq.equals(w)).orElse(false);
            String marker = isEquipped ? Ansi.BRIGHT_GREEN + "► " + Ansi.RESET : "  ";
            console.printf("  %s%d. %s%n", marker, i + 1, w);
        }
        console.println();
    }

    private void equipWeapon() {
        showInventory();

        this.prompt = "Enter weapon number to equip";
        String input = readLine();
        this.prompt = "Choice";

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            error("Please enter a valid weapon number.");
            return;
        }

        Inventory inv = game.getPlayer().getInventory();
        if (inv.equip(idx)) {
            String name = inv.getEquippedWeapon().map(Weapon::name).orElse("?");
            success("You equipped: " + Ansi.BOLD + name + Ansi.RESET);
        } else {
            error("Invalid weapon number.");
        }
    }

    private void printWeaponDetails(Weapon w) {
        console.println("  " + Ansi.BOLD + Ansi.BRIGHT_WHITE + w.name() + Ansi.RESET);
        console.println("  Damage      : " + Ansi.BRIGHT_RED   + w.damage()      + Ansi.RESET);
        console.println("  Speed Bonus : " + Ansi.BRIGHT_CYAN  + "+" + w.speedBonus() + Ansi.RESET);
        console.println("  Rarity      : " + rarityColor(w.rarity()) + w.rarity() + Ansi.RESET);
        console.println("  Description : " + Ansi.DIM + w.description() + Ansi.RESET);
    }

    private String rarityColor(String rarity) {
        return switch (rarity) {
            case "Legendary" -> Ansi.BRIGHT_YELLOW;
            case "Rare"      -> Ansi.BRIGHT_CYAN;
            default          -> Ansi.WHITE;
        };
    }
}
