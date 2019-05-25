package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 16:19
 * description: dijkstra single source shortest path graph search algorithm
 **/

public class Dijkstra {

    /**
     * @param map :        the graph to search with
     * @param source :     the start coordinate
     * @param health :     the car's current health
     * @param fuel :       the car's current fuel
     * @param comparator : the node compare function
     * @param allowableNeighborTileStatus : allowable tile status for neighbor tile when expand to new nodes
     * @return
     */
    public static DijkstraPair dijkstra(MapRecorder map,
                                        Coordinate source,
                                        float health,
                                        float fuel,
                                        Comparator<Node> comparator,
                                        ArrayList<TileStatus> allowableNeighborTileStatus) {

        Node sourceNode = new Node(source, health, fuel);

        PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
        frontier.add(sourceNode);

        HashMap<Coordinate, Coordinate> cameFrom  = new HashMap<>();
        HashMap<Coordinate, Node>       costSoFar = new HashMap<>();

        cameFrom.put(source, null);
        costSoFar.put(source, sourceNode);

        /* update the graph when not finished traversing */
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

//            if ((current.getFuel() < 0.5) || (current.getHealth() < 0.5)) {
//                continue;
//            }
            /* expand the nodes */
            for (Coordinate neighbor: map.tileNeighbors(current.getC(),
                    allowableNeighborTileStatus)) {

                // TODO Unexplored ROAD cost = 0 now
                float neighborHealth = current.getHealth() +
                        MapRecorder.tileHealthCostMap.get(
                                map.getTileAdapter(current.getC()).getType());
                // TODO a map with values = 1 or a constant 1?
                float neighborFuel   = current.getFuel() - 1;
                Node newNode = new Node(neighbor, neighborHealth, neighborFuel);

                /* update when unvisited node or new node is better than old node */
                // TODO 1 magic number? justify or becoma a constant?
                if ((!costSoFar.containsKey(neighbor)) ||
                        (comparator.compare(newNode, costSoFar.get(neighbor)) == 1)) {
                    costSoFar.put(neighbor, newNode);
                    frontier.add(newNode);
                    cameFrom.put(neighbor, current.getC());
                }
            }
        }

        return new DijkstraPair(cameFrom, costSoFar);
    }
}
