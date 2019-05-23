package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

import java.util.HashMap;
import java.util.Stack;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-23 15:31
 * description:
 **/
public class DijkstraPair {
    private HashMap<Coordinate, Coordinate> cameFrom;
    private HashMap<Coordinate, Node>       costSoFar;

    public DijkstraPair(HashMap<Coordinate, Coordinate> cameFrom,
                        HashMap<Coordinate, Node>       costSoFar) {
        this.cameFrom  = cameFrom;
        this.costSoFar = costSoFar;
    }

    public HashMap<Coordinate, Coordinate> getCameFrom() {
        return this.cameFrom;
    }

    public HashMap<Coordinate, Node> getCostSoFar() {
        return costSoFar;
    }

    public Coordinate getNext(Coordinate destination) {
        Stack<Coordinate> path = new Stack<>();

        for (Coordinate c = destination; cameFrom.get(c) != null; c = cameFrom.get(c)) {
            path.push(c);
        }

        return path.pop();
    }
}