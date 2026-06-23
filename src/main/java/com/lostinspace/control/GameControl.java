package com.lostinspace.control;

import com.lostinspace.model.*;
import java.io.*;
import java.util.List;

/**
 * Handles game-level operations: create, save, restore.
 * Also provides factory methods for all initial game data.
 */
public final class GameControl {

    private GameControl() {}

    // ─── New game factory ────────────────────────────────────────────────────

    /** Create a fully initialized new game for the given player name. */
    public static Game createNewGame(String playerName) {
        Game game = new Game();

        Player player = new Player(playerName);
        loadStartingInventory(player.getInventory());
        game.setPlayer(player);

        game.setMap(MapControl.createMap());
        game.setFuel(new Fuel(50.0));
        game.setState(Game.State.PLAYING);

        return game;
    }

    /** Populate the player's starting weapon loadout. */
    private static void loadStartingInventory(Inventory inv) {
        inv.addWeapon(new Weapon("Blow Gun",       4,  2, "Common",    "A simple tube that blows darts. Effective... sometimes."));
        inv.addWeapon(new Weapon("Ray Gun",        12, 5, "Common",    "Classic sci-fi sidearm. Reliable and lightweight."));
        inv.addWeapon(new Weapon("Lava Shooter",   18, 3, "Rare",      "Fires superheated magma rounds. Burns everything."));
        inv.addWeapon(new Weapon("Poop Flinger",   7,  6, "Common",    "It flings poop. Somehow effective — aliens hate it."));
        inv.addWeapon(new Weapon("Slingshot",      6,  8, "Common",    "Lightweight and surprisingly accurate in zero-G."));
        inv.addWeapon(new Weapon("The Terminator", 30, 1, "Legendary", "This weapon does not stop. Neither do you."));
        inv.equip(1); // start with Ray Gun equipped
    }

    // ─── Save / Restore ─────────────────────────────────────────────────────

    /** Serialize the game to a file. */
    public static void saveGame(Game game, String filePath) throws IOException {
        game.snapshotTime();
        try (var fos = new FileOutputStream(filePath);
             var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        }
    }

    /**
     * Deserialize a game from a file.
     * @throws IOException on file I/O errors
     * @throws ClassNotFoundException if the save is from an incompatible version
     */
    public static Game restoreGame(String filePath) throws IOException, ClassNotFoundException {
        try (var fis = new FileInputStream(filePath);
             var ois = new ObjectInputStream(fis)) {
            return (Game) ois.readObject();
        }
    }
}
