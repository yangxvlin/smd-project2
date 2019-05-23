package mycontroller.Strategy;

import mycontroller.MapRecorder;
import utilities.Coordinate;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:43
 * description:
 **/

public interface IStrategy {

    void updateCost(MapRecorder mapRecorder);

    Coordinate getNextCoordinate(MapRecorder map,
                                 Coordinate carPosition,
                                 float health,
                                 float fuel,
                                 boolean enoughParcel);
}