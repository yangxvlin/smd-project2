package mycontroller.Strategy;

import mycontroller.MapRecorder;
import utilities.Coordinate;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:42
 * description:
 **/

public class HealthConserveStrategy implements IStrategy {

    @Override
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float health,
                                        float fuel,
                                        boolean enoughParcel) {
        return null;
    }

//    @Override
//    public void registerIStrategy(IStrategy iStrategy) {
//
//    }
}
