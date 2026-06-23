package com.lostinspace.model;

import java.io.Serializable;

/**
 * Top-level game state container.
 * Holds all mutable game data; passed by reference throughout the session.
 */
public class Game implements Serializable {

    public enum State { INTRO, PLAYING, WON, LOST }

    private String  name;           // save-file label
    private long    startTimeMs;    // System.currentTimeMillis() at game start
    private long    elapsedMs;      // accumulated play time (for pausing)

    private Player  player;
    private Map     map;
    private Fuel    fuel;

    private State   state;

    public Game() {
        this.state      = State.INTRO;
        this.startTimeMs = System.currentTimeMillis();
    }

    // ─── Getters / Setters ──────────────────────────────────────────────────

    public String  getName()            { return name; }
    public void    setName(String n)    { this.name = n; }

    public Player  getPlayer()          { return player; }
    public void    setPlayer(Player p)  { this.player = p; }

    public Map     getMap()             { return map; }
    public void    setMap(Map m)        { this.map = m; }

    public Fuel    getFuel()            { return fuel; }
    public void    setFuel(Fuel f)      { this.fuel = f; }

    public State   getState()           { return state; }
    public void    setState(State s)    { this.state = s; }

    /** Total play time in seconds (including paused elapsed time). */
    public double  getTotalTimeSeconds() {
        return (elapsedMs + (System.currentTimeMillis() - startTimeMs)) / 1000.0;
    }

    /** Snapshot elapsed time (call when the game is paused/saved). */
    public void snapshotTime() {
        elapsedMs += System.currentTimeMillis() - startTimeMs;
        startTimeMs = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Game{name='%s', state=%s, player=%s}".formatted(name, state, player);
    }
}
