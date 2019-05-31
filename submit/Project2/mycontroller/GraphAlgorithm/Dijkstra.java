package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 16:19
 * description: search single source shortest path graph search algorithm
 **/

public class Dijkstra implements ISearchAlgorithm {

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
    @Override
    public ISearchResult search(MapRecorder map,
                                Coordinate source,
                                float maxHealth,
                                float health,
                                float fuelCost,
                                float speed,
                                WorldSpatial.Direction movingDirection,
                                Comparator<Node> comparator,
                                ArrayList<TileStatus> allowableNeighborTileStatus) {

        Node sourceNode = new Node(source, health, fuelCost, maxHealth, speed, movingDirection);

        PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
        frontier.add(sourceNode);

        HashMap<Coordinate, Coordinate> cameFrom  = new HashMap<>();
        HashMap<Coordinate, Node>       costSoFar = new HashMap<>();

        cameFrom.put(source, null);
        costSoFar.put(source, sourceNode);

        /* update the graph when not finished traversing */
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.getHealth() < GAME_OVER_HEALTH) {
                continue;
            }

            /* expand the nodes */
            for (Node neighbor: current.getNeighbors(map, map.tileNeighbors(current.getC(), allowableNeighborTileStatus))) {
                /* update when unvisited node or new node is better than old node */
                if ((!costSoFar.containsKey(neighbor.getC())) ||
                        (comparator.compare(neighbor, costSoFar.get(neighbor.getC())) == 1)) {
                    costSoFar.put(neighbor.getC(), neighbor);
                    frontier.add(neighbor);
                    cameFrom.put(neighbor.getC(), current.getC());
                }
            }
        }

        return new DijkstraResult(cameFrom, costSoFar);
    }
}
