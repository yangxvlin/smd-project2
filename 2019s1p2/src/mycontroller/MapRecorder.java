package mycontroller;

import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileAdapter.TileAdapterFactory;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

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
}
