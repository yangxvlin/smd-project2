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
    private float fuelCost;

    private float maxHealth;

    private WorldSpatial.Direction movingDirection;

    private float speed;

    private static final float FORWARD_VELOCITY = 1;

    /**
     * @param c :      current coordinate
     * @param health : the remaining health at current coordinate
     * @param fuelCost :   the remaining fuel at current coordinate
     */
    public Node(Coordinate c, float health, float fuelCost, float maxHealth, float speed, WorldSpatial.Direction movingDirection) {
        this.c         = c;
        this.health    = health;
        this.fuelCost  = fuelCost;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.movingDirection = movingDirection;
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

    public float getFuelCost() {
        return fuelCost;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public ArrayList<Node> getNeighbors(MapRecorder map,
                                        ArrayList<Coordinate> adjacentCoordinates) {
        ArrayList<Node> res = new ArrayList<>();
        /*
        * up
        * down
        * left
        * right
        * self
        * */

        //

        for (Coordinate adj : adjacentCoordinates) {
            // get the tile type the adjacent tile type
            ITileAdapter.TileType currentTileType = map.getTileAdapter(c).getType();
            world.WorldSpatial.Direction nextMovingDirection = nextMoveDirection(adj);

            // TODO Unexplored ROAD cost = 0 now
            float adjHealth = getHealth() + MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(adj).getType());
            // TODO a map with values = 1 or a constant 1?
            float adjFuelCost = getFuelCost() + 1;
            float adjMaxHealth = getMaxHealth();
            if (MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(adj).getType()) > 0) {
                adjMaxHealth += MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(adj).getType());
            }

            if (isNeedBrake(nextMovingDirection)) {
                /* braked tile's effect */
                switch (currentTileType) {
                    case HEALTH:
                        adjHealth += MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(c).getType());
                        adjMaxHealth += MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(c).getType());
                        break;

                    case LAVA:
                        adjHealth += MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(c).getType());
                        break;
                }
            }

            res.add(new Node(adj, adjHealth, adjFuelCost, adjMaxHealth, FORWARD_VELOCITY, nextMovingDirection));
        }

        // move toward reverse direction
        /* update c, health(negative * 2, ice * 2, otherwise + health delta), fuel + 1, max health + positive(health delta, 0), direction */
        return res;
    }

    /**
     *
     * Decide whether the car need to brake.
     *
     * */
    private boolean isNeedBrake(WorldSpatial.Direction nextMovingDirection){
        // if the car has already stop then no need brake.
        if (speed == 0){
            return false;
        }

        // if the car want to move to the reverse direction, then brake is needed.
        switch (movingDirection){
            case EAST:
                if (nextMovingDirection == WorldSpatial.Direction.WEST){
                    return true;
                }
            case WEST:
                if (nextMovingDirection == WorldSpatial.Direction.EAST){
                    return true;
                }
            case NORTH:
                if (nextMovingDirection == WorldSpatial.Direction.SOUTH){
                    return true;
                }
            case SOUTH:
                if (nextMovingDirection == WorldSpatial.Direction.NORTH){
                    return true;
                }
        }

        return false;
    }

    /**
     * @param adj : adjacent tile's coordinate
     * @return direction based on move from current coordinate to adjacent coordinate
     */
    private WorldSpatial.Direction nextMoveDirection(Coordinate adj){
        if (c.x > adj.x) {
            return WorldSpatial.Direction.WEST;
        } else if (c.x < adj.x) {
            return WorldSpatial.Direction.EAST;
        } else if (c.y < adj.y) {
            return WorldSpatial.Direction.NORTH;
        } else if (c.y > adj.y) {
            return WorldSpatial.Direction.SOUTH;
        }

        System.out.println("Invalid move in direction!");
        System.exit(1);
        return null;
    }

    /**
     * @return String representation of the node
     */
    @Override
    public String toString() {
        return "(" + c.toString() + "), " +
                "health: " + Float.toString(health) + ", " +
                "fuelCost: " + Float.toString(fuelCost) + ", " +
                "speed: " + Float.toString(speed) + "(" + movingDirection + ")";
    }
}
