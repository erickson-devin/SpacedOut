package com.lostinspace.control;

import com.lostinspace.model.*;
import java.util.List;
import java.util.Optional;

/**
 * Handles map creation and player navigation.
 * Data-driven: every planet is defined in one place (buildPlanets()),
 * and every alien is defined in buildAliens().
 * No more 25-method switch — navigation is done by planet index.
 */
public final class MapControl {

    private MapControl() {}

    // ─── Map factory ─────────────────────────────────────────────────────────

    /** Build and return the complete star map (25 destinations). */
    public static Map createMap() {
        return new Map(buildPlanets());
    }

    /**
     * All planet definitions in travel order.
     * Planet(index, name, desc, depth, surfaceDensity,
     *         hasAlien, hasFuel, hasPuzzle, travelCost)
     */
    private static List<Planet> buildPlanets() {
        return List.of(
            new Planet(0,  "Neptune",    "A frigid world of howling storms and icy methane seas.",         15, 7,  true,  true,  false, 0.0),
            new Planet(1,  "Uranus",     "A tilted gas giant drifting silently through the void.",         25, 14, true,  true,  true,  4.0),
            new Planet(2,  "Saturn",     "Magnificent rings of ice and rock circle its golden bands.",     37, 31, false, true,  true,  5.0),
            new Planet(3,  "Titan",      "A hazy moon thick with orange nitrogen clouds.",                 34, 19, true,  true,  false, 4.5),
            new Planet(4,  "Jupiter",    "The great red storm watches you like an eye. Get that checked.", 49, 24, true,  true,  true,  6.0),
            new Planet(5,  "Mars",       "Dry, red, and barren. The wind whispers old secrets.",           29, 15, false, true,  false, 5.5),
            new Planet(6,  "Moon",       "Hey — you can almost see your house from here.",                 22, 12, true,  false, true,  3.0),
            new Planet(7,  "Pluto",      "Did you hear about Pluto?! That's messed up, right?",           33, 18, false, true,  false, 7.0),
            new Planet(8,  "Fiesta",     "A party around every crater. Suspicious confetti everywhere.",   16, 28, true,  true,  true,  8.0),
            new Planet(9,  "Orblook",    "A perfectly round planet. Eerily, mathematically perfect.",      17, 20, false, false, true,  6.5),
            new Planet(10, "Zarthon",    "Made entirely of glowing crystals. Not crystal meth. Crystals.", 31, 12, true,  true,  true,  7.5),
            new Planet(11, "Athenia",    "Advanced civilization. Also: teenagers everywhere. Good luck.",  25, 22, true,  false, true,  6.0),
            new Planet(12, "Draconia",   "A harsh place. The locals are not happy to see you.",           19, 29, true,  true,  false, 8.0),
            new Planet(13, "Blowhard",   "High heat, strong winds, and insufferable locals.",              45, 22, false, true,  true,  9.0),
            new Planet(14, "Enano",      "Beware the dwarf star nearby — radiation burns.",               40, 21, true,  true,  false, 8.5),
            new Planet(15, "Niqueetun",  "Don't get caught in the curls — they'll spin you forever.",     42, 19, true,  false, true,  9.0),
            new Planet(16, "Barometer",  "Temperature is always changing. You'll never know the weather.", 29, 15, false, true,  false, 7.0),
            new Planet(17, "Black Hole", "There is no escape once you're too close. Choose wisely.",       50, 1,  true,  false, false, 12.0),
            new Planet(18, "Andromida",  "A planet far, far away in another galaxy entirely.",             43, 20, true,  true,  true,  15.0),
            new Planet(19, "Phobos",     "The fear of 'bos'. No one knows what that means.",              10, 4,  false, true,  false, 5.0),
            new Planet(20, "Mathilde",   "An average-sized meteor when measured. It measures back.",       21, 12, true,  false, true,  6.0),
            new Planet(21, "Luna",       "The dark side of the Moon. Dark and quiet and watching.",        17, 12, false, true,  false, 3.5),
            new Planet(22, "Cupid",      "Put a little love in your heart! The aliens here still bite.",   41, 22, true,  false, true,  7.5),
            new Planet(23, "MakeMake",   "If the beach were a planet, this would be it. Hostile sand.",   36, 23, false, true,  false, 5.0),
            new Planet(24, "Earth",      "Home. Sweet. Home. You made it.",                               42, 21, false, false, false, 4.0)
        );
    }

    // ─── Alien factory ───────────────────────────────────────────────────────

    /**
     * Returns an alien appropriate for the given planet.
     * Each planet gets a flavour-matched enemy.
     */
    public static Optional<Alien> getAlienForPlanet(Planet planet) {
        if (!planet.hasAlien()) return Optional.empty();

        Alien alien = switch (planet.name()) {
            case "Neptune"   -> new Alien("Cryo Grunt",    "grunt",     "A frost-bitten soldier of the outer rim.",   30, 5, 10, 15, 8,  5.0);
            case "Uranus"    -> new Alien("Gas Phantom",   "shadow",    "A swirling cloud of hostile gas and rage.",  25, 4,  9, 13, 10, 4.0);
            case "Titan"     -> new Alien("Smog Stalker",  "grunt",     "Breathes toxic haze and hates visitors.",    35, 6, 11, 16, 9,  6.0);
            case "Jupiter"   -> new Alien("Storm Rider",   "commander", "Surfing the red storm on a bolt of lightning.", 55, 8, 14, 22, 12, 10.0);
            case "Moon"      -> new Alien("Luna Lurker",   "shadow",    "Hides in the craters. Moves fast at night.", 28, 5,  9, 14, 15, 5.0);
            case "Fiesta"    -> new Alien("Party Crasher", "grunt",     "It showed up uninvited and will not leave.", 32, 6, 10, 15, 9,  5.5);
            case "Zarthon"   -> new Alien("Crystal Golem", "crystal",   "Mineral-based life form. Hits like a rock.", 50, 7, 13, 20, 7,  9.0);
            case "Athenia"   -> new Alien("Teen Rebel",    "grunt",     "Inexplicably strong and unbelievably annoying.", 22, 3,  7, 11, 11, 3.5);
            case "Draconia"  -> new Alien("Drac Warlord",  "commander", "Every scar on this creature tells a story.", 65, 9, 16, 25, 11, 12.0);
            case "Enano"     -> new Alien("Rad Burner",    "grunt",     "Irradiated and furious. Do not hug it.",     38, 6, 11, 17, 10, 7.0);
            case "Niqueetun" -> new Alien("Curl Spinner",  "shadow",    "It spins. You'll get dizzy first.",          40, 7, 12, 18, 14, 7.5);
            case "Black Hole"-> new Alien("Void Entity",   "void",      "It should not exist. And yet, here it is.",  80, 12,20, 30, 5,  20.0);
            case "Andromida" -> new Alien("Galaxy Stalker","commander", "It has crossed galaxies to find you specifically.", 70, 10, 18, 28, 13, 15.0);
            case "Mathilde"  -> new Alien("Rock Hopper",   "grunt",     "Bounces between boulders at terrifying speed.", 33, 5, 10, 16, 16, 6.0);
            case "Cupid"     -> new Alien("Love Biter",    "grunt",     "Don't let the heart-shaped markings fool you.", 28, 4,  9, 14, 9,  5.0);
            default          -> new Alien("Space Grunt",   "grunt",     "A standard-issue hostile life form.",         30, 5, 10, 15, 10, 5.0);
        };
        return Optional.of(alien);
    }

    // ─── Navigation ──────────────────────────────────────────────────────────

    /**
     * Attempt to move the player to the planet at the given index.
     * Consumes fuel. Returns false if not enough fuel.
     */
    public static boolean navigateTo(Game game, int planetIndex) {
        var mapOpt = Optional.ofNullable(game.getMap());
        if (mapOpt.isEmpty()) return false;

        var destOpt = game.getMap().getPlanet(planetIndex);
        if (destOpt.isEmpty()) return false;

        Planet dest = destOpt.get();
        Fuel fuel   = game.getFuel();

        if (!fuel.consume(dest.travelCost())) return false; // not enough fuel

        game.getPlayer().setCurrentPlanetIndex(planetIndex);

        // Check for victory
        if (planetIndex == game.getMap().earthIndex()) {
            game.setState(Game.State.WON);
        }

        return true;
    }

    /** Returns the planet the player is currently on. */
    public static Planet currentPlanet(Game game) {
        int idx = game.getPlayer().getCurrentPlanetIndex();
        return game.getMap().getPlanet(idx)
                   .orElseThrow(() -> new IllegalStateException("Invalid planet index: " + idx));
    }
}
