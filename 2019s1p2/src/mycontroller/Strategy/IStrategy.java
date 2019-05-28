package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:43
 * description:
 **/

public interface IStrategy {

    enum StrategyType {PICKUP, EXIT, EXPLORE, HEAL}

//    void updateCost(MapRecorder mapRecorder);

    Coordinate getNextCoordinate(MapRecorder map,
                                 Coordinate carPosition,
                                 float maxHealth,
                                 float health,
                                 float fuelCost,
                                 float speed,
                                 WorldSpatial.Direction movingDirection,
                                 boolean enoughParcel);

    default boolean isPossible(HashMap<Coordinate, Node> costSoFar,
                               Coordinate destination) {
        /* 0.5 see Car.java line 100 */
//        System.out.println(destination + " " +
//                Boolean.toString(costSoFar.containsKey(destination)));
        if (costSoFar.containsKey(destination) &&
                costSoFar.get(destination).getHealth() >= 0.5) {
            return true;
        }
        return false;
    }

    default Coordinate choosePath(ArrayList<Coordinate> destinations,
                                  DijkstraPair searchResult,
                                  Comparator<Node> comparator,
                                  float maxHealth) {
        Node nextNode = new Node(null, Float.MIN_VALUE, Float.MAX_VALUE, maxHealth, 0, null);
//        System.out.println(Arrays.toString(searchResult.getCameFrom().keySet().toArray()));
//        System.out.println(Arrays.toString(searchResult.getCostSoFar().keySet().toArray()));
        for (Coordinate c: searchResult.getCameFrom().keySet()) {
            System.out.println(c.toString() + " " + searchResult.getCameFrom().get(c) + " " + searchResult.getCostSoFar().get(c));
        }

        /* go to closest reachable parcel */
        for (Coordinate destination : destinations) {
//            System.out.println(destination + " " + Arrays.toString(searchResult.getCameFrom().keySet().toArray()));

            if (isPossible(searchResult.getCostSoFar(), destination)) {
                Node newNode = new Node(searchResult.getNext(destination),
                        searchResult.getCostSoFar().get(destination).getHealth(),
                        searchResult.getCostSoFar().get(destination).getFuelCost(),
                        maxHealth,
                        0,
                        null);

//                System.out.println("<><><><>");
                if (comparator.compare(nextNode, newNode) == -1) {
                    nextNode = newNode;
                }
            }

//            System.out.println(nextNode);
        }

        return nextNode.getC();
    }
}