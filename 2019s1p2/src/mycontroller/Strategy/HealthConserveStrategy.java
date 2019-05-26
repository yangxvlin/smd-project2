package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
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

//    private Comparator<Node> healthComparator;

    private HashMap<StrategyType, IStrategy> strategies;

//    public HealthConserveStrategy(Comparator<Node> healthComparator) {
//        this.healthComparator = healthComparator;
//        this.strategies = new HashMap<>();
//
//    }

    public HealthConserveStrategy() {
        this.strategies = new HashMap<>();

    }

    @Override
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float maxHealth,
                                        float health,
                                        float fuel,
                                        boolean enoughParcel) {
        Coordinate next = null;

        System.out.println(strategies.get(StrategyType.EXIT)
                .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel));
        if (strategies.get(StrategyType.EXIT)
                .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel) != null) {
            /* go to parcels */
            if (!enoughParcel) {
                System.out.println("parcels ");
                next = strategies.get(StrategyType.PICKUP)
                        .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
            /* go to finish */
            } else {
                System.out.println("finish: ");
                next = strategies.get(StrategyType.EXIT)
                        .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
            }

            /* still no where to go, so go to closest unexplored */
            if (next == null) {
                System.out.println("explore: ");
                next = strategies.get(StrategyType.EXPLORE)
                        .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
            }
        }

        /* no where to go, so go to closest health/water */
        if (next == null) {
            System.out.println("healing: ");
            next = strategies.get(StrategyType.HEAL)
                    .getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
        }

        if (next == null) {
            System.out.println("random: ");
            IStrategy random = new RandomMoveStrategy();
            next = random.getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
        }

        /* debug */
        if (next == null) {
            System.out.println("Error! No next searched!");
        }

        return next;
    }

    public void registerIStrategy(StrategyType strategyType, IStrategy strategy) {
        this.strategies.put(strategyType, strategy);
    }

    static class HealthComparator2 implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            assert(o1.getMaxHealth() == o2.getMaxHealth());

            if (o1.getHealth() > o2.getHealth()) {
                return 1;
            } else if (o1.getHealth() < o2.getHealth()) {
                return -1;
            } else if (o1.getFuel() < o2.getFuel()) {
                return -1;
            } else if (o1.getFuel() > o2.getFuel()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    static class HealthComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            assert(o1.getMaxHealth() == o2.getMaxHealth());

            if (o1.getMaxHealth() > o2.getMaxHealth()) {
                return -1;
            } else if (o1.getMaxHealth() < o2.getMaxHealth()) {
                return 1;
            } else if (o1.getFuel() < o2.getFuel()) {
                return -1;
            } else if (o1.getFuel() > o2.getFuel()) {
                return 1;
            } else {
                return 0;
            }

//            if (o1.getMaxHealth() > o2.getMaxHealth()) {
//                return -1;
//            } else if (o1.getMaxHealth() < o2.getMaxHealth()) {
//                return 1;
//            } else if (o1.getHealth() > o2.getHealth()) {
//                return 1;
//            } else if (o1.getHealth() < o2.getHealth()) {
//                return -1;
//            } else if (o1.getFuel() < o2.getFuel()) {
//                return -1;
//            } else if (o1.getFuel() > o2.getFuel()) {
//                return 1;
//            } else {
//                return 0;
//            }
        }
    }
}
