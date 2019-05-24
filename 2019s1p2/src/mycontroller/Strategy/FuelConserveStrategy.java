package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 1:42
 * description:
 **/

public class FuelConserveStrategy implements IStrategy {
    private FuelComparator fuelComparator;

    public FuelConserveStrategy() {
        fuelComparator = new FuelComparator();
    }

    @Override
    public void updateCost(MapRecorder mapRecorder) {

    }

    @Override
    public Coordinate getNextCoordinate(MapRecorder map, Coordinate carPosition, float health, float fuel, boolean enoughParcel) {
        Coordinate next;
        /* go to parcels */
        if (!enoughParcel) {
            next = choosePath(map, carPosition, health, fuel, ITileAdapter.TileType.PARCEL);
        /* go to finish */
        } else {
            next = choosePath(map, carPosition, health, fuel, ITileAdapter.TileType.FINISH);
        }

//        if (health <= 100) {
//            /* no where to go, so go to closest health/water */
//            if (next == null) {
//                next = choosePath(map, carPosition, health, fuel, ITileAdapter.TileType.WATER, ITileAdapter.TileType.HEALTH);
//            }
//        }

        /* still no where to go, so go to closest unexplored */
        if (next == null) {
            next = choosePath(map, carPosition, health, fuel, TileStatus.UNEXPLORED);
        }

        /* debug */
        if (next == null) {
            System.out.println("Error! No next searched!");
        }

        return next;
    }

    private Coordinate choosePath(MapRecorder map,
                                  Coordinate carPosition,
                                  float health,
                                  float fuel,
                                  ITileAdapter.TileType... tileTypes) {
        ArrayList<Coordinate> goals = new ArrayList<>();

        for (ITileAdapter.TileType tileType: tileTypes) {
            goals.addAll(map.getCoordinates(tileType));
        }

        return closestPath(map, carPosition, health, fuel, goals,
                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));
    }

    private Coordinate choosePath(MapRecorder map,
                                  Coordinate carPosition,
                                  float health,
                                  float fuel,
                                  TileStatus tileStatus) {

        ArrayList<Coordinate> goals = new ArrayList<>(map.getSurroundingUnExploredCoordinates());

        System.out.println(goals.size());

        return closestPath(map, carPosition, health, fuel, goals, new ArrayList<>(Arrays.asList(tileStatus, TileStatus.EXPLORED)));
    }

    private Coordinate closestPath(MapRecorder map,
                                   Coordinate carPosition,
                                   float health,
                                   float fuel,
                                   ArrayList<Coordinate> goals,
                                   ArrayList<TileStatus> tileStatusToGo) {
        float minFuelUsage = Float.MIN_VALUE;
        Coordinate next = null;
        System.out.println();
        /* go to closest reachable parcel */
        for (Coordinate goal : goals) {
//            System.out.println(tileTypes[0].toString());

            DijkstraPair res = Dijkstra.dijkstra(map,
                                                 carPosition,
                                                 goal,
                                                 health,
                                                 fuel,
                                                 fuelComparator,
                                                 tileStatusToGo);

            if (isPossible(res.getCostSoFar(), goal) &&
                    (res.getCostSoFar().get(goal).getFuel() > minFuelUsage)) {
                next = res.getNext(goal);
                minFuelUsage = res.getCostSoFar().get(goal).getFuel();

                System.out.print(carPosition.toString() + "->");
                res.printPath(goal);
            }

        }


        return next;
    }

    private boolean isPossible(HashMap<Coordinate, Node> costSoFar,
                               Coordinate destination) {
        /* 0.5 see Car.java line 100 */
        if (costSoFar.containsKey(destination) && costSoFar.get(destination).getHealth() >= 0.5) {
//        if (costSoFar.containsKey(destination)) {
            return true;
        }
        return false;
    }

    class FuelComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getFuel() < o2.getFuel()) {
                return -1;
            } else if (o1.getFuel() > o2.getFuel()) {
                return 1;
            } else if (o1.getHealth() < o2.getHealth()) {
                return 1;
            } else if (o1.getHealth() > o2.getHealth()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
