package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float maxHealth,
                                        float health,
                                        float fuelCost,
                                        float velocity,
                                        WorldSpatial.Direction movingDirection,
                                        boolean enoughParcel) {
        ArrayList<Coordinate> unexplored = new ArrayList<>(map.getSurroundingUnExploredCoordinates());

        if (unexplored.isEmpty()) {
            return null;
        }
//        System.out.println(Arrays.toString(unexplored.toArray()));

        DijkstraPair res = Dijkstra.dijkstra(map,
                carPosition,
                maxHealth,
                health,
                fuelCost,
                velocity,
                movingDirection,
                comparator,
                new ArrayList<>(Arrays.asList(TileStatus.UNEXPLORED, TileStatus.EXPLORED)));

//        System.out.println(">>>>");

        return choosePath(unexplored, res, comparator, maxHealth);
    }
}
