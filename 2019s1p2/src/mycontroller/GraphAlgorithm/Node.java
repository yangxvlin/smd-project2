package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-23 15:51
 * description:
 **/

// Class to represent a node in the graph
public class Node {
    private Coordinate c;
    private float health;
    private float fuel;


    public Node(Coordinate c, float health, float fuel) {
        this.c = c;
        this.health = health;
        this.fuel = fuel;
    }

    public Coordinate getC() {
        return c;
    }

    public float getHealth() {
        return health;
    }

    public float getFuel() {
        return fuel;
    }

    @Override
    public String toString() {
        return "(" + c.toString() + ") health: " + Float.toString(health) + " fuel:" + Float.toString(fuel);
    }
}
