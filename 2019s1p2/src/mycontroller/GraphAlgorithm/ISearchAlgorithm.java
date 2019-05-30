package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-29 21:40
 * description: search algorithm interface
 **/

public interface ISearchAlgorithm {
    /**
     * if car's health is less then 0.5, then the car game over
     */
    float GAME_OVER_HEALTH = 0.5f;

    /**
     * @param map : the graph to search with
     * @param source : the start coordinate
     * @param health : the car's current health
     * @param fuelCost : the car's current fuel
     * @param speed : car speed
     * @param movingDirection : car's current moving direction
     * @param comparator : the node compare function
     * @param allowableNeighborTileStatus : allowable tile status for neighbor tile when expand to new nodes
     * @return searched result
     */
    ISearchResult search(MapRecorder map,
                         Coordinate source,
                         float maxHealth,
                         float health,
                         float fuelCost,
                         float speed,
                         WorldSpatial.Direction movingDirection,
                         Comparator<Node> comparator,
                         ArrayList<TileStatus> allowableNeighborTileStatus);
}
