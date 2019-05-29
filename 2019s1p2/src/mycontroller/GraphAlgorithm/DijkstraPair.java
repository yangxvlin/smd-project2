package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

import java.util.HashMap;
import java.util.Stack;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-23 15:31
 *
 * description: This class is responsible for storing the result from Dijkstra.
 **/

public class DijkstraPair {
    /**
     * mapping of coordinates from source to any place
     */
    private HashMap<Coordinate, Coordinate> cameFrom;

    /**
     * mapping for car status from source to any place
     */
    private HashMap<Coordinate, Node>       costSoFar;

    /**
     * The constructor for DijkstraPair.
     *
     * @param cameFrom :  mapping of coordinates from source to any place
     * @param costSoFar : mapping for car status from source to any place
     */
    public DijkstraPair(HashMap<Coordinate, Coordinate> cameFrom,
                        HashMap<Coordinate, Node>       costSoFar) {
        this.cameFrom  = cameFrom;
        this.costSoFar = costSoFar;
    }

    /**
     *
     * @param destination : destination of coordinate to go to
     * @return the next coordinate in the path from source to destination
     */
    public Coordinate getNext(Coordinate destination) {
        Stack<Coordinate> path = new Stack<>();

        /* backtrack the path */
        for (Coordinate c = destination; cameFrom.get(c) != null; c = cameFrom.get(c)) {

            assert(!path.contains(c));
            path.push(c);
        }

        /* destination is source */
        if (path.empty()) {
            return destination;
        /* the next coordinate */
        } else {
            return path.pop();
        }
    }

    /**
     * @return mapping for car status from source to any place
     */
    public HashMap<Coordinate, Node> getCostSoFar() {
        return costSoFar;
    }

}