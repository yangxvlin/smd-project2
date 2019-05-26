package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-23 15:51
 * description: Class to represent a node in the graph search algorith,
 **/

public class Node {
    /**
     * car's current location
     */
    private Coordinate c;

    /**
     * remaining health for the car at the node
     */
    private float health;

    /**
     * remaining fuel for the car at the node
     */
    private float fuel;

    private float maxHealth;

    /**
     * @param c :      current coordinate
     * @param health : the remaining health at current coordinate
     * @param fuel :   the remaining fuel at current coordinate
     */
    public Node(Coordinate c, float health, float fuel, float maxHealth) {
        this.c = c;
        this.health = health;
        this.fuel = fuel;
        this.maxHealth = maxHealth;
    }

    /**
     * @return arrived coordinate
     */
    public Coordinate getC() {
        return c;
    }

    /**
     * @return remaining health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @return remaining fuel
     */
    public float getFuel() {
        return fuel;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return String representation of the node
     */
    @Override
    public String toString() {
        return "(" + c.toString() + "), " +
                "health: " + Float.toString(health) + ", " +
                "fuel: " + Float.toString(fuel);
    }
}
