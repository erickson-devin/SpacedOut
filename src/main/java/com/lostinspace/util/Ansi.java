package com.lostinspace.util;

/**
 * ANSI terminal escape codes for colorized CLI output.
 * Usage: System.out.println(Ansi.CYAN + "Hello!" + Ansi.RESET);
 */
public final class Ansi {
    private Ansi() {}

    public static final String RESET   = "\u001B[0m";
    public static final String BOLD    = "\u001B[1m";
    public static final String DIM     = "\u001B[2m";
    public static final String ITALIC  = "\u001B[3m";

    // Foreground colors
    public static final String BLACK   = "\u001B[30m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";

    // Bright foreground
    public static final String BRIGHT_BLACK   = "\u001B[90m";
    public static final String BRIGHT_RED     = "\u001B[91m";
    public static final String BRIGHT_GREEN   = "\u001B[92m";
    public static final String BRIGHT_YELLOW  = "\u001B[93m";
    public static final String BRIGHT_BLUE    = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String BRIGHT_CYAN    = "\u001B[96m";
    public static final String BRIGHT_WHITE   = "\u001B[97m";

    // Background colors
    public static final String BG_BLACK   = "\u001B[40m";
    public static final String BG_BLUE    = "\u001B[44m";
    public static final String BG_CYAN    = "\u001B[46m";

    /** Wrap text in a color, then reset. */
    public static String color(String ansiCode, String text) {
        return ansiCode + text + RESET;
    }

    public static String bold(String text)    { return BOLD + text + RESET; }
    public static String red(String text)     { return RED + text + RESET; }
    public static String green(String text)   { return GREEN + text + RESET; }
    public static String yellow(String text)  { return YELLOW + text + RESET; }
    public static String cyan(String text)    { return CYAN + text + RESET; }
    public static String magenta(String text) { return MAGENTA + text + RESET; }
    public static String brightCyan(String text) { return BRIGHT_CYAN + text + RESET; }
    public static String brightYellow(String text) { return BRIGHT_YELLOW + text + RESET; }
    public static String dim(String text)     { return DIM + text + RESET; }
}
