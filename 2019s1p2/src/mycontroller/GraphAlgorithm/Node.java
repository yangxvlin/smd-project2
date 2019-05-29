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

    /**
     * car's current health usage
     */
    private float healthUsage;

    /**
     * car's current moving direction
     */
    private WorldSpatial.Direction movingDirection;

    /**
     * car's current speed
     */
    private float speed;

    /**
     * car's moving velocity
     */
    private static final float FORWARD_VELOCITY = 1;

    /**
     * constant car moving fuel cost
     */
    private static final int FUEL_COST = 1;

    /**
     * @param c : current coordinate
     * @param health : the remaining health at current coordinate
     * @param fuelCost : the remaining fuel at current coordinate
     * @param healthUsage : car's current used health amount
     * @param speed : car's running speed
     * @param movingDirection : car's moving toward direction
     */
    public Node(Coordinate c, float health, float fuelCost, float healthUsage, float speed, WorldSpatial.Direction movingDirection) {
        this.c               = c;
        this.health          = health;
        this.fuelCost        = fuelCost;
        this.healthUsage     = healthUsage;
        this.speed           = speed;
        this.movingDirection = movingDirection;
    }

    /**
     * @return : arrived coordinate
     */
    public Coordinate getC() {
        return c;
    }

    /**
     * @return : remaining health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @return : fuel usage
     */
    public float getFuelCost() {
        return fuelCost;
    }

    /**
     * @return : health usage
     */
    public float getHealthUsage() {
        return healthUsage;
    }

    /**
     * @param map : mapRecorder for the world
     * @param adjacentCoordinates : current car's coordinate's adjacent coordinates
     * @return : list of allowable adjacent nodes
     */
    public ArrayList<Node> getNeighbors(MapRecorder map,
                                        ArrayList<Coordinate> adjacentCoordinates) {
        ArrayList<Node> res = new ArrayList<>();

        for (Coordinate adj : adjacentCoordinates) {
            /* get the tile type the adjacent tile type */
            ITileAdapter.TileType currentTileType = map.getTileAdapter(c).getType();
            world.WorldSpatial.Direction nextMovingDirection = nextMoveDirection(adj);

            /* update cost to neighbor */
            float adjHealth = getHealth() + MapRecorder.TILE_HEALTH_COST_MAP.get(map.getTileAdapter(adj).getType());
            float adjFuelCost = getFuelCost() + FUEL_COST;
            float adjMaxHealth = getHealthUsage();
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

        return res;
    }

    /**
     * @param nextMovingDirection : car's next time frame moving direction
     * @return : true if move backward needs a brake
     */
    private boolean isNeedBrake(WorldSpatial.Direction nextMovingDirection){
        /* if the car has already stop then no need brake. */
        if (speed == 0){
            return false;
        }

        /* if the car want to move to the reverse direction, then brake is needed. */
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
                "health: " + Float.toString(health) +"(" + healthUsage + ")" + ", " +
                "fuelCost: " + Float.toString(fuelCost) + ", " +
                "speed: " + Float.toString(speed) + "(" + movingDirection + ")";
    }
}
