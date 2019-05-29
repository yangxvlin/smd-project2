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
 *
 * description : This class is responsible for navigating the car to the randomly selected tile.
 **/

public class RandomMoveStrategy implements IStrategy {
    @Override
    public Coordinate getNextPath(MapRecorder map,
                                  Coordinate carPosition,
                                  float healthUsage,
                                  float health,
                                  float fuelCost,
                                  float speed,
                                  WorldSpatial.Direction movingDirection,
                                  boolean enoughParcel) {
        /*Get all the neighbors*/
        ArrayList<Coordinate> neighbors = map.tileNeighbors(carPosition,
                new ArrayList<>(Arrays.asList(TileStatus.EXPLORED, TileStatus.UNEXPLORED)));

        /*If there is no neighbour, then put current car position in*/
        if (neighbors.isEmpty()) {
            neighbors.add(carPosition);
        }

        Random rand = new Random();

        // return a randomly selected neighbour.
        return neighbors.get(rand.nextInt(neighbors.size()));
    }


    /**
     * This method does nothing, since there is no need to register.
     *
     * @param strategyType : The strategy for the strategy.
     * @param strategy     : The strategy for adding.
     */
    @Override
    public void registerIStrategy(StrategyType strategyType, IStrategy strategy) {
        // do nothing, since there is no need to registering.
    }
}
