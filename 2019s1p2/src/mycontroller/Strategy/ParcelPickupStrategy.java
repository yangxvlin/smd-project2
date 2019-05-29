package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.Dijkstra;
import mycontroller.GraphAlgorithm.DijkstraPair;
import mycontroller.GraphAlgorithm.Node;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

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
    public Coordinate getNextStep(MapRecorder map,
                                  Coordinate carPosition,
                                  float maxHealth,
                                  float health,
                                  float fuelCost,
                                  float speed,
                                  WorldSpatial.Direction movingDirection,
                                  boolean enoughParcel) {
        ArrayList<Coordinate> parcels = map.getCoordinates(ITileAdapter.TileType.PARCEL);
        if (parcels.isEmpty()) {
            return null;
        }


        DijkstraPair res = Dijkstra.dijkstra(map,
                                             carPosition,
                                             maxHealth,
                                             health,
                                             fuelCost,
                                             speed,
                                             movingDirection,
                                             comparator,
                                             new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));

        return chooseNextStep(parcels, res, comparator, maxHealth);
    }
}
