package mycontroller.GraphAlgorithm;

import mycontroller.MapRecorder;
import mycontroller.TileStatus;
import utilities.Coordinate;
import world.World;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-22 16:19
 * description:
 **/

public class Dijkstra {

    public static DijkstraPair dijkstra(MapRecorder map,
                                        Coordinate source,
                                        Coordinate destination,
                                        float health,
                                        float fuel,
                                        Comparator<Node> comparator,
                                        ArrayList<TileStatus> tileStatus) {
//        System.out.println(source.toString() + "->" + destination.toString());

        Node sourceNode = new Node(source, health, fuel);

        PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
        frontier.add(sourceNode);

        HashMap<Coordinate, Coordinate> cameFrom  = new HashMap<>();
        HashMap<Coordinate, Node>       costSoFar = new HashMap<>();


        cameFrom.put(source, null);
        costSoFar.put(source, sourceNode);

//        System.out.println(Arrays.toString(tileStatus.toArray()));
        while (!frontier.isEmpty()) {

            Node current = frontier.poll();

//            if (current.getC().equals(destination)) {
//                break;
//            }

            if (current.getFuel() < 0.5) {
                continue;
            }

//            System.out.println(frontier.size() + " " +
//                    Boolean.toString(current.getC().equals(destination)) + " " +
//                    "destination: " + destination.toString() + " " +
//            "current: " + current.toString());

//            System.out.println(current.getC() + " -> " +
//                    Arrays.toString(map.tileNeighbors(current.getC(), tileStatus).toArray()));
            for (Coordinate neighbor: map.tileNeighbors(current.getC(), tileStatus)) {
                float neighborHealth = current.getHealth() +
                        MapRecorder.tileHealthCostMap.get(map.getTileAdapter(current.getC()).getType());
                float neighborFuel   = current.getFuel() - 1;

                Node newNode = new Node(neighbor, neighborHealth, neighborFuel);

                /* update when unvisited node or new node is better than old node */
                if ((!costSoFar.containsKey(neighbor)) || (comparator.compare(newNode, costSoFar.get(neighbor)) == 1)) {
//                    System.out.println(newNode.toString());
                    costSoFar.put(neighbor, newNode);
                    frontier.add(newNode);
                    cameFrom.put(neighbor, current.getC());
                }
            }
        }

        return new DijkstraPair(cameFrom, costSoFar);
    }
}
