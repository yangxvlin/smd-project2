package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraResult;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 15:01
 *
 * description : This class is responsible for navigating the car to explore unexplored tiles.
 **/

public class ExploreStrategy implements IStrategy {
    /* The comparator for choosing path */
    private Comparator<Node> comparator;

    /**
     * The constructor for ExploreStrategy
     *
     * @param comparator : The comparator used for choosing path which is defined by the user.
     */
    public ExploreStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method is responsible for finding the next Coordinate on the way to explore the map.
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
        /*
         the out most tiles are the explored tiles that connects with unexplored tiles
         get Coordinates of all of out most tiles
        */
        ArrayList<Coordinate> outMostExplored = new ArrayList<>(map.getOutMostExploredCoordinates());

        /* If there is no out most tiles, then return null, since no tile to explore */
        if (outMostExplored.isEmpty()) {
            return null;
        }
        /*
        search the map by Dijkstra within Explored tiles,
         which is used for determining whether the out most tiles are reachable or not.
        */
        DijkstraResult res = Dijkstra.dijkstra(map,
                carPosition,
                healthUsage,
                health,
                fuelCost,
                speed,
                movingDirection,
                comparator,
                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));

        /* return the Coordinates fot the car to go. */
        return choosePath(outMostExplored, res, comparator, healthUsage);
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
