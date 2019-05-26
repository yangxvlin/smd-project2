package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;

import java.util.HashMap;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:43
 * description:
 **/

public interface IStrategy {

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
}