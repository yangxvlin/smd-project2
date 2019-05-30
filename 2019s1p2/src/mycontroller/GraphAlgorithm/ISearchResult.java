package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

import java.util.HashMap;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-23 15:51
 * description: Class to represent search result interface
 */

public interface ISearchResult {
    /**
     * @param destination : destination of coordinate to go to
     * @return the next coordinate in the path from source to destination
     */
    Coordinate getNext(Coordinate destination);

    /**
     * @return : mapping for car status from source to any place
     */
    HashMap<Coordinate, Node> getCostSoFar();

    /**
     * @return : mapping of coordinates from source to any place
     */
    HashMap<Coordinate, Coordinate> getCameFrom();
}
