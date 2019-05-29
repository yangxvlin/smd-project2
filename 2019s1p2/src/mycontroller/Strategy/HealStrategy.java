package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 19:41
 *
 * description : This class is responsible for navigating the car to explore unexplored tiles.
 *
 **/

public class HealStrategy implements IStrategy {
    /* The comparator for choosing path */
    private Comparator<Node> comparator;

    /**
     * The constructor for HealStrategy
     *
     * @param comparator : The comparator used for choosing path which is defined by the user.
     */
    public HealStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method is responsible for finding the next Coordinate on the way to one healing tile.
     *
     * @param map             : The map explored by the car.
     * @param carPosition     : The current coordinate of the car.
     * @param healthUsage     : The current healthUsage of the car
     * @param health          : The current health of the car
     * @param fuelCost        : The current fuel cost of the car
     * @param speed           : The current speed of the car.
     * @param movingDirection : The direction that the car is currently moving at
     * @param enoughParcel    : A boolean that indicates whether have picked enough parcels.
     * @return : The Coordinate for the car to go.
     */
    @Override
    public Coordinate getNextPath(MapRecorder map,
                                  Coordinate carPosition,
                                  float healthUsage,
                                  float health,
                                  float fuelCost,
                                  float speed,
                                  WorldSpatial.Direction movingDirection,
                                  boolean enoughParcel) {
        /* get Coordinates of all of healing tiles, include Water and Health */
        ArrayList<Coordinate> heals = map.getCoordinates(ITileAdapter.TileType.WATER);
        heals.addAll(map.getCoordinates(ITileAdapter.TileType.HEALTH));

        /* If there is no healing tiles, then return null, since no tile to heal */
        if (heals.isEmpty()) {
            return null;
        }

        /*
        search the map by Dijkstra within Explored tiles,
         which is used for determining whether the healing tiles are reachable or not.
        */
        DijkstraPair res = Dijkstra.dijkstra(map,
                carPosition,
                healthUsage,
                health,
                fuelCost,
                speed,
                movingDirection,
                comparator,
                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));
        /* return the Coordinates fot the car to go. */
        return choosePath(heals, res, comparator, healthUsage);
    }
}
