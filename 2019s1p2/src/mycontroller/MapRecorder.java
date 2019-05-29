package mycontroller;

import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileAdapter.TileAdapterFactory;
import tiles.MapTile;
import tiles.WaterTrap;
import utilities.Coordinate;
import world.World;

import java.util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-21 20:33
 * description: record explored word information
 *              provide below mapping for the system to make decision
 *              {Coordinate            : ITileAdapter         }
 *              {ITileAdapter.TileType : coordinate(s)        }
 *              {Coordinate            : ITileAdapter.TileType}
 **/

public class MapRecorder {
    /**
     * mapping between coordinates and map tile in World
     */
    private HashMap<Coordinate, ITileAdapter> coordinateTileMap;

    /**
     * mapping between tile type and coordinates
     */
    private HashMap<ITileAdapter.TileType, ArrayList<Coordinate>> tileTypeCoordinatesMap;

    /**
     * guide agent the status of the tile in the map
     */
    private HashMap<Coordinate, TileStatus> mapStatus;

    /**
     * mapping between tile type and car health cost
     */
    public static final Map<ITileAdapter.TileType, Float> TILE_HEALTH_COST_MAP = initMap();

    /**
     * @return mapping between tile type and car health cost object
     */
    private static Map<ITileAdapter.TileType, Float> initMap() {
        Map<ITileAdapter.TileType, Float> map = new HashMap<>();

        // TODO constant
        //  https://app.lms.unimelb.edu.au/webapps/discussionboard/do/message?action=list_messages&course_id=_389181_1&nav=discussion_board_entry&conf_id=_835717_1&forum_id=_472706_1&message_id=_1910216_1
        map.put(ITileAdapter.TileType.FINISH, 0f);
        map.put(ITileAdapter.TileType.START,  0f);
        map.put(ITileAdapter.TileType.HEALTH, 1.25f); // 5 (ICE) * 0.25
        map.put(ITileAdapter.TileType.LAVA,   -5f); // 20 (LAVA) * 0.25 (delta)
        map.put(ITileAdapter.TileType.PARCEL, 0f);
        map.put(ITileAdapter.TileType.ROAD,   0f);
        map.put(ITileAdapter.TileType.WALL,   Float.MIN_VALUE);
        map.put(ITileAdapter.TileType.WATER,  (float) WaterTrap.Yield);

        return Collections.unmodifiableMap(map);
    }

    /**
     * initialize map recorder object
     */
    public MapRecorder() {
        this.coordinateTileMap      = new HashMap<>();
        this.tileTypeCoordinatesMap = new HashMap<>();
        this.mapStatus              = new HashMap<>();
    }

    /**
     * update the world without trap information to the map recorder
     * @param initialMap : map without any trap information
     */
    public void updateInitialMap(HashMap<Coordinate, MapTile> initialMap) {
        for (Coordinate c : initialMap.keySet()) {
            MapTile t = initialMap.get(c);
            if (!t.isType(MapTile.Type.EMPTY)) {
                /* create adapter */
                ITileAdapter tileAdapter = TileAdapterFactory.getInstance()
                                                                .createTileAdapter(t);
                /* update two maps */
                updateMapEntry(c, tileAdapter);
//                coordinateTileMap.put(c, tileAdapter);
//                putTileTypeCoordinatesMap(tileAdapter.getType(), c);

                updateCoordinateInitialStatus(c, t);
//                System.out.println(c.toString() + " " + tileAdapter.getType() + " " + mapStatus.get(c));
            }
        }
    }

    private void updateCoordinateInitialStatus(Coordinate c, MapTile t) {
        /* Road are unexplored tile now */
        if (t.isType(MapTile.Type.ROAD)) {
            mapStatus.put(c, TileStatus.UNEXPLORED);
            /* walls are unreachable */
        } else if (t.isType(MapTile.Type.WALL)) {
            mapStatus.put(c, TileStatus.UNREACHABLE);
            /* other MapTile (start, finish) are explored */
        } else {
            mapStatus.put(c, TileStatus.EXPLORED);
        }
    }

    /**
     * update the car's 9*9 observed tiles to the map
     * @param carView : 9*9 grid car view
     */
    public void updateMapRecorder(HashMap<Coordinate, MapTile> carView) {
        for (Coordinate c : carView.keySet()) {
            MapTile t = carView.get(c);
            /* EMPTY means this coordinate is not in the world, thus ignore it */
            if (!t.isType(MapTile.Type.EMPTY)) {
                ITileAdapter currentTileAdapter = TileAdapterFactory.getInstance()
                                                                    .createTileAdapter(t);

                updateMapEntry(c, currentTileAdapter);
                updateCoordinateStatue(c, currentTileAdapter);
            }
        }
    }

    private void putTileTypeCoordinatesMap(ITileAdapter.TileType tileType, Coordinate c) {
        tileTypeCoordinatesMap.putIfAbsent(tileType, new ArrayList<>());
        tileTypeCoordinatesMap.get(tileType).add(c);
    }

    private void updateCoordinateStatue(Coordinate c, ITileAdapter t) {
        if (!t.isType(ITileAdapter.TileType.WALL)) {
            mapStatus.put(c, TileStatus.EXPLORED);
        }
    }

    /**
     * Update two hash map. First overwrite coordinate's MapTile. Second, if
     * there is an change in the MapTile type of the same coordinate, then there
     * is MapTile type change at that location. So, delete previous MapTile type
     * at that coordinate if any and add the new {coordinate: type} pair.
     * @param c
     * @param currentTileAdapter
     */
    private void updateMapEntry(Coordinate c, ITileAdapter currentTileAdapter) {
        ITileAdapter previousTileAdapter = this.coordinateTileMap.get(c);

        if (previousTileAdapter != null) {
            /* type of the tile has changed */
            if (!currentTileAdapter.isType(previousTileAdapter.getType())) {
                /* overwrite previous record */
                coordinateTileMap.put(c, currentTileAdapter);
                /* delete previous record */
                tileTypeCoordinatesMap.get(previousTileAdapter.getType())
                                        .remove(c);
                /* update new record */
                putTileTypeCoordinatesMap(currentTileAdapter.getType(), c);
            }
        /* new tile directly add */
        } else {
            coordinateTileMap.putIfAbsent(c, currentTileAdapter);
            putTileTypeCoordinatesMap(currentTileAdapter.getType(), c);
        }

        /* update coordinate status */
        updateCoordinateStatue(c, currentTileAdapter);
    }

    public void print() {
        for (Coordinate c: coordinateTileMap.keySet()) {
            System.out.println(String.format("(%s), type: %s, status: %s",
                    c.toString(),
                    coordinateTileMap.get(c).getType().toString(),
                    mapStatus.get(c).toString()));
        }
    }

    /**
     * @param c
     * @param allowableTileStatuses
     * @return explored neighbor coordinates
     */
    public ArrayList<Coordinate> tileNeighbors(Coordinate c, ArrayList<TileStatus> allowableTileStatuses) {

        ArrayList<Coordinate> neighbors = new ArrayList<>();

        /* right */
        if (c.x < World.MAP_WIDTH - 1) {
            Coordinate right = new Coordinate(c.x+1, c.y);

            if (allowableTileStatuses.contains(mapStatus.get(right))) {
                neighbors.add(right);
            }
        }

        /* down */
        if (c.y > 0) {
            Coordinate down = new Coordinate(c.x, c.y-1);

            if (allowableTileStatuses.contains(mapStatus.get(down))) {
                neighbors.add(down);
            }
        }

        /* left */
        if (c.x > 0) {
            Coordinate left = new Coordinate(c.x-1, c.y);

            if (allowableTileStatuses.contains(mapStatus.get(left))) {
                neighbors.add(left);
            }
        }

        /* up */
        if (c.y < World.MAP_HEIGHT - 1) {
            Coordinate up = new Coordinate(c.x, c.y + 1);

            if (allowableTileStatuses.contains(mapStatus.get(up))) {
                neighbors.add(up);
            }
        }

        return neighbors;
    }

    public ITileAdapter getTileAdapter(Coordinate c) {
        return coordinateTileMap.get(c);
    }

    public ArrayList<Coordinate> getCoordinates(ITileAdapter.TileType tileType) {
        tileTypeCoordinatesMap.putIfAbsent(tileType, new ArrayList<>());
        return tileTypeCoordinatesMap.get(tileType);
    }

    public ArrayList<Coordinate> getSurroundingUnExploredCoordinates() {
        ArrayList<Coordinate> res = new ArrayList<>();

        for (Coordinate c: mapStatus.keySet()) {
            if (mapStatus.get(c) == TileStatus.EXPLORED) {
                for (Coordinate neighbor: tileNeighbors(c, new ArrayList<>(Collections.singletonList(TileStatus.UNEXPLORED)))) {
                    if (!res.contains(neighbor)) {
                        res.add(neighbor);
                    }
                }
            }
        }

//        System.out.println(Arrays.toString(res.toArray()));
        return res;
    }

    public ArrayList<Coordinate> getOutMostExploredCoordinates() {
        ArrayList<Coordinate> res = new ArrayList<>();

        for (Coordinate c: mapStatus.keySet()) {
            if (mapStatus.get(c) == TileStatus.EXPLORED) {
                ArrayList<Coordinate> neighbors = tileNeighbors(c, new ArrayList<>(Collections.singletonList(TileStatus.UNEXPLORED)));
                if (!neighbors.isEmpty() && !res.contains(c)){
                    res.add(c);
                }
            }
        }

//        System.out.println(Arrays.toString(res.toArray()));
        return res;
    }

    public TileStatus getTileStatus(Coordinate c) {
        return mapStatus.get(c);
    }
}
