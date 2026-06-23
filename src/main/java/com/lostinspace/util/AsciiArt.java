package com.lostinspace.util;

import java.util.Map;

/**
 * All ASCII art used throughout the game, stored as multi-line text blocks.
 * Each constant is a static final String. The planet art doubles as both
 * landing scene art and the navigation map display.
 */
public final class AsciiArt {
    private AsciiArt() {}

    // ─────────────────────────────────────────────────────────────────────────
    //  TITLE SCREEN
    // ─────────────────────────────────────────────────────────────────────────

    public static final String TITLE = """
            \s
             ██╗      ██████╗ ███████╗████████╗    ██╗███╗   ██╗    ███████╗██████╗  █████╗  ██████╗███████╗
             ██║     ██╔═══██╗██╔════╝╚══██╔══╝    ██║████╗  ██║    ██╔════╝██╔══██╗██╔══██╗██╔════╝██╔════╝
             ██║     ██║   ██║███████╗   ██║       ██║██╔██╗ ██║    ███████╗██████╔╝███████║██║     █████╗ \s
             ██║     ██║   ██║╚════██║   ██║       ██║██║╚██╗██║    ╚════██║██╔═══╝ ██╔══██║██║     ██╔══╝ \s
             ███████╗╚██████╔╝███████║   ██║       ██║██║ ╚████║    ███████║██║     ██║  ██║╚██████╗███████╗
             ╚══════╝ ╚═════╝ ╚══════╝   ╚═╝       ╚═╝╚═╝  ╚═══╝    ╚══════╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚══════╝
            \s""";

    public static final String ROCKET_LAUNCH = """
                       *   .  *    .    * .   . *
                    *   .    *  .    .  *   .    *
                         .  *    . *  .  *    .
                   .  *    .   *   .    * .  .  *
                          ___
                         /   \\
                        | O O |
                        |  _  |
                        |     |
                       /|     |\\
                      / |     | \\
                     /  |_____|  \\
                    /___/     \\___\\
                        |     |
                        |     |
                        |_____|
                       (=======)
                       )=======(
                      (=========)
                      )=========(
                     | ~~~~~~~~~ |
                    /   ~~~~~~~   \\
                   /    ~~~~~~~    \\
                  *                 *
                       *   .  *
                    *   .    *  .
                """;

    public static final String VICTORY = """
                    🌍  Y O U   M A D E   I T   H O M E ! 🌍
            \s
                        *    .  *    *  .    *
                    .  *   .    .  *   .  *   .
                       *  .    *   . *    .  *
            \s
                            ████████╗
                           ██╔═══██║
                          ██║   ██║
                          ██║   ██║
                           ██╔══╝
                            ████╗
                              ╚══╝
            \s
                     W E L C O M E   H O M E ,   A S T R O N A U T
                """;

    public static final String GAME_OVER = """
                  ██████╗  █████╗ ███╗   ███╗███████╗    ██████╗ ██╗   ██╗███████╗██████╗
                 ██╔════╝ ██╔══██╗████╗ ████║██╔════╝   ██╔═══██╗██║   ██║██╔════╝██╔══██╗
                 ██║  ███╗███████║██╔████╔██║█████╗     ██║   ██║██║   ██║█████╗  ██████╔╝
                 ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝     ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                 ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗   ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                  ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝    ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
                """;

    // ─────────────────────────────────────────────────────────────────────────
    //  PLANETS
    // ─────────────────────────────────────────────────────────────────────────

    public static final String PLANET_GENERIC = """
                      _______
                   __/       \\__
                  /   * .  *   \\
                 | .    *    .  |
                 |  *  ( )  *   |
                 | .    *    .  |
                  \\__  *   * __/
                     \\_______/
                """;

    public static final String PLANET_NEPTUNE = """
                      _______
                   __/ ~~~~~ \\__
                  /  ~ ~ ~ ~ ~ \\
                 |~ ~ ~ NEPTUNE ~|
                 | ~ ~ ~ ~ ~ ~ ~ |
                  \\__ ~ ~ ~ ___/
                      \\_______/
                   [COLD & WINDY]
                """;

    public static final String PLANET_EARTH = """
                      _______
                   __/ @@@@@ \\__
                  / @@  ~~  @@ \\
                 | @ ~~ EARTH ~~ |
                 | @@ ~~ ~~ @@ @ |
                  \\__ @@ @@ ___/
                      \\_______/
                    [HOME SWEET HOME]
                """;

    public static final String PLANET_MARS = """
                      _______
                   __/ ::::: \\__
                  /  : : : : : \\
                 | : :  MARS  : :|
                 | : : : : : : : |
                  \\__ : : : ___/
                      \\_______/
                   [DRY & BARREN]
                """;

    public static final String PLANET_JUPITER = """
                      _________
                   __/ ======= \\__
                  / == ======= == \\
                 |=== JUPITER ====|
                 | =(  RED SPOT )=|
                  \\__ ======= ___/
                      \\_______/
                  [GIANT RED STORM]
                """;

    public static final String PLANET_SATURN = """
                  ___________________
                 /  _______          \\
                / /  :::::  \\         \\
               | | : SATURN: |  rings  |
               | |  ::::::::  |        |
                \\ \\___________/       /
                 \\___________________/
                """;

    public static final String PLANET_BLACKHOLE = """
                          *  .  *
                      * .   ###   . *
                    .  *  ##   ##  *  .
                   *   . #  BLACK  # .  *
                   .   * # HOLE!!! # *   .
                    *   . ##   ## .  *
                      .   * ### *   .
                          *  .  *
                  [THERE IS NO ESCAPE...]
                """;

    public static final String PLANET_ANDROMEDA = """
                       . * .  * . *  .
                    *  .   * . . *  . *
                      . *  ANDROMEDA * .
                    *  . A GALAXY FAR  *
                    .  *  FAR AWAY...  .
                    *  .  * . . *   . *
                       . *  .  * . *  .
                """;

    // ─────────────────────────────────────────────────────────────────────────
    //  ALIENS
    // ─────────────────────────────────────────────────────────────────────────

    public static final String ALIEN_GRUNT = """
                  ___oOo___
                 |  O   O  |
                 |    ^    |
                 |  \\___/  |
                  \\_______/
                 /|  | |  |\\
                / |  | |  | \\
                """;

    public static final String ALIEN_COMMANDER = """
                   ___/\\_____
                  | * *   * *|
                  |  * *  *  |
                  |   BOSS   |
                  |  *   *   |
                   \\________/
                  /|  ||||  |\\
                 / |  ||||  | \\
                """;

    public static final String ALIEN_SHADOW = """
                     /\\  /\\
                    /  \\/  \\
                   | SHADOW |
                   |  FORM  |
                    \\  /\\  /
                     \\/  \\/
                """;

    public static final String ALIEN_CRYSTAL = """
                      /\\
                     /##\\
                    /####\\
                   | XTAL |
                    \\####/
                     \\##/
                      \\/
                """;

    public static final String ALIEN_VOID = """
                   .  *  .  *  .
                  * .  VOID  . *
                  . * ENTITY * .
                  * .  *  .  * .
                   .  *  .  *  .
                """;

    // ─────────────────────────────────────────────────────────────────────────
    //  COMBAT
    // ─────────────────────────────────────────────────────────────────────────

    public static final String EXPLOSION = """
                      * . * . *
                    . * BOOM! * .
                    * .  *  . * .
                      * . * . *
                """;

    public static final String SHIELD = """
                    [  ===SHIELD===  ]
                    [ ATTACK BLOCKED ]
                    [  ============  ]
                """;

    public static final String CRITICAL_HIT = """
                     ╔═══════════════╗
                     ║  CRITICAL HIT! ║
                     ║   ★ ★ ★ ★ ★  ║
                     ╚═══════════════╝
                """;

    // ─────────────────────────────────────────────────────────────────────────
    //  MISC
    // ─────────────────────────────────────────────────────────────────────────

    public static final String FUEL_PUMP = """
                   ______
                  |      |
                  | FUEL |
                  |______|
                  |      |
                  | ████ |  ← mining
                  | ████ |
                  |______|
                     ||
                    ====
                """;

    public static final String PUZZLE_LOCK = """
                    ┌─────────┐
                    │  ? ? ?  │
                    │ PUZZLE  │
                    │  ? ? ?  │
                    └────╥────┘
                         ║
                      ╔══╩══╗
                      ║LOCK!║
                      ╚═════╝
                """;

    public static final String CHEST = """
                    ╔═══════╗
                    ║■ ■ ■ ■║
                    ╠═══════╣
                    ║       ║
                    ║ LOOT! ║
                    ╚═══════╝
                """;

    /**
     * Returns the ASCII art for a planet by name (case-insensitive).
     * Falls back to a generic planet if not specifically defined.
     */
    public static String forPlanet(String planetName) {
        return switch (planetName.toLowerCase()) {
            case "neptune"    -> PLANET_NEPTUNE;
            case "earth"      -> PLANET_EARTH;
            case "mars"       -> PLANET_MARS;
            case "jupiter"    -> PLANET_JUPITER;
            case "saturn"     -> PLANET_SATURN;
            case "black hole" -> PLANET_BLACKHOLE;
            case "andromida"  -> PLANET_ANDROMEDA;
            default           -> PLANET_GENERIC;
        };
    }

    /**
     * Returns the ASCII art for an alien by type name (case-insensitive).
     */
    public static String forAlien(String alienType) {
        return switch (alienType.toLowerCase()) {
            case "commander" -> ALIEN_COMMANDER;
            case "shadow"    -> ALIEN_SHADOW;
            case "crystal"   -> ALIEN_CRYSTAL;
            case "void"      -> ALIEN_VOID;
            default          -> ALIEN_GRUNT;
        };
    }
}
