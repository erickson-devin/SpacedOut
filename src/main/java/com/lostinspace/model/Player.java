package com.lostinspace.model;

import java.io.Serializable;

/**
 * The player — tracks identity, stats, location, and inventory.
 * Mutable (game state changes as the player travels, takes damage, etc.).
 */
public class Player implements Serializable {

    // Identity
    private String name;
    private double score;

    // Vitals
    private int hp;
    private int maxHp;
    private int speed;    // base flee speed

    // Location
    private int currentPlanetIndex; // index into Map.planets()

    // Equipment
    private final Inventory inventory = new Inventory();

    public Player() {
        this.maxHp  = 100;
        this.hp     = 100;
        this.speed  = 10;
        this.score  = 0;
        this.currentPlanetIndex = 0;
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    // ─── Getters / Setters ──────────────────────────────────────────────────

    public String  getName()               { return name; }
    public void    setName(String name)    { this.name = name; }

    public int     getHp()                 { return hp; }
    public int     getMaxHp()              { return maxHp; }
    public boolean isAlive()               { return hp > 0; }

    public double  getScore()              { return score; }
    public void    addScore(double pts)    { this.score += pts; }

    public int     getSpeed()              { return speed; }
    public void    setSpeed(int speed)     { this.speed = speed; }

    public int     getCurrentPlanetIndex() { return currentPlanetIndex; }
    public void    setCurrentPlanetIndex(int i) { this.currentPlanetIndex = i; }

    public Inventory getInventory()        { return inventory; }

    /** Apply damage. Returns true if the player died. */
    public boolean takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        return hp == 0;
    }

    /** Heal the player (capped at maxHp). */
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    @Override
    public String toString() {
        return "Player{name='%s', hp=%d/%d, planet=%d, score=%.0f}"
            .formatted(name, hp, maxHp, currentPlanetIndex, score);
    }
}
