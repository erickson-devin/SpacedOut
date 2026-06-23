package com.lostinspace.model;

import java.io.Serializable;

/**
 * Tracks the spaceship's fuel supply.
 * Mutable — fuel is consumed by travel and replenished by mining.
 */
public class Fuel implements Serializable {

    public static final double MAX_CAPACITY = 100.0;
    public static final double LOW_THRESHOLD = 20.0;  // warn below this %

    private double currentAmount;
    private final double capacity;

    public Fuel() {
        this.capacity      = MAX_CAPACITY;
        this.currentAmount = 50.0; // start at 50%
    }

    public Fuel(double startAmount) {
        this.capacity      = MAX_CAPACITY;
        this.currentAmount = Math.max(0, Math.min(MAX_CAPACITY, startAmount));
    }

    /** Add fuel (capped at capacity). Returns actual amount added. */
    public double add(double amount) {
        double before = currentAmount;
        currentAmount = Math.min(capacity, currentAmount + amount);
        return currentAmount - before;
    }

    /** Consume fuel. Returns false if insufficient fuel. */
    public boolean consume(double amount) {
        if (currentAmount < amount) return false;
        currentAmount -= amount;
        return true;
    }

    public double getCurrent()   { return currentAmount; }
    public double getCapacity()  { return capacity; }

    /** Fuel percentage (0.0 – 100.0). */
    public double getPercent() {
        return (currentAmount / capacity) * 100.0;
    }

    /** True if fuel is dangerously low. */
    public boolean isLow() {
        return getPercent() < LOW_THRESHOLD;
    }

    @Override
    public String toString() {
        return "Fuel[%.1f / %.1f  (%.1f%%)]".formatted(currentAmount, capacity, getPercent());
    }
}
