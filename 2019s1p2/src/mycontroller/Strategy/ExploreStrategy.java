package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public Coordinate getNextCoordinate(MapRecorder map, Coordinate carPosition, float health, float fuel, boolean enoughParcel) {
        ArrayList<Coordinate> unexplored = new ArrayList<>(map.getSurroundingUnExploredCoordinates());

        if (unexplored.isEmpty()) {
            return null;
        }

        DijkstraPair res = Dijkstra.dijkstra(map,
                carPosition,
                health,
                fuel,
                comparator,
                new ArrayList<>(Arrays.asList(TileStatus.UNEXPLORED, TileStatus.EXPLORED)));

        return choosePath(unexplored, res, comparator);
    }
}
