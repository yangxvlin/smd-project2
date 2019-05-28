package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 15:01
 * description:
 **/

public class ExploreStrategy implements IStrategy {
    private Comparator<Node> comparator;

    public ExploreStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Stack<Coordinate> getNextPath(MapRecorder map,
                                         Coordinate carPosition,
                                         float maxHealth,
                                         float health,
                                         float fuelCost,
                                         float speed,
                                         WorldSpatial.Direction movingDirection,
                                         boolean enoughParcel) {

//        ArrayList<Coordinate> unexplored = new ArrayList<>(map.getSurroundingUnExploredCoordinates());
//
//        if (unexplored.isEmpty()) {
//            return null;
//        }
////        System.out.println(Arrays.toString(unexplored.toArray()));
//
//        DijkstraPair res = Dijkstra.dijkstra(map,
//                carPosition,
//                maxHealth,
//                health,
//                fuelCost,
//                speed,
//                movingDirection,
//                comparator,
//                new ArrayList<>(Arrays.asList(TileStatus.UNEXPLORED, TileStatus.EXPLORED)));
//
////        System.out.println(">>>>");
//
//        return choosePath(unexplored, res, comparator, maxHealth);
//    }
        ArrayList<Coordinate> outMostExplored = new ArrayList<>(map.getOutMostExploredCoordinates());

        if (outMostExplored.isEmpty()) {
            return null;
        }
//        System.out.println(Arrays.toString(unexplored.toArray()));

        DijkstraPair res = Dijkstra.dijkstra(map,
                carPosition,
                maxHealth,
                health,
                fuelCost,
                speed,
                movingDirection,
                comparator,
                new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));
//        System.out.println(">>>>");

        return choosePath(outMostExplored, res, comparator, maxHealth);
    }
}
