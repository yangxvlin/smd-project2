package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:42
 * description:
 **/

public class HealthConserveStrategy implements IStrategy {

    private Comparator<Node> healthComparator;

    private HashMap<StrategyType, IStrategy> strategies;

    public HealthConserveStrategy(Comparator<Node> healthComparator) {
        this.healthComparator = healthComparator;
        this.strategies = new HashMap<>();
    }

    @Override
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float health,
                                        float fuel,
                                        boolean enoughParcel) {
        return null;
    }
}
