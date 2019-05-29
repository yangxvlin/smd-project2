package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

import java.util.HashMap;

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
