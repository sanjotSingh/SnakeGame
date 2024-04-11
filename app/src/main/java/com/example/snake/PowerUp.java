package com.example.snake;
import android.graphics.Point;

public class PowerUp extends Object {
    // Define properties specific to power-ups
    private int type; // Type o power-up (e.g., speed boost, invincibility, etc.)
    private boolean active; // Whether the power-up is currently active

    // Constructor
    public PowerUp(Point location, int type) {
        super();
        this.location = location;
        this.type = type;
        this.active = false;
    }

    // Method to activate the power-up
    public void activate() {
        // Implement activation logic here
        this.active = true;
    }

    // Method to deactivate the power-up
    public void deactivate() {
        // Implement deactivation logic here
    }

    // Method to check if the power-up is active
    public boolean isActive() {
        return true;
    }

    // Method to apply the effect of the power-up
    public void applyEffect(Snake snake) {
        // Implement effect logic here, e.g., increase speed, extend length, etc.
    }
}
