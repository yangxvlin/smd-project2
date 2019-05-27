package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

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
                                        float maxHealth,
                                        float health,
                                        float fuel,
                                        WorldSpatial.Direction carDirection,
                                        Comparator<Node> comparator,
                                        ArrayList<TileStatus> allowableNeighborTileStatus) {

        Node sourceNode = new Node(source, health, fuel, maxHealth, carDirection);

        PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
        frontier.add(sourceNode);

        HashMap<Coordinate, Coordinate> cameFrom  = new HashMap<>();
        HashMap<Coordinate, Node>       costSoFar = new HashMap<>();

        cameFrom.put(source, null);
        costSoFar.put(source, sourceNode);
//        boolean tmp = false;
        /* update the graph when not finished traversing */
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
//            tmp = frontier.isEmpty();
//
//            if (tmp) {
//                System.out.println(current + " " +
//                                Arrays.toString(map.tileNeighbors(current.getC(), allowableNeighborTileStatus).toArray()));
//            }

            if ((current.getFuel() < 0.5) || (current.getHealth() < 0.5)) {
                continue;
            }

            /* expand the nodes */
            for (Node neighbor: current.getNeighbors(map, map.tileNeighbors(current.getC(), allowableNeighborTileStatus))) {

//                // TODO Unexplored ROAD cost = 0 now
//                float neighborHealth = current.getHealth() +
//                        MapRecorder.tileHealthCostMap.get(
//                                map.getTileAdapter(neighbor).getType());
//                // TODO a map with values = 1 or a constant 1?
//                float neighborFuel   = current.getFuel() - 1;
//
//                float neighborMaxHealth = current.getMaxHealth();
//                if (MapRecorder.tileHealthCostMap.get(map.getTileAdapter(neighbor).getType()) > 0) {
//                    neighborMaxHealth += MapRecorder.tileHealthCostMap.get(map.getTileAdapter(neighbor).getType());
//                }
//
//                Node newNode = new Node(neighbor, neighborHealth, neighborFuel, neighborMaxHealth);

//                if (tmp){
//                    System.out.println(neighbor + " " +
//                            Boolean.toString(!costSoFar.containsKey(neighbor)) + " " );
////                            Boolean.toString(comparator.compare(newNode, costSoFar.get(neighbor)) == 1));
//                }
                /* update when unvisited node or new node is better than old node */
                // TODO 1 magic number? justify or become a constant?
                if ((!costSoFar.containsKey(neighbor.getC())) ||
                        (comparator.compare(neighbor, costSoFar.get(neighbor.getC())) == 1)) {
                    costSoFar.put(neighbor.getC(), neighbor);
                    frontier.add(neighbor);
                    cameFrom.put(neighbor.getC(), current.getC());
                }

            }
//            tmp = false;
        }

        return new DijkstraPair(cameFrom, costSoFar);
    }
}
