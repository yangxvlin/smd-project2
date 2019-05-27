package mycontroller.Strategy;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-26 13:41
 * description:
 **/

public class RandomMoveStrategy implements IStrategy {
    @Override
    public Coordinate getNextCoordinate(MapRecorder map,
                                        Coordinate carPosition,
                                        float maxHealth,
                                        float health,
                                        float fuel,
                                        WorldSpatial.Direction carDirection,
                                        boolean enoughParcel) {
        ArrayList<Coordinate> neighbors = map.tileNeighbors(carPosition,
                new ArrayList<>(Arrays.asList(TileStatus.EXPLORED, TileStatus.UNEXPLORED)));

        if (neighbors.isEmpty()) {
            neighbors.add(carPosition);
        }

        Random rand = new Random();
        return neighbors.get(rand.nextInt(neighbors.size()));
    }
}
