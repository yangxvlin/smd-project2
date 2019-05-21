package mycontroller;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<Coordinate, MapTile> tileMap;

    /**
     * mapping between trap tile type and coordinates
     */
    private HashMap<Class<?>, ArrayList<Coordinate>> trapTileCoordinatesMap;

    /**
     * ,apping between tile type and coordinates
     */
    private HashMap<MapTile.Type, ArrayList<Coordinate>> tileCoordinatesMap;

    /**
     * guide agent the status of the tile in the map
     */
    private TileStatus[][] mapStatus;

    public MapRecorder() {
        this.tileMap = new HashMap<>();

        this.trapTileCoordinatesMap = new HashMap<>();
        this.tileCoordinatesMap     = new HashMap<>();

        this.mapStatus = new TileStatus[World.MAP_WIDTH][World.MAP_HEIGHT];

    }

    /**
     *
     * @param carView 9*9 grid car view
     */
    public void updateMapRecorder(HashMap<Coordinate, MapTile> carView) {
        for (Coordinate c : carView.keySet()) {
            MapTile t = carView.get(c);
            if (!t.getType().equals(MapTile.Type.EMPTY)) {
                updateMapEntry(c, t);
            }
        }
    }

    /**
     * exclude to update Road in the initial map because roads are unknown tile
     * in the map
     * @param initialMap
     * @param excludedTileTypes
     */
    public void updateInitialMap(HashMap<Coordinate, MapTile> initialMap, MapTile.Type... excludedTileTypes) {
        List<MapTile.Type> excludedClassesList = Arrays.asList(excludedTileTypes);

        for (Coordinate c : initialMap.keySet()) {
            MapTile t = initialMap.get(c);
            if (!t.getType().equals(MapTile.Type.EMPTY)) {
                if (!excludedClassesList.contains(t.getType())) {
                    updateMapEntry(c, t);
                    /* walls are unreachable */
                    if (t.getType() != MapTile.Type.WALL) {
                        mapStatus[c.x][c.y] = TileStatus.EXPLORED;
                    } else {
                        mapStatus[c.x][c.y] = TileStatus.UNREACHABLE;
                    }
                } else {
                    mapStatus[c.x][c.y] = TileStatus.UNEXPLORED;
                }
            }
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
        assert !currentTile.getType().equals(MapTile.Type.EMPTY);

        MapTile previousTile = tileMap.get(c);
        Class currentTileClass = currentTile.getClass();
        MapTile.Type currentTileType = currentTile.getType();

        /* overwrite coordinate's MapTile */
        tileMap.put(c, currentTile);
        if (currentTile.getType() != MapTile.Type.WALL) {
            mapStatus[c.x][c.y] = TileStatus.EXPLORED;
        }

        /* remove previous type's info there is an change in the type */
        // TODO some redundant code
        if (previousTile != null) {
            Class previousTileClass = previousTile.getClass();
            MapTile.Type previousTileType = previousTile.getType();

            if (!currentTileType.equals(previousTileType)) {
                tileCoordinatesMap.get(previousTileType).remove(c);
                tileCoordinatesMap.putIfAbsent(currentTileType, new ArrayList<>());
                tileCoordinatesMap.get(currentTileType).add(c);
                if (currentTileType.equals(MapTile.Type.TRAP) & !previousTileClass.equals(currentTileClass)) {
                    trapTileCoordinatesMap.get(currentTileClass).remove(c);
                    trapTileCoordinatesMap.putIfAbsent(currentTileClass, new ArrayList<>());
                    trapTileCoordinatesMap.get(currentTileClass).add(c);
                }
            }
        } else {
            tileCoordinatesMap.putIfAbsent(currentTileType, new ArrayList<>());
            tileCoordinatesMap.get(currentTileType).add(c);
            if (currentTileType.equals(MapTile.Type.TRAP)) {
                trapTileCoordinatesMap.putIfAbsent(currentTileClass, new ArrayList<>());
                trapTileCoordinatesMap.get(currentTileClass).add(c);
            }
        }
    }
}
