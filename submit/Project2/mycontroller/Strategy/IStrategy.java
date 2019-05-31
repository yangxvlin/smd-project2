package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.ISearchAlgorithm;
import mycontroller.GraphAlgorithm.ISearchResult;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 1:43
 * description: This interface defines APIs for strategies used for getting next step coordinate.
 **/

public interface IStrategy {
    /**
     * if car's health is less then 0.5, then the car game over
     */
    float GAME_OVER_HEALTH = 0.5f;

    /**
     *
     * PICKUP refers to a strategy for picking up a parcel
     * EXIT refers to a strategy for going to the finish place
     * EXPLORE refers to a strategy for going to unexplored places
     * HEAL refers to a strategy for going to healing tiles.
     *
     */
    enum StrategyType {PICKUP, EXIT, EXPLORE, HEAL}


    /**
     *  This methods is responsible for finding the next coordinate to go depends on given information.
     *
     * @param map : The map explored by the car.
     * @param carPosition : The current coordinate of the car.
     * @param healthUsage : The current healthUsage of the car
     * @param health : The current health of the car
     * @param fuelCost : The current fuel cost of the car
     * @param speed : The current speed of the car.
     * @param movingDirection : The direction that the car is currently moving at
     * @param enoughParcel : A boolean that indicates whether have picked enough parcels.
     * @return : The coordinate to go.
     */
    Coordinate getNextPath(MapRecorder map,
                           Coordinate carPosition,
                           float healthUsage,
                           float health,
                           float fuelCost,
                           float speed,
                           WorldSpatial.Direction movingDirection,
                           boolean enoughParcel);

    /**
     * This method is responsible for determine whether a destination is reachable or not.
     *
     * @param costSoFar : A HashMap with Coordinate as the key, Node as the value, which is used to look up the Node
     *                  for the destination.
     * @param destination : The coordinate of the destination.
     * @return : A boolean, false for unreachable, true for reachable.
     */
    default boolean isPossible(HashMap<Coordinate, Node> costSoFar,
                               Coordinate destination) {
        /* those destination with remaining health more than 0.5 is possible. */
        if (costSoFar.containsKey(destination) &&
                costSoFar.get(destination).getHealth() >= GAME_OVER_HEALTH) {
            return true;
        }
        return false;
    }

    /**
     * This method is responsible for choosing the best path depends on the given comparator.
     *
     * @param destinations : An ArrayList of Coordinates which represents destinations.
     * @param searchResult : The search result from Dijkstra algorithm.
     * @param comparator : The given comparator for ordering destinations.
     * @param healthUsage : The current healthUsage of the car.
     * @return : A Coordinate to go.
     */
    default Coordinate choosePath(ArrayList<Coordinate> destinations,
                                  ISearchResult searchResult,
                                  Comparator<Node> comparator,
                                  float healthUsage) {
        /* create a Node variable for storing the node to go. */
        Node nextNode = new Node(null, Float.MIN_VALUE, Float.MAX_VALUE, healthUsage, 0, null); // 0 has no meaning, just a random input


        /* go to closest reachable parcel */
        /* if the destination is reachable */
        for (Coordinate destination : destinations)
            if (isPossible(searchResult.getCostSoFar(), destination)) {
                /* get the Node of that destination */
                Node newNode = new Node(searchResult.getNext(destination),
                        searchResult.getCostSoFar().get(destination).getHealth(),
                        searchResult.getCostSoFar().get(destination).getFuelCost(),
                        healthUsage,
                        0,
                        null); // 0 has no meaning, just a random input
                /*
                 Compare two destinations by the given comparator, if the newNode is better,
                 replace the nextNode with newNode
                */
                if (comparator.compare(nextNode, newNode) == -1) {
                    nextNode = newNode;
                }
            }

        /* return the next Coordinate to go. */
        return nextNode.getC();
    }

    /**
     * This method is responsible for registering one strategy into HashMap.
     *
     * @param strategyType : The strategy for the strategy.
     * @param strategy : The strategy for adding.
     */
    void registerIStrategy(StrategyType strategyType, IStrategy strategy);

    /**
     * add graph search algorithm to the car drive strategy
     *
     * @param searchAlgorithm : graph algorithm used to search next coordinate to drive to
     */
    void registerISearchAlgorithm(ISearchAlgorithm searchAlgorithm);
}
