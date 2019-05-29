package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:42
 *
 * description :
 **/

public class FuelConserveStrategy implements IStrategy {
//    private Comparator<Node> fuelComparator;

    private HashMap<StrategyType, IStrategy> strategies;

//    public FuelConserveStrategy(Comparator<Node> fuelComparator) {
//        this.fuelComparator = fuelComparator;
//        this.strategies = new HashMap<>();
//    }

    public FuelConserveStrategy() {
        this.strategies = new HashMap<>();
    }

    @Override
    public Coordinate getNextPath(MapRecorder map,
                                  Coordinate carPosition,
                                  float maxHealth,
                                  float health,
                                  float fuelCost,
                                  float speed,
                                  WorldSpatial.Direction movingDirection,
                                  boolean enoughParcel) {
        Coordinate next;
        /* go to parcels */
        if (!enoughParcel) {
            System.out.println("Parcels: ");
            next = strategies.get(StrategyType.PICKUP)
                                .getNextPath(map, carPosition, maxHealth, health, fuelCost, speed, movingDirection, enoughParcel);
//            next = choosePath(map, carPosition, health, fuel, ITileAdapter.TileType.PARCEL);
        /* go to finish */
        } else {
            System.out.println("Finishs");
            next = strategies.get(StrategyType.EXIT)
                                .getNextPath(map, carPosition, maxHealth, health, fuelCost, speed, movingDirection, enoughParcel);
//            next = closestPath(map, carPosition, health, fuel, map.getCoordinates(ITileAdapter.TileType.FINISH),
//                    new ArrayList<>(Arrays.asList(TileStatus.UNEXPLORED, TileStatus.EXPLORED)));
        }

        /* still no where to go, so go to closest unexplored */
        if (next == null) {
            System.out.println("Unexplored: ");
            next = strategies.get(StrategyType.EXPLORE)
                                .getNextPath(map, carPosition, maxHealth, health, fuelCost, speed, movingDirection, enoughParcel);
//            next = choosePath(map, carPosition, health, fuel, TileStatus.UNEXPLORED);
        }

//        if (health <= 20) {
//            /* no where to go, so go to closest health/water */
//            if (next == null) {
//                next = choosePath(map, carPosition, health, fuel, ITileAdapter.TileType.WATER, ITileAdapter.TileType.HEALTH);
//            }
//        }

        if (next == null) {
//            IStrategy random = new RandomMoveStrategy();
//            next = random.getNextCoordinate(map, carPosition, maxHealth, health, fuel, enoughParcel);
//            next = new Stack<>();
//            next.push(carPosition);
            next = carPosition;
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

    /* ** try to get rid of below, but don't delete them until we finished ** */
//    private Coordinate choosePath(MapRecorder map,
//                                  Coordinate carPosition,
//                                  float health,
//                                  float fuel,
//                                  ITileAdapter.TileType... tileTypes) {
//        ArrayList<Coordinate> goals = new ArrayList<>();
//
//
//        for (ITileAdapter.TileType tileType: tileTypes) {
//            goals.addAll(map.getCoordinates(tileType));
//        }
//
//        return closestPath(map, carPosition, health, fuel, goals,
//                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));
//    }
//
//    private Coordinate choosePath(MapRecorder map,
//                                  Coordinate carPosition,
//                                  float health,
//                                  float fuel,
//                                  TileStatus tileStatus) {
//
//        ArrayList<Coordinate> goals = new ArrayList<>(map.getSurroundingUnExploredCoordinates());
//
////        System.out.println(goals.size());
//
//        return closestPath(map, carPosition, health, fuel, goals,
//                new ArrayList<>(Arrays.asList(tileStatus, TileStatus.EXPLORED)));
//    }
//
//    private Coordinate closestPath(MapRecorder map,
//                                   Coordinate carPosition,
//                                   float health,
//                                   float fuel,
//                                   ArrayList<Coordinate> goals,
//                                   ArrayList<TileStatus> tileStatusToGo) {
//        float minFuelUsage = Float.MIN_VALUE;
//        float minHealthLeft = Float.MIN_VALUE;
//
//        Coordinate next = null;
//
//        if (goals.isEmpty()) {
//            return next;
//        }
//        DijkstraPair res = Dijkstra.dijkstra(map,
//                                             carPosition,
//                                             health,
//                                             fuel,
//                                             fuelComparator,
//                                             tileStatusToGo);
////        System.out.println(res.getCostSoFar().keySet());
////        System.out.println(res.getCameFrom().keySet());
//
////        System.out.println();
//        /* go to closest reachable parcel */
//        for (Coordinate goal : goals) {
////            System.out.println("goal: " + goal.toString() +
////                    " (" + map.getTileAdapter(goal).getType() + ")" +
////                    " (" + map.getTileStatus(goal) + ")");
//
//
//            if (isPossible(res.getCostSoFar(), goal) &&
//                    (((res.getCostSoFar().get(goal).getFuel() > minFuelUsage)) ||
//                        ((res.getCostSoFar().get(goal).getFuel() == minFuelUsage) &&
//                         (res.getCostSoFar().get(goal).getHealth() > minHealthLeft) ) )) {
////            if (res.getCameFrom().containsKey(goal) &&
////                    (((res.getCostSoFar().get(goal).getFuel() > minFuelUsage)) ||
////                            ((res.getCostSoFar().get(goal).getFuel() == minFuelUsage) &&
////                                    (res.getCostSoFar().get(goal).getHealth() > minHealthLeft)) ) ) {
//                next = res.getNext(goal);
//                minFuelUsage  = res.getCostSoFar().get(goal).getFuel();
//                minHealthLeft = res.getCostSoFar().get(goal).getHealth();
//
////                System.out.print(carPosition.toString() + "->");
////                res.printPath(goal);
//            }
//        }
//        return next;
//    }

    static class FuelComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
//            if (o1.getFuelCost() < o2.getFuelCost()) {
//                return 1;
//            } else if (o1.getFuelCost() > o2.getFuelCost()) {
//                return -1;
//            } else {
//                return 0;
//            }
            if (o1.getFuelCost() < o2.getFuelCost()) {
                return 1;
            } else if (o1.getFuelCost() > o2.getFuelCost()) {
                return -1;
            } else if (o1.getHealth() < o2.getHealth()) {
                return -1;
            } else if (o1.getHealth() > o2.getHealth()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
