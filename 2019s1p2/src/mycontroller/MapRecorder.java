package mycontroller;

import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileAdapter.TileAdapterFactory;
import tiles.HealthTrap;
import tiles.LavaTrap;
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
 *              provide mapping between {coordinates: mapTile}
 *                                      {MapTile.Type: coordinates}
 *                                      {MapTile.class (MapTile.Type == Trap): coordinates}
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

    public static final Map<ITileAdapter.TileType, Float> tileHealthCostMap = initMap();

    private static Map<ITileAdapter.TileType, Float> initMap() {
        Map<ITileAdapter.TileType, Float> map = new HashMap<>();

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

    public MapRecorder() {
        this.coordinateTileMap      = new HashMap<>();
        this.tileTypeCoordinatesMap = new HashMap<>();
        this.mapStatus              = new HashMap<>();
    }

    /**
     *
     * @param carView 9*9 grid car view
     */
    public void updateMapRecorder(HashMap<Coordinate, MapTile> carView) {
        for (Coordinate c : carView.keySet()) {
            MapTile t = carView.get(c);
            if (!t.isType(MapTile.Type.EMPTY)) {
                updateMapEntry(c, t);
            }
        }
    }

    /**
     * exclude to update Road in the initial map because roads are unknown tile
     * in the map
     * @param initialMap
     */
    public void updateInitialMap(HashMap<Coordinate, MapTile> initialMap) {

        for (Coordinate c : initialMap.keySet()) {
            MapTile t = initialMap.get(c);
            if (!t.isType(MapTile.Type.EMPTY)) {
                /* create adapter */
                ITileAdapter tileAdapter = TileAdapterFactory.getInstance()
                                                                .createTileAdapter(t);
                /* update two maps */
                coordinateTileMap.put(c, tileAdapter);
                putTileTypeCoordinatesMap(tileAdapter.getType(), c);

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
     * @param currentTile
     */
    public void updateMapEntry(Coordinate c, MapTile currentTile) {
        assert(!currentTile.isType(MapTile.Type.EMPTY));
        ITileAdapter currentTileAdapter  = TileAdapterFactory.getInstance()
                                                                .createTileAdapter(currentTile);
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
     * @return explored neighbor coordinates
     */
    public ArrayList<Coordinate> tileNeighbors(Coordinate c, ArrayList<TileStatus> tileStatuses) {

        ArrayList<Coordinate> neighbors = new ArrayList<>();

        /* up */
        if (c.y < World.MAP_HEIGHT - 1) {
            Coordinate up = new Coordinate(c.x, c.y + 1);

            if (tileStatuses.contains(mapStatus.get(up))) {
                neighbors.add(up);
            }
        }

        /* down */
        if (c.y > 0) {
            Coordinate down = new Coordinate(c.x, c.y-1);

            if (tileStatuses.contains(mapStatus.get(down))) {
                neighbors.add(down);
            }
        }

        /* left */
        if (c.x > 0) {
            Coordinate left = new Coordinate(c.x-1, c.y);

            if (tileStatuses.contains(mapStatus.get(left))) {
                neighbors.add(left);
            }
        }

        /* right */
        if (c.x < World.MAP_WIDTH - 1) {
            Coordinate right = new Coordinate(c.x+1, c.y);

            if (tileStatuses.contains(mapStatus.get(right))) {
                neighbors.add(right);
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
                        res.add(c);
                    }
                }

            }
        }

        return res;
    }
}
