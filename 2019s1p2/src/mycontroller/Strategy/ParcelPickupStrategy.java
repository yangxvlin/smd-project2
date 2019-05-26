package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 13:29
 * description:
 **/

public class ParcelPickupStrategy implements IStrategy {
    private Comparator<Node> comparator;

    public ParcelPickupStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float health,
                                        float fuel,
                                        boolean enoughParcel) {
        ArrayList<Coordinate> parcels = map.getCoordinates(ITileAdapter.TileType.PARCEL);
        if (parcels.isEmpty()) {
            return null;
        }

        Node nextNode = new Node(null, Float.MIN_VALUE, Float.MIN_VALUE);

        DijkstraPair res = Dijkstra.dijkstra(map,
                                             carPosition,
                                             health,
                                             fuel,
                                             comparator,
                                             new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));

        /* go to closest reachable parcel */
        for (Coordinate parcel : parcels) {

            if (isPossible(res.getCostSoFar(), parcel)) {
                Node newNode = new Node(res.getNext(parcel),
                                        res.getCostSoFar().get(parcel).getHealth(),
                                        res.getCostSoFar().get(parcel).getFuel());

                if (comparator.compare(nextNode, newNode) == -1) {
                    nextNode = newNode;
                }
            }

        }

        return nextNode.getC();
    }
}
