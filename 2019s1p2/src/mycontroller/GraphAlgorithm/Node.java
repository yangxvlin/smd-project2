package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-23 15:51
 * description: Class to represent a node in the graph search algorithm,
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
     * cost for the car at the node
     */
    private float fuel;

    private float maxHealth;

    private WorldSpatial.Direction direction;

    /**
     * @param c :      current coordinate
     * @param health : the remaining health at current coordinate
     * @param fuel :   the remaining fuel at current coordinate
     */
    public Node(Coordinate c, float health, float fuel, float maxHealth, WorldSpatial.Direction direction) {
        this.c = c;
        this.health = health;
        this.fuel = fuel;
        this.maxHealth = maxHealth;
        this.direction = direction;
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

    public ArrayList<Node> getNeighbors(MapRecorder map,
                                        ArrayList<Coordinate> adjacentCoordinates) {
        /*
        * up
        * down
        * left
        * right
        * self
        * */


        for (Coordinate c : adjacentCoordinates){
            // get the tile type the adjacent tile type
            ITileAdapter.TileType adjacentTileType = map.getTileAdapter(c).getType();
            world.WorldSpatial.Direction adjacentDirection = nextDirection(c);


        }

        // move toward reverse direction
        /* update c, health(negative * 2, ice * 2, otherwise + health delta), fuel + 1, max health + positive(health delta, 0), direction */
        return null;
    }

    /**
     *
     * Decide whether the car is moving backward
     *
     * */
    private boolean isMoveBackward(WorldSpatial.Direction nextDirection, Coordinate adjacentCoordinate){
        if (this.direction == nextDirection){
            if (this.direction == WorldSpatial.Direction.EAST){
                if (this.c.y == adjacentCoordinate.y && this.c.x == adjacentCoordinate.x + 1){
                    return true;
                }
            }else if (this.direction == WorldSpatial.Direction.WEST){
                if (this.c.y == adjacentCoordinate.y && this.c.x == adjacentCoordinate.x -1){
                    return true;
                }
            }else if (this.direction == WorldSpatial.Direction.NORTH){
                if (this.c.x == adjacentCoordinate.x && this.c.y == adjacentCoordinate.y + 1){
                    return true;
                }
            }else if (this.direction == WorldSpatial.Direction.NORTH){
                if (this.c.x == adjacentCoordinate.x && this.c.y == adjacentCoordinate.y -1){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param adj : adjacent tile's coordinate
     * @return direction based on move from current coordinate to adjacent coordinate
     */
    private WorldSpatial.Direction nextDirection(Coordinate adj){
        switch (direction) {
            case NORTH:
                if (c.x > adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.WEST;
                } else if (c.x < adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.EAST;
                } else if (c.y > adj.y) {
                    return WorldSpatial.Direction.NORTH;
                } else if (c.y < adj.y) {
                    return WorldSpatial.Direction.NORTH;
                }

            case SOUTH:
                if (c.x > adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.WEST;
                } else if (c.x < adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.EAST;
                } else if (c.y > adj.y) {
                    return WorldSpatial.Direction.SOUTH;
                } else if (c.y < adj.y) {
                    return WorldSpatial.Direction.SOUTH;
                }

            case EAST:
                if (c.x > adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.EAST;
                } else if (c.x < adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.EAST;
                } else if (c.y > adj.y) {
                    return WorldSpatial.Direction.SOUTH;
                } else if (c.y < adj.y) {
                    return WorldSpatial.Direction.NORTH;
                }

            case WEST:
                if (c.x > adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.WEST;
                } else if (c.x < adj.x) {
                    assert(c.y == adj.y);
                    return WorldSpatial.Direction.WEST;
                } else if (c.y > adj.y) {
                    return WorldSpatial.Direction.NORTH;
                } else if (c.y < adj.y) {
                    return WorldSpatial.Direction.SOUTH;
                }
        }

        System.out.println("Invalid move in direction!");
        System.exit(1);
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
