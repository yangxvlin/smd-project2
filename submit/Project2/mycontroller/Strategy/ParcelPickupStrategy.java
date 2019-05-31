package mycontroller.Strategy;

import mycontroller.GraphAlgorithm.*;
import mycontroller.MapRecorder;
import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-26 13:29
 *
 * description : This class is responsible for navigating the car to the parcel.
 **/

public class ParcelPickupStrategy implements IStrategy {
    /** The comparator for choosing path */
    private Comparator<Node> comparator;

    /**
     * graph algorithm used to search next coordinate to drive to
     */
    private ISearchAlgorithm searchAlgorithm;

    /**
     * The constructor for ParcelPickupStrategy
     *
     * @param comparator : The comparator used for choosing path which is defined by the user.
     */
    public ParcelPickupStrategy(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method is responsible for finding the next Coordinate on the way to picking up the parcel.
     *
     * @param map             : The map explored by the car.
     * @param carPosition     : The current coordinate of the car.
     * @param healthUsage     : The current healthUsage of the car
     * @param health          : The current health of the car
     * @param fuelCost        : The current fuel cost of the car
     * @param speed           : The current speed of the car.
     * @param movingDirection : The direction that the car is currently moving at
     * @param enoughParcel    : A boolean that indicates whether have picked enough parcels.
     * @return : The Coordinate for the car to go.
     */
    @Override
    public Coordinate getNextPath(MapRecorder map,
                                  Coordinate carPosition,
                                  float healthUsage,
                                  float health,
                                  float fuelCost,
                                  float speed,
                                  WorldSpatial.Direction movingDirection,
                                  boolean enoughParcel) {
        /* get Coordinates of all of parcel tiles */
        ArrayList<Coordinate> parcels = map.getCoordinates(ITileAdapter.TileType.PARCEL);

        /* If there is no parcel tiles, then return null */
        if (parcels.isEmpty()) {
            return null;
        }

        /*
        search the map by Dijkstra within Explored tiles,
         which is used for determining whether the healing tiles are reachable or not.
        */
        ISearchResult res = searchAlgorithm.search(map,
                                                   carPosition,
                                                   healthUsage,
                                                   health,
                                                   fuelCost,
                                                   speed,
                                                   movingDirection,
                                                   comparator,
                                                   new ArrayList<>(Collections.singletonList(TileStatus.EXPLORED)));

        /* return the Coordinates fot the car to go. */
        return choosePath(parcels, res, comparator, healthUsage);
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

    /**
     * add graph search algorithm to the car drive strategy
     *
     * @param searchAlgorithm : graph algorithm used to search next coordinate to drive to
     */
    @Override
    public void registerISearchAlgorithm(ISearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }
}
