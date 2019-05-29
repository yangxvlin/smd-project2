package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraResult;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 14:15
 *
 * description : This class is responsible for navigating the car to the Exit place.
 **/

public class ExitStrategy implements IStrategy {
    /* The comparator for choosing path */
    private Comparator<Node> comparator;

    /**
     * The constructor for ExitStrategy
     *
     * @param comparator : The comparator used for choosing path which is defined by the user.
     */
    public ExitStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method is responsible for finding the next Coordinate on the way to the Exit place.
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
        /* get Coordinates of all Exit tile */
        ArrayList<Coordinate> finishes = map.getCoordinates(ITileAdapter.TileType.FINISH);
        assert(!finishes.isEmpty());

        /* search the map by Dijkstra within Explored tiles, which is used for determining whether Exit tiles are reachable or not. */
        DijkstraResult res = Dijkstra.dijkstra(map,
                carPosition,
                healthUsage,
                health,
                fuelCost,
                speed,
                movingDirection,
                comparator,
                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));

        /* Choose the best Coordinate on the path to the Exit tile depends on the given comparator. */
        Coordinate next = choosePath(finishes, res, comparator, healthUsage);

        if (next == null) {
            /* If there is no way to Exit in current situation, search the map within both Explored and Unexplored tiles. */
            res = Dijkstra.dijkstra(map,
                    carPosition,
                    healthUsage,
                    health,
                    fuelCost,
                    speed,
                    movingDirection,
                    comparator,
                    new ArrayList<>(Arrays.asList(TileStatus.UNEXPLORED, TileStatus.EXPLORED)));
            next = choosePath(finishes, res, comparator, healthUsage);
        }


        /* return thr Coordinate to go, if there is no way to Exit tiles, the Coordinate would be null. */
        return next;
    }

    /**
     * This method does nothing, since there is no need to register.
     *
     * @param strategyType : The strategy for the strategy.
     * @param strategy     : The strategy for adding.
     */
    @Override
    public void registerIStrategy(StrategyType strategyType, IStrategy strategy) {
        // do nothing, since there is no need to registering.
    }
}
