package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:43
 * description:
 **/

public interface IStrategy {

    enum StrategyType {PICKUP, EXIT, EXPLORE}

//    void updateCost(MapRecorder mapRecorder);

    Coordinate getNextCoordinate(MapRecorder map,
                                 Coordinate carPosition,
                                 float health,
                                 float fuel,
                                 boolean enoughParcel);

    default boolean isPossible(HashMap<Coordinate, Node> costSoFar,
                               Coordinate destination) {
        /* 0.5 see Car.java line 100 */
        if (costSoFar.containsKey(destination) &&
                costSoFar.get(destination).getHealth() >= 0.5) {
            return true;
        }
        return false;
    }

    default Coordinate choosePath(ArrayList<Coordinate> destinations,
                                  DijkstraPair searchResult,
                                  Comparator<Node> comparator) {
        Node nextNode = new Node(null, Float.MIN_VALUE, Float.MIN_VALUE);
        /* go to closest reachable parcel */
        for (Coordinate destination : destinations) {

            if (isPossible(searchResult.getCostSoFar(), destination)) {
                Node newNode = new Node(searchResult.getNext(destination),
                        searchResult.getCostSoFar().get(destination).getHealth(),
                        searchResult.getCostSoFar().get(destination).getFuel());

                if (comparator.compare(nextNode, newNode) == -1) {
                    nextNode = newNode;
                }
            }
        }

        return nextNode.getC();
    }
}