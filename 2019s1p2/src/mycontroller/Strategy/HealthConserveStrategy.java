package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.ISearchAlgorithm;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:42
 *
 * description : This class is responsible for choosing net Coordinate to go in a health conserve manner
 **/

public class HealthConserveStrategy implements IStrategy {
    /*The HashMap for looking up strategies*/
    private HashMap<StrategyType, IStrategy> strategies;

    /**
     * The construct for HealthConserveStrategy.
     *
     */
    public HealthConserveStrategy() {
        this.strategies = new HashMap<>();

    }

    /**
     * This method is responsible for selecting the next Coordinate to go in a health conserve manner
     *
     * @param map             : The map explored by the car.
     * @param carPosition     : The current coordinate of the car.
     * @param healthUsage     : The current health usage of the car
     * @param health          : The current health of the car
     * @param fuelCost        : The current fuel cost of the car
     * @param speed           : The current speed of the car.
     * @param movingDirection : The direction that the car is currently moving at
     * @param enoughParcel    : A boolean that indicates whether have picked enough parcels.
     * @return : The Coordinate to go.
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
        Coordinate next = null;
        /* go to parcels */
        if (!enoughParcel) {
            /*selecting the next Coordinate to the parcel*/
            next = strategies.get(StrategyType.PICKUP)
                    .getNextPath(map, carPosition, healthUsage, health, fuelCost, speed, movingDirection, enoughParcel);
            /* go to finish */
        } else {
            /*selecting the next Coordinate to the exit*/
            next = strategies.get(StrategyType.EXIT)
                    .getNextPath(map, carPosition, healthUsage, health, fuelCost, speed, movingDirection, enoughParcel);
        }

        /* still no where to go, so go to closest unexplored */
        if (next == null) {
            /*selecting the next Coordinate to explore*/
            next = strategies.get(StrategyType.EXPLORE)
                    .getNextPath(map, carPosition, healthUsage, health, fuelCost, speed, movingDirection, enoughParcel);
        }

        /* no where to go, so go to closest health/water */
        if (next == null) {
            /*selecting the next Coordinate to heal tile*/
            next = strategies.get(StrategyType.HEAL)
                    .getNextPath(map, carPosition, healthUsage, health, fuelCost, speed, movingDirection, enoughParcel);
        }

        /*If the next Coordinate to go is null, stay in current position*/
        if (next == null) {
            next = carPosition;
        }

        /*return the next Coordinate to go.*/
        return next;
    }

    /**
     * This method is responsible for registering strategy into its HashMap.
     *
     * @param strategyType : The strategy for the strategy.
     * @param strategy     : The strategy for adding.
     */
    @Override
    public void registerIStrategy(StrategyType strategyType, IStrategy strategy) {
        this.strategies.put(strategyType, strategy);
    }

    /**
     * don't add graph search algorithm because this strategy doesn't need it
     *
     * @param searchAlgorithm : graph algorithm used to search next coordinate to drive to
     */
    @Override
    public void registerISearchAlgorithm(ISearchAlgorithm searchAlgorithm) {
        /* do nothing because this doesn't require graph search algorithm */
    }

    /**
     * This class is used for searching healing tile
     */
    static class HealComparator implements Comparator<Node> {

        /**
         * @param o1 : The first Node
         * @param o2 : The second Node
         * @return : The result of comparison
         */
        @Override
        public int compare(Node o1, Node o2) {
            assert(o1.getHealthUsage() == o2.getHealthUsage());

            if (o1.getHealth() < o2.getHealth()) {
                return -1;
            } else if (o1.getFuelCost() < o2.getFuelCost()) {
                return 1;
            } else if (o1.getFuelCost() > o2.getFuelCost()) {
                return -1;
            } else if (o1.getHealth() > o2.getHealth()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * This class is responsible for comparing Node in a fuel health manner
     */
    static class HealthComparator implements Comparator<Node> {
        /**
         * @param o1 : The first Node
         * @param o2 : The second Node
         * @return : The result of comparison
         */
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getHealthUsage() > o2.getHealthUsage()) {
                return -1;
            } else if (o1.getFuelCost() < o2.getFuelCost()) {
                return 1;
            } else if (o1.getFuelCost() > o2.getFuelCost()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
