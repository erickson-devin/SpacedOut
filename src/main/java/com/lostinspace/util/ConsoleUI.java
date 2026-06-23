package com.lostinspace.util;

import java.io.PrintWriter;

/**
 * Console UI helpers: box drawing, status bar, typewriter effect, clear screen.
 * All methods take a PrintWriter so they integrate cleanly with the View layer.
 */
public final class ConsoleUI {
    private ConsoleUI() {}

    // Box-drawing character sets
    public static final String H  = "═";   // horizontal
    public static final String V  = "║";   // vertical
    public static final String TL = "╔";   // top-left corner
    public static final String TR = "╗";   // top-right corner
    public static final String BL = "╚";   // bottom-left corner
    public static final String BR = "╝";   // bottom-right corner
    public static final String ML = "╠";   // middle-left (divider)
    public static final String MR = "╣";   // middle-right (divider)

    private static final int DEFAULT_WIDTH = 62;

    /** Clear the terminal screen. */
    public static void clearScreen(PrintWriter out) {
        out.print("\033[H\033[2J");
        out.flush();
    }

    /** Print a horizontal divider line. */
    public static void divider(PrintWriter out) {
        out.println(Ansi.BRIGHT_BLACK + "  " + "─".repeat(DEFAULT_WIDTH) + Ansi.RESET);
    }

    /**
     * Print a framed title box.
     * @param title  The title text (centered inside the box)
     * @param color  An Ansi color constant
     */
    public static void titleBox(PrintWriter out, String title, String color) {
        int inner = DEFAULT_WIDTH - 2;
        String border = H.repeat(inner);
        String padded = center(title, inner);
        out.println(color + TL + border + TR + Ansi.RESET);
        out.println(color + V + Ansi.BOLD + Ansi.BRIGHT_WHITE + padded + color + V + Ansi.RESET);
        out.println(color + BL + border + BR + Ansi.RESET);
    }

    /**
     * Print a simple framed section box (no bold title).
     */
    public static void box(PrintWriter out, String text, String color) {
        int inner = DEFAULT_WIDTH - 2;
        String border = H.repeat(inner);
        String padded = center(text, inner);
        out.println(color + TL + border + TR + Ansi.RESET);
        out.println(color + V + padded + color + V + Ansi.RESET);
        out.println(color + BL + border + BR + Ansi.RESET);
    }

    /**
     * Print a framed menu option line.
     * Example:  ║  [N] Navigate                                        ║
     */
    public static void menuItem(PrintWriter out, String key, String label, String keyColor, String textColor) {
        int inner = DEFAULT_WIDTH - 2;
        String keyStr = keyColor + "[" + key + "]" + Ansi.RESET + " " + textColor + label + Ansi.RESET;
        // Strip ansi codes to calculate real length for padding
        int rawLen = ("[" + key + "] " + label).length();
        int pad = Math.max(0, inner - 1 - rawLen);
        out.println(Ansi.CYAN + V + Ansi.RESET + " " + keyStr + " ".repeat(pad) + Ansi.CYAN + V + Ansi.RESET);
    }

    /** Print the bottom border of a menu frame. */
    public static void menuBottom(PrintWriter out) {
        out.println(Ansi.CYAN + BL + H.repeat(DEFAULT_WIDTH - 2) + BR + Ansi.RESET);
    }

    /** Print the top border of a menu frame. */
    public static void menuTop(PrintWriter out) {
        out.println(Ansi.CYAN + TL + H.repeat(DEFAULT_WIDTH - 2) + TR + Ansi.RESET);
    }

    /** Print a divider row inside a menu frame. */
    public static void menuDivider(PrintWriter out) {
        out.println(Ansi.CYAN + ML + H.repeat(DEFAULT_WIDTH - 2) + MR + Ansi.RESET);
    }

    /**
     * Render the persistent player status bar.
     *
     * @param playerName   Current player name
     * @param hp           Current HP (0-100)
     * @param maxHp        Max HP
     * @param fuel         Current fuel units
     * @param fuelCap      Max fuel capacity
     * @param planetName   Current planet name
     * @param weaponName   Equipped weapon name (or "None")
     */
    public static void statusBar(PrintWriter out, String playerName,
                                  int hp, int maxHp,
                                  double fuel, double fuelCap,
                                  String planetName, String weaponName) {
        String hpColor  = hp > 50 ? Ansi.GREEN : hp > 25 ? Ansi.YELLOW : Ansi.RED;
        String fuelColor = fuel / fuelCap > 0.3 ? Ansi.CYAN : Ansi.BRIGHT_YELLOW;

        String hpBar   = progressBar(hp, maxHp, 10, hpColor);
        String fuelBar = progressBar((int) fuel, (int) fuelCap, 10, fuelColor);

        int fuelPct = (int)((fuel / fuelCap) * 100);

        out.println();
        divider(out);
        out.printf("  %s%-12s%s │ %sHP%s %s %s%d/%d%s │ %sFUEL%s %s %s%d%%%s │ %s🪐 %s%s │ %s⚔  %s%s%n",
            Ansi.BOLD + Ansi.BRIGHT_WHITE, playerName, Ansi.RESET,
            Ansi.DIM, Ansi.RESET, hpBar, hpColor, hp, maxHp, Ansi.RESET,
            Ansi.DIM, Ansi.RESET, fuelBar, fuelColor, fuelPct, Ansi.RESET,
            Ansi.DIM, Ansi.BRIGHT_CYAN, planetName, Ansi.RESET,
            Ansi.DIM, Ansi.BRIGHT_MAGENTA, weaponName, Ansi.RESET
        );
        divider(out);
        out.println();
    }

    /**
     * Print text one character at a time (typewriter effect).
     * @param delay milliseconds between characters
     */
    public static void typewriter(PrintWriter out, String text, long delay) {
        for (char c : text.toCharArray()) {
            out.print(c);
            out.flush();
            try { Thread.sleep(delay); } catch (InterruptedException ignored) {}
        }
        out.println();
    }

    /** Standard typewriter speed. */
    public static void typewriter(PrintWriter out, String text) {
        typewriter(out, text, 18);
    }

    /** Fast typewriter (for less dramatic moments). */
    public static void typewriterFast(PrintWriter out, String text) {
        typewriter(out, text, 6);
    }

    // ─── Private helpers ────────────────────────────────────────────────────

    /** Center a string within a field of `width` characters. */
    private static String center(String s, int width) {
        if (s.length() >= width) return s.substring(0, width);
        int left  = (width - s.length()) / 2;
        int right = width - s.length() - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    /** Render a mini ASCII progress bar. */
    private static String progressBar(int current, int max, int bars, String color) {
        if (max <= 0) return "??????????";
        int filled = (int) Math.round((double) current / max * bars);
        filled = Math.max(0, Math.min(bars, filled));
        String bar = "█".repeat(filled) + Ansi.DIM + "░".repeat(bars - filled);
        return color + bar + Ansi.RESET;
    }
}
